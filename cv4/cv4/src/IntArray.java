import java.util.Arrays;

public class IntArray extends IntSequence {
    private int[] values;

    public IntArray(int... values) {
        this.values = values;
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= values.length) { // Check if index is valid
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + values.length);
        }
        return values[index];
    }

    @Override
    public void printList() {
        for (int value : values) {
            System.out.println(value);
        }
    }

    @Override
    public void append(int value) {
        values = Arrays.copyOf(values, values.length + 1);
        values[values.length - 1] = value;
    }

    @Override
    public int length() {
        return values.length;
    }

    public boolean equals(IntArray arr){
        return Arrays.equals(values, arr.values);
    }

    public String toString() {
        return Arrays.toString(values);
    }
}
