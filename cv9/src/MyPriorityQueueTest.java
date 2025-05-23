import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyPriorityQueueTest {
    @Test
    public void orderTest(){
        MyPriorityQueue<Task> q = new MyPriorityQueue<>(2);
        q.add(new Task("Task 1", Priority.HIGH));
        q.add(new Task("Task 2", Priority.LOW));
        q.add(new Task("Task 3", Priority.NORMAL));

        assertEquals(Priority.HIGH, q.remove().getPriority());
        assertEquals(Priority.NORMAL, q.remove().getPriority());
        assertEquals(Priority.LOW, q.remove().getPriority());
    }

}
