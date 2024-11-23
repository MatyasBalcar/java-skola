import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        double[][] test_matrix = new double[][]{{1,2,3}, {4,5,6}, {7,8,9}};
        Matrix matice = new Matrix(test_matrix);

        System.out.println("Should print a 1-9 in a 3*3 matrix , then an empty one followed by the same original matrix");
        try{
            matice.saveToTxt("./test.txt");
        }
        catch(FileNotFoundException e){
            System.out.println("Soubor neni :(");
        }
        matice.printMatrix();
        matice.values = new double[][]{{}};
        matice.printMatrix();
        try{
            matice.loadFromTxt("./test.txt");
            matice.printMatrix();
        } catch (IOException e) {
            System.out.println("Soubor neni :(");
        }


        System.out.println("Should print a 1-9 in a 3*3 matrix , then an empty one followed by the same original matrix");
        try{
            matice.saveToBinary("./test.b");
        }
        catch(FileNotFoundException e){
            System.out.println("Soubor neni :(");
        }
        matice.printMatrix();
        matice.values = new double[][]{{}};
        matice.printMatrix();
        try{
            matice.loadFromBinary("./test.b");
            matice.printMatrix();
        }catch(FileNotFoundException e){
            System.out.println("Soubor neni :(");
        }


        try{
            matice.saveToBinaryStream("./test_stream.b");
        }
        catch(FileNotFoundException e){
            System.out.println("Soubor neni :(");
        }


    }
}