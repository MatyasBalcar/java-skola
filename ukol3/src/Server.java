import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Server {
    public String name;
    private final Hashtable<String, String> userDb = new Hashtable<>();
    private final Hashtable<String, String> messages = new Hashtable<>();
    private final Hashtable<String, BufferedWriter> clientWriters = new Hashtable<>();
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
                    String message = String.join(" ", Arrays.copyOfRange(sp_line, 2, sp_line.length));
                    resp = this.sendMessage(sp_line[1], message);
                    break;
                case "read":
                    resp = this.readMessages();
                    break;
                case "sort":
                    if (sp_line.length >= 3) {
                        int size = Integer.parseInt(sp_line[1]);
                        int threads = Integer.parseInt(sp_line[2]);
                        int testMode = 0;
                        if (sp_line.length == 4) {
                            testMode = 1;
                        }
                        resp = this.sortArray(size, threads, testMode);
                    } else {
                        resp = "ERR Invalid sort request format. Use: sort <size> <threads>";
                    }
                    break;
                case "logout":
                    resp = this.logOut(this.signedInUserName, clientSocket, rd, wr);
                    return;
                default:
                    resp = "undefined";
                    break;
            }

            wr.write(resp + "\n");
            wr.flush();
        }
    }

    public String signUpUser(String userName, String password) {
        if (userDb.containsKey(userName)) {
            return "User already exists";
        } else {
            userDb.put(userName, password);
            return "User signed up successfully";
        }
    }

    public String logInUser(String userName, String password, BufferedWriter wr) {
        if (signedInUserName.isEmpty() && userDb.getOrDefault(userName, "").equals(password)) {
            signedInUserName = userName;
            clientWriters.put(userName, wr);
            return "User logged in";
        } else {
            return "ERR Invalid login or already logged in";
        }
    }

    public String sendMessage(String user, String message) {
        if (!signedInUserName.equals("")) {
            messages.put(user, message);
            return "Message sent";
        } else {
            return "ERR User not logged in";
        }
    }

    public String readMessages() {
        if (!signedInUserName.equals("")) {
            String message = messages.getOrDefault(signedInUserName, "No messages");
            messages.remove(signedInUserName);
            return "Messages: " + message;
        } else {
            return "ERR User not logged in";
        }
    }

    public String logOut(String userName, Socket clientSocket, BufferedReader rd, BufferedWriter wr) {
        if (!signedInUserName.equals("")) {
            signedInUserName = "";
            clientWriters.remove(userName);
            try {
                wr.write("LOGOUT: You have been logged out\n");
                wr.flush();
                rd.close();
                wr.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "User logged out";
        } else {
            return "ERR User not logged in";
        }
    }

    public String sortArray(int size, int threads, int testMode) {
        System.out.println("Starting sort for size " + size + " with " + threads + " threads");

        int[] array;
        if (testMode == 1) {
            array = IntStream.range(0, size).map(i -> size - i - 1).toArray();
        } else {
            array = new Random().ints(size, 0, 10000).toArray();
        }

        //tady by mohl byt nejaky cyklus ci neco na Threads.Buidler a Threads.ofVirtual
        //ale nasel jsem tohle snad to muzu pouzit

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        long startTime = System.currentTimeMillis();
        parallelMergeSort(array, executor);
        long endTime = System.currentTimeMillis();

        executor.shutdown();
        return (endTime - startTime) + " ms with " + threads + " threads and size " + size;
    }


    private void parallelMergeSort(int[] array, ExecutorService executor) {
        parallelMergeSortHelper(array, 0, array.length - 1, executor);
    }

    private void parallelMergeSortHelper(int[] array, int left, int right, ExecutorService executor) {
        if (left < right) {
            int mid = (left + right) / 2;

            Future<?> leftTask = executor.submit(() -> parallelMergeSortHelper(array, left, mid, executor));
            Future<?> rightTask = executor.submit(() -> parallelMergeSortHelper(array, mid + 1, right, executor));

            try {
                leftTask.get();
                rightTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            merge(array, left, mid, right);
        }
    }

    private void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = Arrays.copyOfRange(array, left, mid + 1);
        int[] rightArray = Arrays.copyOfRange(array, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < n1) {
            array[k++] = leftArray[i++];
        }
        while (j < n2) {
            array[k++] = rightArray[j++];
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
