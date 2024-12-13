import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress receiverAddress = InetAddress.getByName("localhost");
        int receiverPort = 9876;
        int windowSize = 8;

        // Message to send
        String message = "Hi";
        byte[] data = message.getBytes();

        int base = 0;
        boolean[] acknowledged = new boolean[data.length];

        while (base < data.length) {
            // Send packets within the window
            for (int i = base; i < base + windowSize && i < data.length; i++) {
                if (!acknowledged[i]) { // Only send unacknowledged packets
                    byte[] sendData = new byte[2];
                    sendData[0] = (byte) i; // Sequence number
                    sendData[1] = data[i];  // Data byte

                    DatagramPacket packet = new DatagramPacket(sendData, sendData.length, receiverAddress, receiverPort);
                    clientSocket.send(packet);
                    System.out.println("Sent: " + (char) data[i] + " with SeqNum: " + i);
                }
            }

            // Wait for acknowledgment
            byte[] ackData = new byte[1];
            DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length);
            clientSocket.receive(ackPacket);

            int ack = ackPacket.getData()[0];
            System.out.println("Acknowledgment received for SeqNum: " + ack);

            if (ack >= base) {
                // Mark packets up to the acknowledged sequence number as acknowledged
                for (int i = base; i <= ack; i++) {
                    acknowledged[i] = true;
                }
                base = ack + 1; // Slide the window
            }
        }

        clientSocket.close();
        System.out.println("All packets sent successfully!");
    }
}
