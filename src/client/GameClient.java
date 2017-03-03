package client;

import common.GameDefaults;
import common.GamePlayer;
import common.GamePlayer.ACTION;
import common.GameStage;
import common.NetworkPacket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * TODO: Add description
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class GameClient implements Runnable {

  private GameStage gameStage;
  private GamePlayer player1;
  private GamePlayer player2;
  private Socket clientSocket;
  private InputStream inputStream;
  private OutputStream outputStream;

  public GameClient(String address, int port) throws IOException {
    clientSocket = new Socket(address, port);
    inputStream = clientSocket.getInputStream();
    outputStream = clientSocket.getOutputStream();
    gameStage = new GameStage();
    player1 = gameStage.getPlayer1();
    player2 = gameStage.getPlayer2();
  }

  public static void main(String[] args) throws IOException {
    GameClient client = new GameClient("localhost", 8022);

    client.run();
  }

  private void onConnect() throws IOException {
    sendPacket(NetworkPacket.otherConnect("DefaultPlayer"));
  }

  private void sendPacket(byte[] packet) throws IOException {
    outputStream.write(packet);
  }

  private boolean receivePacket() throws IOException {
    int maybeType = inputStream.read();

    if (maybeType == -1) {
      System.err.println("Read -1 from inputStream");
      return false;
    }

    NetworkPacket.TYPE type = NetworkPacket.TYPE.values()[(byte) maybeType];

    switch (type) {
      default:
        System.err.println("Unimplemented packet!");
        System.exit(-1);
        break;
      case S_SYNC_PLAYER:
        int playerNumber = inputStream.read();
        byte data[] = new byte[Double.BYTES];
        inputStream.read(data);
        double x = ByteBuffer.wrap(data).getDouble();
        inputStream.read(data);
        double y = ByteBuffer.wrap(data).getDouble();
        data = new byte[Integer.BYTES];
        inputStream.read(data);
        int hp = ByteBuffer.wrap(data).getInt();
        boolean faceRight;

        if (inputStream.read() == 0) {
          faceRight = false;
        } else {
          faceRight = true;
        }

        GamePlayer toUpdate = null;

        if (playerNumber == 1) {
          toUpdate = player1;

        } else if (playerNumber == 2) {
          toUpdate = player2;
        }
        if (toUpdate != null) {
          toUpdate.setPosition(new Point2D(x, y));
          toUpdate.setHP(hp);
          toUpdate.setFaceRight(faceRight);
        }
    }

    return true;
  }

  public void onKeyPressed(KeyEvent event) {
    try {
      KeyCode code = event.getCode();

      if (code == GameDefaults.MOVE_LEFT) {
        sendPacket(NetworkPacket.actionStart(ACTION.MOVE_LEFT));
      } else if (code == GameDefaults.MOVE_RIGHT) {
        sendPacket(NetworkPacket.actionStart(ACTION.MOVE_RIGHT));
      } else if (code == GameDefaults.JUMP) {
        sendPacket(NetworkPacket.actionStart(ACTION.JUMP));
      } else if (code == GameDefaults.FALL) {
        sendPacket(NetworkPacket.actionStart(ACTION.FALL));
      } else if (code == GameDefaults.HIT) {
        sendPacket(NetworkPacket.actionStart(ACTION.HIT));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void onKeyReleased(KeyEvent event) {
    try {
      KeyCode code = event.getCode();

      if (code == GameDefaults.MOVE_LEFT) {
        sendPacket(NetworkPacket.actionEnd(ACTION.MOVE_LEFT));
      } else if (code == GameDefaults.MOVE_RIGHT) {
        sendPacket(NetworkPacket.actionEnd(ACTION.MOVE_RIGHT));
      } else if (code == GameDefaults.JUMP) {
        sendPacket(NetworkPacket.actionEnd(ACTION.JUMP));
      } else if (code == GameDefaults.FALL) {
        sendPacket(NetworkPacket.actionEnd(ACTION.FALL));
      } else if (code == GameDefaults.HIT) {
        sendPacket(NetworkPacket.actionEnd(ACTION.HIT));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      if (clientSocket.isConnected()) {
        onConnect();

        while (clientSocket.isConnected()) {
          if (!receivePacket()) {
            break;
          }
        }

        System.err.println("Connection lost");
        System.exit(-1);
      } else {
        System.err.println("Tried to start with client not connected to server!");
        System.exit(-1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public GameStage getGameStage() {
    return gameStage;
  }

  public GamePlayer getPlayer1() {
    return player1;
  }

  public GamePlayer getPlayer2() {
    return player2;
  }
}
