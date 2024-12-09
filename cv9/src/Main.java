import org.jetbrains.annotations.NotNull;

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
// pro ukol c 10

    public static List<Integer> merge(List<Integer> list1, List<Integer> list2, boolean useLinkedList) {
        List<Integer> merged;
        if (useLinkedList){
            merged = new LinkedList<>();
        }else{
            merged = new ArrayList<>();
        }

        int i = 0, j = 0;

        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) <= list2.get(j)) {
                merged.add(list1.get(i));
                i++;
            } else {
                merged.add(list2.get(j));
                j++;
            }
        }

        while (i < list1.size()) {
            merged.add(list1.get(i));
            i++;
        }

        while (j < list2.size()) {
            merged.add(list2.get(j));
            j++;
        }

        return merged;
    }
    // pro ukol c 10
    public static List<Integer> mergeSort(List<Integer> list, boolean useLinkedList) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Integer> left = list.subList(0, mid);
        List<Integer> right = list.subList(mid, list.size());

        return merge(mergeSort(left, useLinkedList), mergeSort(right, useLinkedList), false);
    }
    public static void main(String[] args) {
        List<Integer> linked = new LinkedList<>();
        List<Integer> array= new ArrayList<>();

        Random random = new Random();

        int LIMIT = 10000;
        for (int i = 0; i < LIMIT; i++) {
            int curr = random.nextInt(LIMIT);
            linked.add(curr);
            array.add(curr);
        }

        long startLinked  = System.currentTimeMillis();
        linked = mergeSort(linked, true);
        long endLinked  = System.currentTimeMillis();

        long startArray = System.currentTimeMillis();
        array = mergeSort(array, false);
        long endArray = System.currentTimeMillis();

        System.out.println("Linked time in ms:");
        System.out.println(endLinked - startLinked); //77
        System.out.println("Array time in ms:");
        System.out.println(endArray - startArray); //7 asi 10 * rychlejsi kdyz pouzijeme array (da se cekat splitujeme to a berenm i te prvky)
    }
}