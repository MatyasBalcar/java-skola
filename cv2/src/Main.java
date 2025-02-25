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


        //sample test kod

        //inicializace
        Client client0 = new Client("cl0", default_ip);
        Client client1 = new Client("cl1", default_ip);

        //signing both users up
        client0.sendMessage("signup cl0 password");
        client1.sendMessage("signup cl1 password");

        //logging cl0 in
        client0.sendMessage("login cl0 password");

        //sending message to cl1
        client0.sendMessage("msg for cl1 jak se mas");

        //logging out cl0
        client0.sendMessage("logout");

        //logging in cl1
        client1.sendMessage("login cl1 password");

        //reading messages
        client1.sendMessage("read");

        //logging out cl1
        client1.sendMessage("logout");

        //nesmysl
        client1.sendMessage("paradigmataprogramovani");





    }

}
