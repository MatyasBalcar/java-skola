import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void testMatrixCreationValid() {
        double[][] values = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        assertDoesNotThrow(() -> new Matrix(values));
    }

    @Test
    void testMatrixCreationInvalid() {
        double[][] invalidValues = {
                {1.0, 2.0},
                {3.0, 4.0, 5.0}
        };
        assertThrows(IllegalArgumentException.class, () -> new Matrix(invalidValues));
    }

    @Test
    void testAddValid() {
        double[][] values1 = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        double[][] values2 = {
                {5.0, 6.0},
                {7.0, 8.0}
        };
        Matrix m1 = new Matrix(values1);
        Matrix m2 = new Matrix(values2);

        Matrix result = m1.add(m2);

        double[][] expected = {
                {6.0, 8.0},
                {10.0, 12.0}
        };
        assertArrayEquals(expected, result.values);
    }

    @Test
    void testAddInvalid() {
        double[][] values1 = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        double[][] values2 = {
                {5.0}
        };
        Matrix m1 = new Matrix(values1);
        Matrix m2 = new Matrix(values2);

        assertThrows(IllegalArgumentException.class, () -> m1.add(m2));
    }

    @Test
    void testSaveToTxtAndLoadFromTxt() throws IOException {
        double[][] values = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        Matrix matrix = new Matrix(values);
        String path = "test_matrix.txt";

        matrix.saveToTxt(path);

        Matrix loadedMatrix = new Matrix(new double[2][2]);
        loadedMatrix.loadFromTxt(path);

        assertArrayEquals(values, loadedMatrix.values);

        // Clean up
        new File(path).delete();
    }

    @Test
    void testSaveToBinaryAndLoadFromBinary() throws IOException {
        double[][] values = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        Matrix matrix = new Matrix(values);
        String path = "test_matrix.bin";

        matrix.saveToBinary(path);

        Matrix loadedMatrix = new Matrix(new double[2][2]);
        loadedMatrix.loadFromBinary(path);

        assertArrayEquals(values, loadedMatrix.values);

        // Clean up
        new File(path).delete();
    }

    @Test
    void testSaveToBinaryStreamAndLoadFromBinary() throws IOException {
        double[][] values = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        Matrix matrix = new Matrix(values);
        String path = "test_matrix_stream.bin";

        matrix.saveToBinary(path);

        Matrix loadedMatrix = new Matrix(new double[2][2]);
        loadedMatrix.loadFromBinary(path);

        assertArrayEquals(values, loadedMatrix.values);

        // Clean up
        new File(path).delete();
    }
}
