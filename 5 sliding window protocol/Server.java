import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        serverSocket.setSoTimeout(5000); // Timeout after 5 seconds of inactivity
        int expectedSeqNum = 0;

        System.out.println("Server started. Waiting for packets...");
        try {
            while (true) {
                byte[] receiveData = new byte[2];
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(packet); // Wait for a packet

                int seqNum = receiveData[0];
                char receivedChar = (char) receiveData[1];
                System.out.println("Received: " + receivedChar + " with SeqNum: " + seqNum);

                if (seqNum == expectedSeqNum) {
                    System.out.println("In-order packet received: " + receivedChar);
                    byte[] ackData = new byte[] { (byte) seqNum };
                    DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, packet.getAddress(), packet.getPort());
                    serverSocket.send(ackPacket);
                    System.out.println("Acknowledgment sent for SeqNum: " + seqNum);
                    expectedSeqNum++;
                } else {
                    System.out.println("Out-of-order packet received. Ignored.");
                }
            }
        } catch (SocketTimeoutException e) {
            System.out.println("No packets received for 5 seconds. Receiver shutting down.");
        } finally {
            serverSocket.close();
            System.out.println("Receiver closed.");
        }
    }
}
