import java.util.*;

public class Main {
    public static <T> MyList locate(MyList list, T item) {
        MyList indices = new MyList(list.size());
        for (int i = 0; i < list.size(); i++) {
            if (item.equals(list.get(i))) {
                indices.add(i);
            }
        }
        return indices;
    }

    public static <T extends Number & Comparable<T>> MyList selectLowerNumbers(MyList list, T item) {
        MyList result = new MyList(list.size());
        for (int i = 0; i < list.size(); i++) {
            T currentItem = (T) list.get(i);
            if (currentItem.compareTo(item) < 0) {
                result.add(currentItem);
            }
        }
        return result;
    }

    //    ukol pro cv 10
    public static List<Object> odd(List<Object> input) {
        List<Object> result = new ArrayList<>();
        for (Object o : input) {
            if (o.getClass() == Integer.class) {
                Integer num = (Integer) o;
                if (num % 2 == 0) {
                    result.add(num);
                }
            }
        }
        return result;
    }

    //    ukol pro cv 10
    public static List<Integer> oddNumbers(List<Integer> input) {
        List<Integer> result = new ArrayList<>();
        for (Integer i : input) {
            if (i % 2 == 0) {
                result.add(i);
            }
        }
        return result;
    }

    //    ukol pro cv 10
    public static boolean hasDuplicates(Collection<?> collection) {
        //* python ahh reseni
        Set<Object> set = new HashSet<>();
        for (Object item : collection) {
            if (!set.add(item)) {
                return true;
            }
        }
        return false;
    }

    //    ukol pro cv 10
    public static Dictionary<String, Integer> frequencies(String s) {
        Dictionary<String, Integer> dict = new Hashtable<>();
        for (int i = 0; i < s.length(); i++) {
            try{
                dict.put(s.substring(i, i + 1), dict.get(s.substring(i, i + 1)) + 1);
            }catch (NullPointerException e) {
                dict.put(s.substring(i, i + 1), 1);
            }

        }
        return dict;
    }

    public static void main(String[] args) {
//        MyPriorityQueue<Prioritizable> queue = new MyPriorityQueue<>(3);
//
//        queue.add(new Task("Task 1", Priority.HIGH));
//        queue.add(new Task("Task 2", Priority.NORMAL));
//        queue.add(new Task("Task 3", Priority.LOW));
//        queue.add(new Task("Task 4", Priority.HIGH));
//
//        while (!queue.isEmpty()) {
//            System.out.println(queue.remove());
//        }

        MyList listString = new MyList(3);
        listString.add("Task 1");
        listString.add("Task 2");
        listString.add("Task 1");
        System.out.printf(locate(listString, "Task 1") + "");

        MyList listInteger = new MyList(3);
        listInteger.add(1);
        listInteger.add(2);
        listInteger.add(1);
        System.out.printf(locate(listString, 2) + "");

        System.out.printf(selectLowerNumbers(listInteger, 2) + "");

        System.out.println(frequencies("abcdefghabc"));

        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        l1.add(1);
        l2.add(2);
        l1.add(3);
        l2.add(4);
        l1.add(5);
        l2.add(6);

    }
}