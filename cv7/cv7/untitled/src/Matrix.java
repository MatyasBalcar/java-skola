import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Matrix {
    double[][] values;

    public Matrix(double[][] values) throws IllegalArgumentException {
        if(values.length != values[0].length){
            throw new IllegalArgumentException("The number of rows and columns do not match");
        }else{
            this.values = values;
        }
    }

    public Matrix add(Matrix that) throws IllegalArgumentException {
        if(that.values.length != that.values[0].length){
            throw new IllegalArgumentException("The number of rows and columns do not match");
        }
        Matrix result = new Matrix(this.values);

        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < values[i].length; j++){
                result.values[i][j] = values[i][j] + that.values[i][j];
            }
        }
        return result;
    }

    public void saveToTxt(String path) throws FileNotFoundException {
        try{
            FileWriter file = new FileWriter(path);
            BufferedWriter buffer = new BufferedWriter(file);

            for (double[] value : values) {
                for (double v : value) {
                    buffer.append(String.valueOf(v)).append(" ");
                }
                buffer.newLine();
            }
            buffer.close();
        }
        catch (IOException e){
             throw new FileNotFoundException(path);
        }
    }

    public void loadFromTxt(String path) throws IOException {
        List<double[]> matrixList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                double[] row = Arrays.stream(parts).mapToDouble(Double::parseDouble).toArray();
                matrixList.add(row);
            }
        }

        this.values = new double[matrixList.size()][];
        for (int i = 0; i < matrixList.size(); i++) {
            this.values[i] = matrixList.get(i);
        }
    }

    public void printMatrix() {
        System.out.print("\n");
        if (values == null || values.length == 0 || values[0].length == 0)  {
            System.out.println("Matrix is empty.");
            return;
        }
        for (double[] row : values) {
            System.out.println(Arrays.toString(row));
        }
        System.out.print("\n");
    }

    public void saveToBinary(String path) throws FileNotFoundException {
        try {
            FileWriter file = new FileWriter(path);
            BufferedWriter buffer = new BufferedWriter(file);

            // Write matrix dimensions
            buffer.append(String.valueOf(values.length)).append(" ");
            buffer.append(String.valueOf(values[0].length)).append("\n");

            // Write matrix values
            for (double[] row : values) {
                for (double v : row) {
                    buffer.append(String.valueOf(v)).append(" ");
                }
                buffer.append("\n"); // Newline after each row
            }
            buffer.close();
        } catch (IOException e) {
            throw new FileNotFoundException(path);
        }
    }

    public void loadFromBinary(String path) throws FileNotFoundException {
        try {
            FileReader file = new FileReader(path);
            BufferedReader buffer = new BufferedReader(file);
            Scanner scanner = new Scanner(buffer);

            // Read dimensions
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            values = new double[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (scanner.hasNextDouble()) {
                        values[i][j] = scanner.nextDouble();
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            throw new FileNotFoundException(path);
        }
    }


    public void saveToBinaryStream(String path) throws FileNotFoundException {
        try (FileOutputStream file = new FileOutputStream(path);
             BufferedOutputStream buffer = new BufferedOutputStream(file);
             DataOutputStream dataOut = new DataOutputStream(buffer)) {

            // Write matrix dimensions
            dataOut.writeInt(values.length);
            dataOut.writeInt(values[0].length);

            // Write matrix values
            for (double[] row : values) {
                for (double v : row) {
                    dataOut.writeDouble(v);
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException(path);
        }
    }
}

