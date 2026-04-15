/**
 * CHAPTER 6 — TASKS: Generic Interfaces
 *
 * Solutions: Solutions/Chapter06/02_Tasks_GenericInterfaces_Solution.java
 */

import java.util.List;
import java.util.ArrayList;

public class Tasks_GenericInterfaces {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — Implement Comparable in a generic class
    // ═══════════════════════════════════════════════════════════════
    // Create a generic class Box<T extends Comparable<T>> that
    // implements Comparable<Box<T>>. Two boxes are compared by
    // comparing their contained values.
    //
    // The class should also have:
    //   getValue()  — returns the stored value
    //   toString()  — "Box[value]"
    //
    // Example:
    //   Box<Integer> a = new Box<>(3);
    //   Box<Integer> b = new Box<>(7);
    //   a.compareTo(b)  →  negative (3 < 7)
    //   Collections.sort(list of boxes) should work

    static class Box<T extends Comparable<T>> implements Comparable<Box<T>> {
        // TODO: field, constructor, getValue, toString, compareTo
        public T getValue() { return null; } // replace

        @Override
        public int compareTo(Box<T> other) {
            return 0; // replace
        }

        @Override
        public String toString() { return "Box[" + getValue() + "]"; }
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — Define and implement a Converter<F, T> interface
    // ═══════════════════════════════════════════════════════════════
    // Define a generic interface:
    //   interface Converter<F, T> {
    //       T convert(F from);
    //   }
    //
    // Then implement:
    //   a) StringToInteger: converts String → Integer using Integer.parseInt
    //   b) IntegerToString: converts Integer → String using String.valueOf
    //   c) A method:
    //        public static <F, T> List<T> convertAll(List<F> items, Converter<F, T> conv)
    //      that applies the converter to every element and returns the results.

    interface Converter<F, T> {
        // TODO: declare the convert method
    }

    static class StringToInteger implements Converter<String, Integer> {
        // TODO
    }

    static class IntegerToString implements Converter<Integer, String> {
        // TODO
    }

    public static <F, T> List<T> convertAll(List<F> items, Converter<F, T> conv) {
        // TODO
        return new ArrayList<>();
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — Generic Predicate interface + combinator
    // ═══════════════════════════════════════════════════════════════
    // Define a functional interface:
    //   interface Predicate<T> {
    //       boolean test(T value);
    //
    //       default Predicate<T> and(Predicate<T> other) { ... }
    //       default Predicate<T> or(Predicate<T> other)  { ... }
    //       default Predicate<T> negate()                { ... }
    //   }
    //
    // and() returns a predicate that is true only if BOTH are true.
    // or()  returns a predicate that is true if EITHER is true.
    // negate() inverts the predicate.
    //
    // Then write:
    //   public static <T> List<T> filter(List<T> list, Predicate<T> predicate)
    // which returns only the elements that satisfy the predicate.

    interface Predicate<T> {
        boolean test(T value);

        default Predicate<T> and(Predicate<T> other) {
            // TODO
            return null;
        }

        default Predicate<T> or(Predicate<T> other) {
            // TODO
            return null;
        }

        default Predicate<T> negate() {
            // TODO
            return null;
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        // TODO
        return new ArrayList<>();
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // Task 1
        System.out.println("=== Task 1: Comparable Box ===");
        Box<Integer> a = new Box<>(); // TODO: adjust constructor once implemented
        // Construct boxes properly after implementing the constructor
        // For now this tests compilation — add the values in your implementation
        List<Box<Integer>> boxes = new ArrayList<>();
        // boxes.add(new Box<>(5));
        // boxes.add(new Box<>(1));
        // boxes.add(new Box<>(3));
        // java.util.Collections.sort(boxes);
        // System.out.println(boxes);  // [Box[1], Box[3], Box[5]]

        // Task 2
        System.out.println("=== Task 2: Converter ===");
        StringToInteger toInt = new StringToInteger();
        IntegerToString toStr = new IntegerToString();

        System.out.println(toInt.convert("123"));    // 123
        System.out.println(toStr.convert(456));      // "456"

        List<String> strNums = List.of("1", "2", "3", "4");
        List<Integer> converted = convertAll(strNums, toInt);
        System.out.println("Converted: " + converted);  // [1, 2, 3, 4]

        // Task 3
        System.out.println("=== Task 3: Predicate ===");
        Predicate<Integer> isEven    = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;

        List<Integer> nums = List.of(-4, -3, -2, -1, 0, 1, 2, 3, 4);

        System.out.println("Even:             " + filter(nums, isEven));
        System.out.println("Positive:         " + filter(nums, isPositive));
        System.out.println("Even and positive:" + filter(nums, isEven.and(isPositive)));
        System.out.println("Even or negative: " + filter(nums, isEven.or(isPositive.negate())));
        System.out.println("Not even:         " + filter(nums, isEven.negate()));
    }
}
