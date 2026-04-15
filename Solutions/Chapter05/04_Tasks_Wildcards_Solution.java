/**
 * SOLUTIONS — Chapter 5: Wildcards
 */
import java.util.ArrayList;
import java.util.List;

public class Tasks_Wildcards_Solution {

    // Task 1
    public static void printAll(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i));
        }
    }

    // Task 2
    public static double sumNumbers(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) sum += n.doubleValue();
        return sum;
    }

    // Task 3
    public static void insertIntegers(List<? super Integer> list, int from, int to) {
        for (int i = from; i <= to; i++) list.add(i);
    }

    // Task 4
    public static <T> void transferMatching(
            List<? extends T> source,
            List<? super T> destination,
            String substring) {
        for (T element : source) {
            if (element.toString().contains(substring)) {
                destination.add(element);
            }
        }
    }

    public static void main(String[] args) {
        printAll(List.of("alpha","beta","gamma"));
        System.out.println(sumNumbers(List.of(1,2,3,4,5)));   // 15.0
        System.out.println(sumNumbers(List.of(1.1,2.2,3.3))); // 6.6

        List<Integer> ints = new ArrayList<>();
        insertIntegers(ints, 1, 5);
        System.out.println(ints);   // [1, 2, 3, 4, 5]

        List<String> fruits = List.of("apple","apricot","banana","avocado","cherry");
        List<String> matched = new ArrayList<>();
        transferMatching(fruits, matched, "ap");
        System.out.println(matched);  // [apple, apricot]
    }
}
