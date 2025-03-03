import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket;
    private BufferedReader rd;
    private BufferedWriter wr;

    public Client(String host) throws IOException {
        try {
            this.socket = new Socket(host, 4242);
            this.rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            wr.write(message + "\n");
            wr.flush();
            System.out.println("Server Response: " + rd.readLine());
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
