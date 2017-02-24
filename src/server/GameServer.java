package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * TODO: Add description
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-23
 */

public class GameServer extends Thread {


  DatagramSocket socket = null;


  public GameServer() {
    try {
      this.socket = new DatagramSocket(9876);
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    while (true) {
      byte[] recieveBuf = new byte[128];
      DatagramPacket recievePacket = new DatagramPacket(recieveBuf, recieveBuf.length);
      try {
        socket.receive(recievePacket);

      } catch (IOException e) {
        e.printStackTrace();
      }
      String message = new String(recievePacket.getData());
      //System.out.print("SERVER RECIEVED: " + message + "\n");

    }
  }

  public void sendData(byte[] data, int length, InetAddress address, int portnum) {
    DatagramPacket sendPacket = new DatagramPacket(data, length, address, portnum);
    try {
      socket.send(sendPacket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
