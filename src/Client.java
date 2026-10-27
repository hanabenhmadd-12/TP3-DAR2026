import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1234;

        try (Socket socket = new Socket()) {
            InetAddress address = InetAddress.getByName(host);
            socket.connect(new InetSocketAddress(address, port), 5000);
            System.out.println("Connecté au serveur " + address.getHostAddress() + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Thread reader = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException ignored) { }
                System.out.println("Connexion fermée.");
                System.exit(0);
            });
            reader.setDaemon(true);
            reader.start();

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = console.readLine()) != null) {
                out.println(line);
                if (line.trim().equalsIgnoreCase("quit")) break;
            }
            Thread.sleep(200);
        } catch (IOException | InterruptedException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}