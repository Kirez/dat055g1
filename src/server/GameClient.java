package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javafx.scene.input.KeyEvent;

/**
 * Stores keypresses in setState, sends keys to server..
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-23
 */
public class GameClient extends Thread {

  /** Destination port number. */
  private int port = 9876;
  /** Destination adress. */
  private InetAddress address;
  private DatagramSocket socket = null;
  private HashSet<String> keys;

  /**
   * Initializes the <tt>GameClient</tt>.
   */
  public GameClient() {
    keys = new HashSet<>();
    try {
      this.socket = new DatagramSocket();
      this.address = InetAddress.getByName("localhost");
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  private void sendData(byte[] data) {
    DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
    try {
      socket.send(sendPacket);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public synchronized void sendKeys() {
    if (keys.isEmpty()) {
      sendData("NOKEY".getBytes());
      return;
    }
    for (String s : keys) {
      sendData(s.getBytes());
    }
  }

  public void run() {
    while (true) {
      sendKeys();
      try {
        sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public synchronized void setKeyPressed(KeyEvent event) {
    keys.add(event.getCode().getName());
  }

  public synchronized void setKeyReleased(KeyEvent event) {
    keys.remove(event.getCode().getName());

  }
}
