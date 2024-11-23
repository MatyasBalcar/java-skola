import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Matrix {
    //  n*n array reprezentujici matice, stejny pocet sloupcu jako radku
    double[][] values;

    public Matrix(double[][] values) throws IllegalArgumentException {
        int height = values.length;
        for (double[] value : values) {
            if (value.length != height) {
//              kdyz pocet sloupcu neni stejny jako pocet radku, hodime error
                throw new IllegalArgumentException("The number of rows and columns do not match");
            }
        }
        this.values = values;

    }

    public Matrix add(Matrix that) throws IllegalArgumentException {
        if (this.values.length != that.values.length || this.values[0].length != that.values[0].length) {
            throw new IllegalArgumentException("The number of rows and columns do not match");
        }

        Matrix result = new Matrix(this.values);


        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                result.values[i][j] = values[i][j] + that.values[i][j];
            }
        }
        return result;
    }

    public void saveToTxt(String path) throws FileNotFoundException {
//        metoda pro ukladani matic do textu
//        format:
//        1.0 2.0
//        3.0 4.0
        try {
//            inicializace writeru
            FileWriter file = new FileWriter(path);
            BufferedWriter buffer = new BufferedWriter(file);

            for (double[] value : values) {
                for (double v : value) {
//                    append hodnoty  a mezery
                    buffer.append(String.valueOf(v)).append(" ");
                }
                buffer.newLine();
            }
            buffer.close();
        } catch (IOException e) {
//            pokud nenalezneme soubor
            throw new FileNotFoundException(path);
        }
    }

    public void loadFromTxt(String path) throws IOException {
//        funkce por nacitani z txt
        List<double[]> matrixList = new ArrayList<>();
//      inicializace readeru primo v try podmince
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
//            cteme dokud neskonci
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                double[] row = Arrays.stream(parts).mapToDouble(Double::parseDouble).toArray();
                matrixList.add(row);
            }
        }
//      return matrix
        this.values = new double[matrixList.size()][];
        for (int i = 0; i < matrixList.size(); i++) {
//            pridavani hodnot do return matice
            this.values[i] = matrixList.get(i);
        }
    }

    public void printMatrix() {
//        optional funkce na print matice v citelnem formatu
        System.out.print("\n");
        if (values == null || values.length == 0 || values[0].length == 0) {
//            matice je prazdna
            System.out.println("Matrix is empty.");
            return;
        }
        for (double[] row : values) {
            System.out.println(Arrays.toString(row));
        }
        System.out.print("\n");
    }

    public void loadFromBinary(String path) throws FileNotFoundException {
//        nacitani matic z binarnich souboru
        try (FileInputStream fis = new FileInputStream(path);
             DataInputStream dis = new DataInputStream(fis)) {
            int width = dis.readInt();
            int height = dis.readInt();
            this.values = new double[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    this.values[i][j] = dis.readDouble();
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }


    public void saveToBinary(String path) throws FileNotFoundException {
//        obdoba save ale za pomoci proudu
        try (FileOutputStream file = new FileOutputStream(path);
//             inicializace writign streamu
             BufferedOutputStream buffer = new BufferedOutputStream(file);
             DataOutputStream dataOut = new DataOutputStream(buffer)) {

            dataOut.writeInt(values.length);
            dataOut.writeInt(values[0].length);

            for (double[] row : values) {
                for (double v : row) {
                    dataOut.writeDouble(v);
                }
            }
        } catch (IOException e) {
//            soubor neni
            throw new FileNotFoundException(path);
        }
    }
}

