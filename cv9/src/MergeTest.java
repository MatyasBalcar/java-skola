import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeTest {

    @Test
    void testMergeSortValid() {
        List<Integer> l = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(9);

        l.add(1);
        l.add(3);
        l.add(9);
        l.add(2);

        l = Main.mergeSort(l, false);
        assertEquals(expected, l);
    }

    @Test
    void testMergeSortInvalid() {
        List<Integer> l = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(9);
        expected.add(3);

        l.add(1);
        l.add(3);
        l.add(9);
        l.add(2);

        l = Main.mergeSort(l, false);
        assertNotEquals(expected, l);
    }
    @Test
    void testMerge(){
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> l3;
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(5);
        expected.add(7);
        l1.add(1);
        l1.add(2);
        l1.add(5);
        l2.add(3);
        l2.add(7);

        l3 = Main.merge(l1,l2, false);


        assertEquals(expected, l3);
    }

}
