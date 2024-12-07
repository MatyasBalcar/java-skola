class MyQueue<T> {
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
}