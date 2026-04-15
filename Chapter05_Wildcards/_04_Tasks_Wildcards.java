/**
 * CHAPTER 5 — TASKS: Wildcards
 *
 * Solutions: Solutions/Chapter05/04_Tasks_Wildcards_Solution.java
 */

import java.util.ArrayList;
import java.util.List;

public class _04_Tasks_Wildcards {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — printAll (unbounded wildcard)
    // ═══════════════════════════════════════════════════════════════
    // Write a method that accepts ANY list and prints each element
    // on its own line, prefixed with its index:
    //
    //   public static void printAll(List<?> list)
    //
    // Example output for List.of("a","b","c"):
    //   0: a
    //   1: b
    //   2: c

    public static void printAll(List<?> list) {
        // TODO
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — sumNumbers (upper bounded wildcard)
    // ═══════════════════════════════════════════════════════════════
    // Write a method using an upper-bounded wildcard that returns
    // the sum of all elements as a double:
    //
    //   public static double sumNumbers(List<? extends Number> list)
    //
    // Example: sumNumbers([1, 2, 3]) → 6.0
    //          sumNumbers([1.1, 2.2]) → 3.3

    public static double sumNumbers(List<? extends Number> list) {
        // TODO
        return 0;
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — insertIntegers (lower bounded wildcard)
    // ═══════════════════════════════════════════════════════════════
    // Write a method that inserts a range of integers [from..to] into
    // any list that can hold Integer or a supertype:
    //
    //   public static void insertIntegers(List<? super Integer> list, int from, int to)
    //
    // Example: insertIntegers(list, 3, 6) adds [3, 4, 5, 6] to list

    public static void insertIntegers(List<? super Integer> list, int from, int to) {
        // TODO
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 4 — transfer (PECS — both wildcards)
    // ═══════════════════════════════════════════════════════════════
    // Write a generic method that copies all elements from a source
    // list to a destination list, but only elements where toString()
    // contains a given substring. Use proper PECS wildcards.
    //
    //   public static <T> void transferMatching(
    //       List<? extends T> source,
    //       List<? super T> destination,
    //       String substring)
    //
    // Example:
    //   source      = ["apple", "apricot", "banana", "avocado"]
    //   destination = []
    //   substring   = "ap"
    //   result: destination = ["apple", "apricot"]

    public static <T> void transferMatching(
            List<? extends T> source,
            List<? super T> destination,
            String substring) {
        // TODO
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // Task 1
        System.out.println("=== Task 1: printAll ===");
        printAll(List.of("alpha", "beta", "gamma"));
        printAll(List.of(10, 20, 30));

        // Task 2
        System.out.println("\n=== Task 2: sumNumbers ===");
        System.out.println(sumNumbers(List.of(1, 2, 3, 4, 5)));      // 15.0
        System.out.println(sumNumbers(List.of(1.1, 2.2, 3.3)));      // 6.6
        System.out.println(sumNumbers(List.of(100L, 200L)));         // 300.0

        // Task 3
        System.out.println("\n=== Task 3: insertIntegers ===");
        List<Integer> ints = new ArrayList<>();
        insertIntegers(ints, 1, 5);
        System.out.println("Ints: " + ints);   // [1, 2, 3, 4, 5]

        List<Number> nums = new ArrayList<>();
        insertIntegers(nums, 10, 13);
        System.out.println("Nums: " + nums);   // [10, 11, 12, 13]

        List<Object> objs = new ArrayList<>();
        insertIntegers(objs, 7, 9);
        System.out.println("Objs: " + objs);   // [7, 8, 9]

        // Task 4
        System.out.println("\n=== Task 4: transferMatching ===");
        List<String> fruits = List.of("apple", "apricot", "banana", "avocado", "cherry");
        List<String> matched = new ArrayList<>();
        transferMatching(fruits, matched, "ap");
        System.out.println("Matched 'ap': " + matched);  // [apple, apricot]

        List<Object> objs2 = new ArrayList<>();
        transferMatching(fruits, objs2, "an");
        System.out.println("Matched 'an': " + objs2);    // [banana]
    }
}
