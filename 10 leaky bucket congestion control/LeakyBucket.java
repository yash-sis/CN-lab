import java.util.Scanner;

public class LeakyBucket {
    // Generate a random integer up to a given limit
    static int random(int limit) {
        return (int) (Math.random() * limit);
    }

    public static void main(String[] args) throws InterruptedException {
        int[] packetSize = new int[5];

        // Generate random packet sizes
        System.out.println("Generated Packet Sizes:");
        for (int i = 0; i < 5; i++) {
            packetSize[i] = random(10);
            System.out.println("PacketSize[" + i + "] : " + packetSize[i]);
        }

        // Input output rate and bucket size
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter output rate: ");
        int outputRate = scanner.nextInt();
        System.out.print("Enter bucket size: ");
        int bucketSize = scanner.nextInt();
        scanner.close();

        int remainingBytes = 0; // Remaining bytes in the bucket

        System.out.println("\nProcessing Packets...\n");

        // Process each packet
        for (int i = 0; i < 5; i++) {
            if (packetSize[i] + remainingBytes > bucketSize) {
                // Check if the incoming packet exceeds bucket capacity
                if (packetSize[i] > bucketSize) {
                    System.out.println("Incoming packet " + i + " of size " + packetSize[i] + " exceeds bucket capacity: PACKET REJECTED\n");
                } else {
                    System.out.println("Packet " + i + ": Bucket capacity exceeded: REJECTING new packet\n");
                }
            } else {
                // Add packet to the bucket
                remainingBytes += packetSize[i];
                System.out.println("Incoming packet " + i + " of size: " + packetSize[i]);
                System.out.println("Bytes remaining for transmission: " + remainingBytes);

                int transmissionTime = random(6) * 10; // Simulated transmission time
                System.out.println("Estimated time for transmission: " + transmissionTime + "ms");

                // Simulate transmission process
                for (int clk = 10; clk <= transmissionTime; clk += 10) {
                    Thread.sleep(10); // Simulate clock tick

                    if (remainingBytes > 0) {
                        if (remainingBytes <= outputRate) {
                            // Transmit all remaining bytes
                            System.out.println("Packet of size " + remainingBytes + " transmitted");
                            remainingBytes = 0;
                        } else {
                            // Transmit at the output rate
                            System.out.println("Packet of size " + outputRate + " transmitted");
                            remainingBytes -= outputRate;
                            System.out.println("Bytes remaining after transmission: " + remainingBytes);
                            System.out.println("Time left: " + (transmissionTime - clk) + "ms");
                        }
                    } else {
                        System.out.println("No packets to transmit");
                        break;
                    }
                }
                System.out.println();
            }
        }
    }
}

/*
Generated Packet Sizes:
PacketSize[0] : 1
PacketSize[1] : 0
PacketSize[2] : 2
PacketSize[3] : 2
PacketSize[4] : 7

Enter output rate: 3
Enter bucket size: 5

Processing Packets...

Incoming packet 0 of size: 1
Bytes remaining for transmission: 1
Estimated time for transmission: 30ms
Packet of size 1 transmitted
No packets to transmit

Incoming packet 1 of size: 0
Bytes remaining for transmission: 0
Estimated time for transmission: 40ms
No packets to transmit

Incoming packet 2 of size: 2
Bytes remaining for transmission: 2
Estimated time for transmission: 50ms
Packet of size 2 transmitted
No packets to transmit

Incoming packet 3 of size: 2
Bytes remaining for transmission: 2
Estimated time for transmission: 30ms
Packet of size 2 transmitted
No packets to transmit

Incoming packet 4 of size 7 exceeds bucket capacity: PACKET REJECTED
*/