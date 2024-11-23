import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Matrix {
//  n*n array reprezentujici matice, stejny pocet sloupcu jako radku
    double[][] values;

    public Matrix(double[][] values) throws IllegalArgumentException {
        if(values.length != values[0].length){
//          kdyz pocet sloupcu neni stejny jako pocet radku, hodime error
            throw new IllegalArgumentException("The number of rows and columns do not match");
        }else{
            this.values = values;
        }
    }

    public Matrix add(Matrix that) throws IllegalArgumentException {
        if(that.values.length != that.values[0].length){
//            teoreticky by tu to byt nemuselo, protoze to chekujeme u tvoreni, ale pro jistotu to tady dam, at se to
//            kdyztak nepokazi
            throw new IllegalArgumentException("The number of rows and columns do not match");
        }
//        result matrix na returnuti
        Matrix result = new Matrix(this.values);

        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < values[i].length; j++){
//                scitani jednotlivych prvku
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
        try{
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
        }
        catch (IOException e){
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
        if (values == null || values.length == 0 || values[0].length == 0)  {
//            matice je prazdna
            System.out.println("Matrix is empty.");
            return;
        }
        for (double[] row : values) {
            System.out.println(Arrays.toString(row));
        }
        System.out.print("\n");
    }

    public void saveToBinary(String path) throws FileNotFoundException {
//        funkce pro ukladani matic do binarnich souboru
        try {
//            inicializace writeru
            FileWriter file = new FileWriter(path);
            BufferedWriter buffer = new BufferedWriter(file);

//            dimenze
            buffer.append(String.valueOf(values.length)).append(" ");
            buffer.append(String.valueOf(values[0].length)).append("\n");

//            zapsani dat
            for (double[] row : values) {
                for (double v : row) {
                    buffer.append(String.valueOf(v)).append(" ");
                }
                buffer.append("\n"); // Newline after each row
            }
            buffer.close();
        } catch (IOException e) {
//            soubor neexistuje
            throw new FileNotFoundException(path);
        }
    }

    public void loadFromBinary(String path) throws FileNotFoundException {
//        nacitani matic z binarnich souboru
        try {
//            inicializace readeru
            FileReader file = new FileReader(path);
            BufferedReader buffer = new BufferedReader(file);
            Scanner scanner = new Scanner(buffer);
//            ziskani dimenzi
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
//            return matice
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
//            soubor nebyl nalezen
            throw new FileNotFoundException(path);
        }
    }


    public void saveToBinaryStream(String path) throws FileNotFoundException {
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

