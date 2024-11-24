import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverIP = InetAddress.getByName("127.0.0.1");
        byte[] sendBuffer;
        byte[] receiveBuffer = new byte[1024];
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // Get client input and send to server
            System.out.print("Client: ");
            String clientMessage = clientInput.readLine();
            sendBuffer = clientMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverIP, 9876);
            clientSocket.send(sendPacket);

            // Exit condition
            if (clientMessage.trim().equalsIgnoreCase("exit")) {
                System.out.println("Client terminated the connection.");
                break;
            }

            // Receive response from server
            System.out.println("Waiting for server response...");
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);
            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("Server: " + serverResponse);
        }

        clientSocket.close();
        System.out.println("Client terminated.");
    }
}
