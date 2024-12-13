import java.util.Scanner;

public class LeakyBucket {
    // Generate a random integer up to a given limit
    static int random(int limit) {
        return (int) (Math.random() * limit);
    }

    public static void main(String[] args) throws InterruptedException {
        int[] packetSize = new int[5];

        System.out.println("Generated Packet Sizes:");
        for (int i = 0; i < 5; i++) {
            packetSize[i] = random(10);
            System.out.println("PacketSize[" + i + "] : " + packetSize[i]);
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter output rate: ");
        int outputRate = sc.nextInt();
        System.out.print("Enter bucket size: ");
        int bucketSize = sc.nextInt();
        sc.close();

        int remainingBytes = 0;

        System.out.println("\nProcessing Packets...\n");
        for (int i = 0; i < 5; i++) {
            int packet = packetSize[i];
            if (packet + remainingBytes > bucketSize) {
                System.out.println("Packet " + i + " size " + packet + " exceeds capacity: REJECTED\n");
                continue;
            }

            remainingBytes += packet;
            System.out.println("Incoming packet " + i + ": " + packet + ", Bytes remaining: " + remainingBytes);
            int transmissionTime = random(6) * 10;
            System.out.println("Transmission time: " + transmissionTime + "ms");

            for (int clk = 10; clk <= transmissionTime && remainingBytes > 0; clk += 10) {
                Thread.sleep(10);
                int transmitted = Math.min(outputRate, remainingBytes);
                remainingBytes -= transmitted;
                System.out.println("Transmitted " + transmitted + " bytes, Bytes remaining: " + remainingBytes);
            }
            System.out.println();
        }
    }
}
