import java.io.*;
import java.net.*;

public class Client {
    private String name;
    private Socket socket;
    private BufferedReader rd;
    private BufferedWriter wr;

    public Client(String name, String host) {
        this.name = name;
        try {
            this.socket = new Socket(host, 4242);
            this.rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            wr.write(message + "\n");
            wr.flush();

            String response = rd.readLine();
            System.out.println("Server Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            wr.close();
            rd.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
