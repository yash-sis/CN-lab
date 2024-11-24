import java.util.Scanner;

class CRC {
    private static int[] toIntArray(String input, int extraZeros) {
        int[] result = new int[input.length() + extraZeros];
        for (int i = 0; i < input.length(); i++) result[i] = input.charAt(i) - '0';
        return result;
    }

    private static void computeCRC(int[] data, int[] divisor) {
        for (int i = 0; i <= data.length - divisor.length; i++) {
            if (data[i] == 1) 
                for (int j = 0; j < divisor.length; j++) data[i + j] ^= divisor[j];
        }
    }

    private static boolean isValid(int[] data) {
        for (int bit : data) if (bit == 1) return false;
        return true;
    }

    private static void printArray(int[] data) {
        for (int bit : data) System.out.print(bit);
        System.out.println();
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        
        // Get inputs
        System.out.print("Sender:\n");
        System.out.print("Enter data stream: ");
        String dataStream = sc.nextLine();
        System.out.print("Enter generator: ");
        String generator = sc.nextLine();

        // Convert input strings to integer arrays for computation
        int[] data = toIntArray(dataStream, generator.length() - 1);
        int[] divisor = toIntArray(generator, 0);

        // Calculate CRC
        computeCRC(data, divisor);
        System.out.print("The CRC code is: ");
        printArray(data);

        // Check input CRC
        System.out.print("\nReciever:\n");
        System.out.print("Enter transmitted data: ");
        dataStream = sc.nextLine();
        System.out.print("Enter generator: ");
        generator = sc.nextLine();
        
        // Update data and divisor with new input
        data = toIntArray(dataStream, generator.length() - 1);
        divisor = toIntArray(generator, 0);

        // Calculate remainder for validation
        computeCRC(data, divisor);
        System.out.println(isValid(data) ? "\nData stream is valid" : "\nData stream is invalid. CRC error occurred.");

        sc.close();
    }
}
