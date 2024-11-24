import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveBuffer = new byte[1024];
        byte[] sendBuffer;
        System.out.println("Waiting for client request...");

        while (true) {
            // Receive data from client
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            serverSocket.receive(receivePacket);
            InetAddress clientIP = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            String clientData = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("Client connected: " + clientIP);
            System.out.println("Client message: " + clientData);

            // Check if client wants to exit
            if (clientData.trim().equalsIgnoreCase("exit")) {
                System.out.println("Client has terminated the connection.");
                break;
            }

            // Send response to client
            System.out.print("Server: ");
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
            String serverResponse = serverInput.readLine();
            sendBuffer = serverResponse.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientIP, clientPort);
            serverSocket.send(sendPacket);
            System.out.println("Message sent to client.");
        }

        serverSocket.close();
        System.out.println("Server terminated.");
    }
}
