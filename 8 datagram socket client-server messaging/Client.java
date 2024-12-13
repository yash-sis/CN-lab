import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket(9877);
        byte[] buffer = new byte[1024];

        System.out.println("Client is waiting for server messages...");

        while (true) {
            // Receive message from server
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(receivePacket);

            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server: " + message);
        }
    }
}
