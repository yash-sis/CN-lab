import java.net.*;
import java.io.*;

public class ServerSide {
    public static void main(String[] args) throws Exception {
        // Establish server on port 4000
        ServerSocket serverSocket = new ServerSocket(4000);
        System.out.println("Server is ready for connection...");
        Socket socket = serverSocket.accept();
        System.out.println("Connection successful!");

        // Read filename from client
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String fileName = clientInput.readLine();

        // Read file contents
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);

        // Send file contents to client
        String line;
        while ((line = fileReader.readLine()) != null) {
            clientOutput.println(line);
        }

        // Close resources
        fileReader.close();
        clientInput.close();
        clientOutput.close();
        socket.close();
        serverSocket.close();
    }
}
