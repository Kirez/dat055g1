package server;

import common.GamePlayer;
import common.GamePlayer.ACTION;
import common.GameStage;
import common.NetworkPacket;
import common.NetworkPacket.TYPE;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class GameServer {

  private GameStage gameStage;
  private GamePlayer player1;
  private GamePlayer player2;
  private StageController stageController;
  private PlayerController player1Controller;
  private PlayerController player2Controller;
  private GameEngine gameEngine;
  private ServerSocket serverSocket;
  private Socket client1;
  private Socket client2;
  private Thread gameThread;
  private HashSet<Thread> clientThreads;

  public GameServer(int port) throws IOException {
    serverSocket = new ServerSocket(port);

    gameStage = new GameStage();
    player1 = gameStage.getPlayer1();
    player2 = gameStage.getPlayer2();

    stageController = new StageController(gameStage);
    player1Controller = new PlayerController(player1);
    player2Controller = new PlayerController(player2);

    gameEngine = new GameEngine();
    gameEngine.addController(stageController);
    gameEngine.addController(player1Controller);
    gameEngine.addController(player2Controller);

    gameThread = new Thread(gameEngine);
  }

  void syncClients() throws IOException {
    OutputStream outputStream = client1.getOutputStream();
    outputStream.write(NetworkPacket.sync(player1, 1));
    outputStream.write(NetworkPacket.sync(player2, 2));
    outputStream = client2.getOutputStream();
    outputStream.write(NetworkPacket.sync(player1, 1));
    outputStream.write(NetworkPacket.sync(player2, 2));
  }

  public void start() {
    while (client1 == null || client2 == null) {
      try {
        System.out.println("Waiting for new connection");
        Socket client = serverSocket.accept();
        System.out.println("New connection!");
        if (client1 == null) {
          client1 = client;
        } else {
          client2 = client;
        }
      } catch (IOException e){
        e.printStackTrace();
      }
    }

    Thread clientThread1 = new Thread(new ClientListener(client1, player1Controller));
    Thread clientThread2 = new Thread(new ClientListener(client2, player2Controller));

    clientThread1.start();
    clientThread2.start();
    gameThread.start();

    while (true) {
      try {
        syncClients();
        Thread.sleep(16l);
      } catch (InterruptedException | IOException e) {
        e.printStackTrace();
      }
    }
  }

  private class ClientListener implements Runnable {
    private Socket socket;
    InputStream inputStream;
    PlayerController playerController;

    public ClientListener(Socket socket, PlayerController playerController) {
      this.socket = socket;
      this.playerController = playerController;
    }

    public TYPE identifyPacket() throws IOException {
      int maybeType = inputStream.read();

      if (maybeType < 0 || maybeType >= TYPE.values().length) {
        System.err.println("Received packet type is unknown");
        return TYPE.ERROR;
      }

      NetworkPacket.TYPE type = NetworkPacket.TYPE.values()[(byte) maybeType];

      return type;
    }

    @Override
    public void run() {
      try {
        inputStream = socket.getInputStream();
        while (socket.isConnected()) {
          switch (identifyPacket()) {
            default:
              System.err.println("Unsupported packet");
              break;
            case C_ACTION_START:
              int actionStartInt = inputStream.read();
              if (actionStartInt < 0 || actionStartInt >= ACTION.values().length) {
                System.err.println("Unknown action started");
              }
              else {
                ACTION action = ACTION.values()[actionStartInt];
                playerController.actionStart(action);
              }
              break;
            case C_ACTION_END:
              int actionEndInt = inputStream.read();
              if (actionEndInt < 0 || actionEndInt >= ACTION.values().length) {
                System.err.println("Unknown action started");
              }
              else {
                ACTION action = ACTION.values()[actionEndInt];
                playerController.actionEnd(action);
              }
              break;
          }
        }
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    GameServer server = new GameServer(8022);
    server.start();
  }
}
