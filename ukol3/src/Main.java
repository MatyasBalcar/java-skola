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


        client2.sendMessage("sort 1000 1000 ");
        client2.sendMessage("sort 3000 1000 ");
//        client2.sendMessage("sort 100000 100000 ");

        client2.sendMessage("quit");

        System.out.println("\nServer usina...");
        client2.sendMessage("stopserver");
        client2.sendMessage("quit");
        client2.closeConnection();
        System.out.println("\nServer spinka...");
    }
}
