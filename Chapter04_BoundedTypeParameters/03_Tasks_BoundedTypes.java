/**
 * CHAPTER 4 — TASKS: Bounded Type Parameters
 *
 * Solutions: Solutions/Chapter04/03_Tasks_BoundedTypes_Solution.java
 */

import java.util.List;
import java.util.ArrayList;

public class Tasks_BoundedTypes {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — average
    // ═══════════════════════════════════════════════════════════════
    // Write a static method that computes the average of a list of numbers:
    //
    //   public static <T extends Number> double average(List<T> list)
    //
    // Use doubleValue() to sum the elements.
    // Throw IllegalArgumentException if the list is empty.
    //
    // Example: average([1, 2, 3, 4, 5]) → 3.0
    //          average([1.5, 2.5])       → 2.0

    public static <T extends Number> double average(List<T> list) {
        // TODO
        return 0;
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — isSorted
    // ═══════════════════════════════════════════════════════════════
    // Write a method that returns true if the list is in ascending order:
    //
    //   public static <T extends Comparable<T>> boolean isSorted(List<T> list)
    //
    // An empty list or single-element list is considered sorted.
    //
    // Example: isSorted([1, 2, 3, 4]) → true
    //          isSorted([1, 3, 2, 4]) → false
    //          isSorted(["a","b","c"]) → true

    public static <T extends Comparable<T>> boolean isSorted(List<T> list) {
        // TODO
        return false;
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — minMax
    // ═══════════════════════════════════════════════════════════════
    // Write a method that returns a 2-element array [min, max]:
    //
    //   public static <T extends Comparable<T>> T[] minMax(List<T> list)
    //
    // Because we can't create a generic array directly, return Object[]
    // and cast inside main when you test. Or use a Pair class.
    //
    // Alternatively, print min and max separately from the method.
    // Simplest approach: return a simple wrapper or just print.
    //
    // Implement it as:
    //   public static <T extends Comparable<T>> void printMinMax(List<T> list)
    //
    // Example: printMinMax([5, 3, 9, 1, 7]) prints Min: 1  Max: 9

    public static <T extends Comparable<T>> void printMinMax(List<T> list) {
        // TODO
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 4 — BoundedPair
    // ═══════════════════════════════════════════════════════════════
    // Create a class BoundedPair<T extends Comparable<T>> that holds
    // two values of the same Comparable type and provides:
    //   min()  — the smaller of the two
    //   max()  — the larger of the two
    //   range(other BoundedPair) — true if this pair's range overlaps with other's
    //              (i.e., this.min <= other.max && other.min <= this.max)

    static class BoundedPair<T extends Comparable<T>> {
        private final T a;
        private final T b;

        public BoundedPair(T a, T b) {
            this.a = a;
            this.b = b;
        }

        // TODO: min()
        public T min() {
            return null; // replace
        }

        // TODO: max()
        public T max() {
            return null; // replace
        }

        // TODO: overlaps(BoundedPair<T> other)
        public boolean overlaps(BoundedPair<T> other) {
            return false; // replace
        }

        @Override
        public String toString() {
            return "[" + min() + ", " + max() + "]";
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // Task 1
        System.out.println("=== Task 1: average ===");
        System.out.println(average(List.of(1, 2, 3, 4, 5)));        // 3.0
        System.out.println(average(List.of(1.5, 2.5, 3.0)));        // 2.3333...
        System.out.println(average(List.of(10)));                    // 10.0
        try { average(new ArrayList<>()); }
        catch (IllegalArgumentException e) { System.out.println("Exception: " + e.getMessage()); }

        // Task 2
        System.out.println("\n=== Task 2: isSorted ===");
        System.out.println(isSorted(List.of(1, 2, 3, 4)));          // true
        System.out.println(isSorted(List.of(1, 3, 2, 4)));          // false
        System.out.println(isSorted(List.of("ant", "bee", "cat"))); // true
        System.out.println(isSorted(List.of(5)));                   // true
        System.out.println(isSorted(new ArrayList<>()));             // true

        // Task 3
        System.out.println("\n=== Task 3: printMinMax ===");
        printMinMax(List.of(5, 3, 9, 1, 7));         // Min: 1  Max: 9
        printMinMax(List.of("zebra","ant","monkey")); // Min: ant  Max: zebra

        // Task 4
        System.out.println("\n=== Task 4: BoundedPair ===");
        BoundedPair<Integer> pair1 = new BoundedPair<>(3, 7);
        BoundedPair<Integer> pair2 = new BoundedPair<>(5, 10);
        BoundedPair<Integer> pair3 = new BoundedPair<>(8, 15);

        System.out.println("pair1: " + pair1);                        // [3, 7]
        System.out.println("pair2: " + pair2);                        // [5, 10]
        System.out.println("pair1 overlaps pair2: " + pair1.overlaps(pair2)); // true
        System.out.println("pair1 overlaps pair3: " + pair1.overlaps(pair3)); // false
        System.out.println("pair2 overlaps pair3: " + pair2.overlaps(pair3)); // true
    }
}
