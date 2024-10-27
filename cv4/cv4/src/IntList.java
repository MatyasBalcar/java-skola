public class IntList extends IntSequence {
    private IntNode head;

    public IntList() {
        this.head = null;
    }

    @Override
    public void append(int value) {
        IntNode newNode = new IntNode(value);
        if (head == null) {
            head = newNode;
        } else {
            IntNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    @Override
    public void printList() {
        IntNode current = head;
        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    @Override
    public int get(int index) {
        if (index < 0 || index >= length()) { // Check if index is valid
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length());
        }
        IntNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int length() {
        int length = 0;
        IntNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    private class IntNode {
        int value;
        IntNode next;

        public IntNode(int value) {
            this.value = value;
            this.next = null;
        }
    }
    public boolean equals(IntList l) {

        if (this.length() != l.length()) {
            return false;
        }

        IntNode currentA = this.head;
        IntNode currentB = l.head;

        while (currentA != null && currentB != null) {
            if (currentA.value != currentB.value) {
                return false;
            }
            currentA = currentA.next;
            currentB = currentB.next;
        }

        return true;
    }

    public String toString(){
        String result = "[";
        IntNode current = this.head;
        while (current != null) {
            result += current.value + " ";
            current = current.next;
        }
        result += "]";
        return result;
    }
}
