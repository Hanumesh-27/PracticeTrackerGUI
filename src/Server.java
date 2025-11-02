import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) {
        int port = 6005;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                String msgIn, msgOut;
                while (true) {
                    msgIn = in.readLine();
                    if (msgIn == null || msgIn.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                    System.out.println("Client: " + msgIn);

                    System.out.print("Server: ");
                    msgOut = keyboard.readLine();
                    out.println(msgOut);
                    if (msgOut.equalsIgnoreCase("exit")) {
                        System.out.println("Chat ended.");
                        break;
                    }
                }
                socket.close();
                System.out.println("\nName : Guru Rohid N");
                System.out.println("Reg No : 2117240020117");
                System.out.println("\nDo you want to wait for another client? (yes/no)");
                BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
                String again = sc.readLine();
                if (!again.equalsIgnoreCase("yes")) {
                    System.out.println("Server shutting down...");
                    break;
                }
            } } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }  } }
