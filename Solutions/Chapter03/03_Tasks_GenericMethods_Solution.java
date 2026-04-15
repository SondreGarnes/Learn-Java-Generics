/**
 * SOLUTIONS — Chapter 3: Generic Methods
 */
import java.util.ArrayList;
import java.util.List;

public class Tasks_GenericMethods_Solution {

    // Task 1
    public static <T> int countOccurrences(List<T> list, T target) {
        int count = 0;
        for (T element : list) {
            if (element.equals(target)) count++;
        }
        return count;
    }

    // Task 2
    public static <T> List<T> filterNonNull(List<T> list) {
        List<T> result = new ArrayList<>();
        for (T element : list) {
            if (element != null) result.add(element);
        }
        return result;
    }

    // Task 3
    public static <T> Object[] toObjectArray(List<T> list) {
        return list.toArray();
    }

    // Task 4
    public static <T extends Comparable<T>> T max(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("List must not be empty");
        T max = list.get(0);
        for (T element : list) {
            if (element.compareTo(max) > 0) max = element;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(countOccurrences(List.of("a","b","a","c","a"), "a")); // 3
        System.out.println(countOccurrences(List.of(1,2,3,2,1,2), 2));           // 3

        List<String> withNulls = new ArrayList<>();
        withNulls.add("a"); withNulls.add(null); withNulls.add("b");
        System.out.println(filterNonNull(withNulls));  // [a, b]

        Object[] arr = toObjectArray(List.of("x","y","z"));
        System.out.println(arr.length);                // 3

        System.out.println(max(List.of(3,1,4,1,5,9,2,6)));               // 9
        System.out.println(max(List.of("banana","apple","cherry")));      // cherry
    }
}
