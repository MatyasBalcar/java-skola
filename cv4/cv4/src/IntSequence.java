public abstract class IntSequence {
    public abstract int get(int index);
    public abstract void printList();
    public abstract void append(int value);
    public abstract int length();

    public boolean isEmpty() {
        return length() == 0;
    }
}
