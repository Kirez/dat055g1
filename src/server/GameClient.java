package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread {

  int port;
  InetAddress address;
  DatagramSocket socket = null;
  DatagramPacket sendPacket;
  DatagramPacket recievePacket;
  byte[] recieveBuf = new byte[1024];
  byte[] sendBuf = new byte[1024];

  public GameClient() {
    try {
      this.socket = new DatagramSocket();
      this.address = InetAddress.getByName("localhost");
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  public void run() {
    sendBuf = "yoyoyoyoy im da client".trim().getBytes();
    while (true) {
      sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, 9876);
      try {
        socket.send(sendPacket);
      } catch (IOException e) {
        e.printStackTrace();
      }
      recievePacket = new DatagramPacket(recieveBuf, recieveBuf.length);
      try {
        socket.receive(recievePacket);
        String recMess = new String(recievePacket.getData());
        System.out.println("What the server sayyy?: " + recMess);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  public void sendData(byte[] data) {
    DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
    try {
      socket.send(sendPacket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
