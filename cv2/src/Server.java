import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Server {
    public String name;

    private Hashtable<String, String> userDb = new Hashtable<>();
    private Hashtable<String, String> messages = new Hashtable<>();
    private Hashtable<String, BufferedWriter> clientWriters = new Hashtable<>();

    private String signedInUserName = "";

    public Server(String name) {
        this.name = name;
    }

    private void processRequests(Socket clientSocket, BufferedReader rd, BufferedWriter wr) throws IOException {
        while (true) {
            String line = rd.readLine();
            if (line == null || line.equalsIgnoreCase("quit")) break;

            String resp;
            String[] sp_line = line.split("\\s+");
            String keyword = sp_line[0];

            switch (keyword) {
                case "signup":
                    resp = this.signUpUser(sp_line[1], sp_line[2]);
                    break;
                case "login":
                    resp = this.logInUser(sp_line[1], sp_line[2], wr);
                    break;
                case "msg":
                    String message = Arrays.stream(sp_line).skip(3).collect(Collectors.joining(" "));
                    resp = this.sendMessage(sp_line[2], message);
                    break;
                case "read":
                    resp = this.readMessages();
                    break;

                case "logout":
                    resp = this.logOut(this.signedInUserName, clientSocket, rd, wr);
                    return;
                default:
                    resp = "Unrecognized command";
                    break;
            }

            wr.write(resp + "\n");
            wr.flush();
        }
    }

    private int checkIfUserInDb(String username) {
        if (this.userDb.containsKey(username)) {
            return 1;
        } else {
            return 0;
        }
    }

    public String signUpUser(String userName, String password) {
        if (checkIfUserInDb(userName) == 1) {
            return "User already in db";
        } else {
            userDb.put(userName, password);
            return "Status 200: User added to database";
        }
    }

    public String logInUser(String userName, String password, BufferedWriter wr) {
        if (Objects.equals(this.signedInUserName, "") && checkIfUserInDb(userName) == 1 && userDb.get(userName).equals(password)) {
            this.signedInUserName = userName;
            clientWriters.put(userName, wr); // Uložíme spojený BufferedWriter
            return "Status: 200 " + userName + " User logged in";
        } else {
            return "ERR User with these credentials doesn't exist or user is already logged in";
        }
    }


    public String sendMessage(String user, String message) {
        if (!Objects.equals(this.signedInUserName, "")) {
            this.messages.put(user, message);
            return "Status: 200 Message sent";
        } else {
            return "ERR User isn't signed in";
        }
    }

    public String readMessages() {
        if (!Objects.equals(this.signedInUserName, "")) {
            StringBuilder ret = new StringBuilder();
            Iterator<Map.Entry<String, String>> it = messages.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry.getKey().equals(this.signedInUserName)) {
                    ret.append("FROM: ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
                    messages.remove(entry.getKey());
                }
            }

            return ret.append("OK").toString();
        } else {
            return "ERR User isn't signed in";
        }
    }


    public String logOut(String userName, Socket clientSocket, BufferedReader rd, BufferedWriter wr) {
        if (!Objects.equals(this.signedInUserName, "")) {
            this.signedInUserName = "";

            if (wr != null) {
                try {
                    wr.write("LOGOUT: You have been logged out\n");
                    wr.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            clientWriters.remove(userName);

            try {
                if (rd != null) rd.close();
                if (wr != null) wr.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Status: 200 " + userName + " Logged out and connection closed";
        } else {
            return "ERR User isn't signed in";
        }
    }


    public void startServer() throws IOException {
        ServerSocket srvSocket = new ServerSocket(4242);
        System.out.println("Server started. Waiting for clients...");

        while (true) {
            Socket clientSocket = srvSocket.accept();
            System.out.println("Client connected!");

            new Thread(() -> {
                try (
                        BufferedReader rd = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
                ) {
                    processRequests(clientSocket, rd, wr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
