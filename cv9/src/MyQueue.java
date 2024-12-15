import java.util.Iterator;

public class MyQueue<T> implements Iterable<T> {
    private final T[] array;
    private int head;
    private int tail;
    private int count;

    public MyQueue(int capacity) {
        array = (T[]) new Object[capacity];
        head = 0;
        tail = 0;
        count = 0;
    }

    public void add(T item) {
        if (count == array.length) {
            throw new IllegalStateException("Fronta je plná");
        }
        array[tail] = item;
        tail = (tail + 1) % array.length;
        count++;
    }

    public T remove() {
        if (count == 0) {
            throw new IllegalStateException("Fronta je prázdná");
        }
        T item = array[head];
        array[head] = null;
        head = (head + 1) % array.length;
        count--;
        return item;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyQueueIterator();
    }

    // Vnořená třída implementující iterátor
    private class MyQueueIterator implements Iterator<T> {
        private int currentIndex;
        private int elementsIterated;

        public MyQueueIterator() {
            this.currentIndex = head; // Začínáme na pozici "head"
            this.elementsIterated = 0; // Počet iterovaných prvků
        }

        @Override
        public boolean hasNext() {
            return elementsIterated < count;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("Žádné další prvky");
            }
            T item = array[currentIndex];
            currentIndex = (currentIndex + 1) % array.length;
            elementsIterated++;
            return item;
        }
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<>(5);

        queue.add(10);
        queue.add(20);
        queue.add(30);

        System.out.println("Obsah fronty pomocí for-each:");
        for (Integer num : queue) {
            System.out.println(num);
        }

        System.out.println("Obsah fronty po iteraci (fronta zůstává beze změny):");
        for (Integer num : queue) {
            System.out.println(num);
        }
    }
}
