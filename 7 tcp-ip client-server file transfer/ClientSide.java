import java.net.*;
import java.io.*;

public class ClientSide {
    public static void main(String[] args) throws Exception {
        // Connect to server on localhost and port 4000
        Socket socket = new Socket("127.0.0.1", 4000);

        // Read filename from user
        System.out.print("Enter the file name: ");
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String fileName = userInput.readLine();

        // Send filename to server
        PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
        serverOutput.println(fileName);

        // Receive file contents from server
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        System.out.println("File contents:");
        while ((line = serverInput.readLine()) != null) {
            System.out.println(line);
        }

        // Close resources
        userInput.close();
        serverInput.close();
        serverOutput.close();
        socket.close();
    }
}
