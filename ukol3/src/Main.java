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




        // Sorting Tests
        System.out.println("\n=== Sorting Tests ===");

        Client client2 = new Client(default_ip);

        client2.sendMessage("sort 1000 1000 1");
        client2.sendMessage("sort 1000 1000 1");
        client2.sendMessage("sort 1000 1000 1");
        client2.sendMessage("sort 1000 1000 1");
        client2.sendMessage("sort 1000 1000 1");
        client2.sendMessage("sort 1000 1000 1");



//        // Invalid cases
//        client2.sendMessage("sort 0 4");  // Invalid size
//        client2.sendMessage("sort -100 4"); // Negative size
//        client2.sendMessage("sort 100 -2"); // Negative thread count
//        client2.sendMessage("sort 100 abc"); // Invalid thread count
//        client2.sendMessage("sort abc 5"); // Invalid size

        client2.sendMessage("quit");


        System.out.println("\nStopping server...");
        Client client3 = new Client(default_ip);
        client3.sendMessage("stopserver");
        client3.sendMessage("quit");
        client3.closeConnection();
    }
}
