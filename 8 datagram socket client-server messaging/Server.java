import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        InetAddress clientIP = InetAddress.getByName("127.0.0.1");
        Scanner sc = new Scanner(System.in);
        byte[] buffer;

        System.out.println("Server is running...");

        while (true) {
            // Server input
            System.out.print("Server: ");
            String message = sc.nextLine();
            buffer = message.getBytes();

            // Send message to client
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, clientIP, 9877);
            serverSocket.send(sendPacket);

            System.out.println("Message sent to client.");
        }
    }
}
