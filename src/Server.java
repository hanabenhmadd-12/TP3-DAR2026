import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 1234;

    public static void main(String[] args) {
        int clientCount = 0;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré, en attente de clients sur le port " + PORT + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                clientCount++;
                System.out.println("Client n°" + clientCount + " connecté, IP : "
                        + socket.getRemoteSocketAddress());
                new ClientHandler(socket, clientCount).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}