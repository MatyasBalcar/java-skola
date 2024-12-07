import java.util.ArrayList;
import java.util.List;

public class MyList {
    private Object[] items;
    private int size;

    public MyList(int capacity) {
        items = new Object[capacity];
        size = 0;
    }

    public void add(Object item) {
        if (size == items.length) {
            Object[] newArray = new Object[items.length * 2];
            for (int i = 0; i < items.length; i++)
                newArray[i] = items[i];
            items = newArray;
        }
        items[size++] = item;
    }

    public Object get(int index) {
        return items[index];
    }

    public int size() {
        return size;
    }

    public String toString() {
        StringBuilder res = new StringBuilder("[");
        for(int i = 0; i < size - 1; i++){
            res.append(items[i]).append(", ");
        }
        if (size > 0){
            res.append(items[size - 1]);
        }
        res.append("]");
        return res.toString();
    }

}
