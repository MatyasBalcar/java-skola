import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server("server");
        String default_ip = "127.0.0.1";

        new Thread(() -> {
            try {
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("\n=== Sorting Tests ===");

        Client client2 = new Client(default_ip);


        client2.sendMessage("sort 1000 400 1");

        client2.sendMessage("sort 1000 500 1");

        client2.sendMessage("sort 1000 750 1");

        client2.sendMessage("quit");

        System.out.println("\nServer usina...");
        client2.sendMessage("stopserver");
        client2.sendMessage("quit");
        client2.closeConnection();
        System.out.println("\nServer spinka...");
    }
}
