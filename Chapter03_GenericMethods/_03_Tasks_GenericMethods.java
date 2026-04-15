/**
 * CHAPTER 3 — TASKS: Generic Methods
 *
 * Implement each method below. Use the main() method to test your work.
 * Solutions: Solutions/Chapter03/03_Tasks_GenericMethods_Solution.java
 */

import java.util.ArrayList;
import java.util.List;

public class _03_Tasks_GenericMethods {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — count occurrences
    // ═══════════════════════════════════════════════════════════════
    // Write a static generic method:
    //   public static <T> int countOccurrences(List<T> list, T target)
    //
    // It returns how many times target appears in list using equals().
    //
    // Example: countOccurrences(["a","b","a","c","a"], "a") → 3

    public static <T> int countOccurrences(List<T> list, T target) {
        // TODO
        return 0;
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — filterNonNull
    // ═══════════════════════════════════════════════════════════════
    // Write a static generic method:
    //   public static <T> List<T> filterNonNull(List<T> list)
    //
    // Returns a new list containing only the non-null elements.
    //
    // Example: filterNonNull(["a", null, "b", null, "c"]) → ["a","b","c"]

    public static <T> List<T> filterNonNull(List<T> list) {
        // TODO
        return new ArrayList<>();
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — toArray (simulate)
    // ═══════════════════════════════════════════════════════════════
    // Write a method that converts a List<T> to an Object[] (since
    // you cannot create a generic array T[] directly without tricks).
    //
    //   public static <T> Object[] toObjectArray(List<T> list)
    //
    // Example: toObjectArray(["x","y","z"]) → Object[]{"x","y","z"}

    public static <T> Object[] toObjectArray(List<T> list) {
        // TODO
        return new Object[0];
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 4 — max (using Comparable)
    // ═══════════════════════════════════════════════════════════════
    // Write a generic method that returns the maximum element of a list.
    // Use a bounded type parameter so that T must implement Comparable<T>:
    //
    //   public static <T extends Comparable<T>> T max(List<T> list)
    //
    // Throw IllegalArgumentException if the list is empty.
    //
    // Hint: Comparable<T> provides compareTo(T other). If a.compareTo(b) > 0,
    //       then a > b.
    //
    // Example: max([3, 1, 4, 1, 5, 9, 2]) → 9
    //          max(["banana","apple","cherry"]) → "cherry"

    public static <T extends Comparable<T>> T max(List<T> list) {
        // TODO
        return null;
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // Task 1
        System.out.println("=== Task 1: countOccurrences ===");
        List<String> words = List.of("apple", "banana", "apple", "cherry", "apple");
        System.out.println(countOccurrences(words, "apple"));   // 3
        System.out.println(countOccurrences(words, "banana"));  // 1
        System.out.println(countOccurrences(words, "mango"));   // 0

        List<Integer> nums = List.of(1, 2, 3, 2, 1, 2);
        System.out.println(countOccurrences(nums, 2));           // 3

        // Task 2
        System.out.println("\n=== Task 2: filterNonNull ===");
        List<String> withNulls = new ArrayList<>();
        withNulls.add("a");
        withNulls.add(null);
        withNulls.add("b");
        withNulls.add(null);
        withNulls.add("c");
        System.out.println(filterNonNull(withNulls));  // [a, b, c]

        // Task 3
        System.out.println("\n=== Task 3: toObjectArray ===");
        List<String> letters = List.of("x", "y", "z");
        Object[] arr = toObjectArray(letters);
        System.out.println("Length: " + arr.length);   // 3
        for (Object o : arr) System.out.print(o + " ");
        System.out.println();

        // Task 4
        System.out.println("\n=== Task 4: max ===");
        List<Integer> ints  = List.of(3, 1, 4, 1, 5, 9, 2, 6);
        List<String>  strs  = List.of("banana", "apple", "cherry", "date");
        System.out.println("Max int:    " + max(ints));   // 9
        System.out.println("Max string: " + max(strs));   // date

        try {
            max(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            System.out.println("Empty list exception: " + e.getMessage());
        }
    }
}
