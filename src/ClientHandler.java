import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final int clientNumber;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Bienvenue ! Vous etes le client numero " + clientNumber);
            out.println("Tapez 'quit' pour vous deconnecter.");

            String line;
            while ((line = in.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("quit")) {
                    out.println("Au revoir client numero " + clientNumber);
                    break;
                }
                System.out.println("[Client n°" + clientNumber + "] " + line);
                out.println("Serveur (echo pour client numero " + clientNumber + ") : " + line);
            }
        } catch (IOException e) {
            System.err.println("Erreur avec le client n°" + clientNumber + " : " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) { }
            System.out.println("Client n°" + clientNumber + " deconnecte.");
        }
    }
}