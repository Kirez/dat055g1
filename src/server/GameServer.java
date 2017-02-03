package server;

import java.io.IOException;
import java.net.*;

public class GameServer extends Thread{
    int port =0;
    DatagramSocket socket = null;
    DatagramPacket recievePacket;
    DatagramPacket sendPacket;
    byte[] recieveBuf = new byte[1024];
    byte[] sendBuf = new byte[1024];

    public GameServer(){
        try {
            this.socket = new DatagramSocket(9876);
        }catch(SocketException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            recievePacket = new DatagramPacket(recieveBuf, recieveBuf.length);
            try {
                socket.receive(recievePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(recievePacket.getData());
            System.out.print("SERVER RECIEVED: " +message);
            InetAddress IPAddress = recievePacket.getAddress();
            int port = recievePacket.getPort();
            String sendMess = "YOYOYO! IM DA SERVER!";
            sendData(sendMess.getBytes(), (int)sendMess.getBytes().length, IPAddress, port);
            break;
        }
    }

    public void sendData(byte[] data, int lenght, InetAddress address, int portnum){
        DatagramPacket sendPacket = new DatagramPacket(data, lenght, address, portnum);
        try{
            socket.send(sendPacket);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
