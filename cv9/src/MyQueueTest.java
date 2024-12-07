import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyQueueTest {
    @Test
    public void insertDeleteTest(){
        MyQueue<Integer> q = new MyQueue<>(3);
        assertTrue(q.isEmpty());

        q.add(1);
        q.add(2);
        q.add(3);

        assertFalse(q.isEmpty());
        assertEquals(3, q.size());
        assertEquals(1, (int) q.remove());
        assertEquals(2, (int) q.remove());
        assertEquals(3, (int) q.remove());
        assertTrue(q.isEmpty());
    }

    @Test
    public void tooLargeErrorTest(){
        MyQueue<Integer> q = new MyQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        assertThrows(IllegalStateException.class, () -> q.add(3));
    }

    @Test
    public void tooSmallErrorTest() {
        MyQueue<Integer> q = new MyQueue<>(3);
        q.add(1);
        q.remove();
        assertThrows(IllegalStateException.class, q::remove);
    }
}
