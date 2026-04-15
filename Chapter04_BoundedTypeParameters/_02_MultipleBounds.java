/**
 * CHAPTER 4 — BOUNDED TYPE PARAMETERS
 * File 2 of 3: Multiple Bounds & Practical Patterns
 *
 * ═══════════════════════════════════════════════════════════════
 * MULTIPLE BOUNDS
 * ═══════════════════════════════════════════════════════════════
 *
 * A type parameter can require a type to satisfy SEVERAL constraints
 * at once using &:
 *
 *   <T extends ClassA & InterfaceB & InterfaceC>
 *
 * Key rules:
 *   1. Only ONE class is allowed (it must come FIRST).
 *   2. As many interfaces as needed can follow.
 *   3. T will have access to methods from ALL of them.
 *
 * ───────────────────────────────────────────────────────────────
 * PRACTICAL USE CASES
 * ───────────────────────────────────────────────────────────────
 *   • <T extends Comparable<T> & Cloneable>     — comparable and cloneable
 *   • <T extends Number & Comparable<T>>        — numeric and sortable
 *   • <T extends Readable & Closeable>          — readable and auto-closeable
 *
 * ───────────────────────────────────────────────────────────────
 * RECURSIVE BOUNDS (preview — covered fully in Chapter 8)
 * ───────────────────────────────────────────────────────────────
 * The most common "weird-looking" bound you'll encounter:
 *
 *   <T extends Comparable<T>>
 *
 * Read: "T must be comparable to itself."
 * This is needed so that compareTo(T other) accepts T, not Object.
 */

import java.io.Serializable;
import java.util.List;

public class _02_MultipleBounds {

    /**
     * Demonstrate a type that satisfies two interface bounds.
     * Both Number and Comparable<T> methods become available inside.
     */
    public static <T extends Number & Comparable<T>> T clamp(T value, T min, T max) {
        // compareTo() from Comparable<T>
        if (value.compareTo(min) < 0) return min;
        if (value.compareTo(max) > 0) return max;
        return value;
    }

    /**
     * SerializableComparable — a named intersection type via bounds.
     * T must implement both Comparable and Serializable.
     * This is used when you need to both sort AND serialize elements.
     */
    public static <T extends Comparable<T> & Serializable> List<T> sortedCopy(List<T> list) {
        List<T> copy = new java.util.ArrayList<>(list);
        java.util.Collections.sort(copy);
        return copy;
    }

    /**
     * A class demonstrating bounds on the class-level type parameter.
     * SortedBag<T> only accepts Comparable types so it can keep itself sorted.
     */
    static class SortedBag<T extends Comparable<T>> {
        private final List<T> items = new java.util.ArrayList<>();

        public void add(T item) {
            items.add(item);
            java.util.Collections.sort(items);   // sort() works because T extends Comparable<T>
        }

        public T smallest() {
            if (items.isEmpty()) throw new RuntimeException("Bag is empty");
            return items.get(0);
        }

        public T largest() {
            if (items.isEmpty()) throw new RuntimeException("Bag is empty");
            return items.get(items.size() - 1);
        }

        public List<T> asList() { return java.util.Collections.unmodifiableList(items); }

        @Override
        public String toString() { return items.toString(); }
    }

    public static void main(String[] args) {

        // clamp — works for any Number & Comparable (Integer, Double, ...)
        System.out.println("clamp(5, 1, 10):   " + clamp(5, 1, 10));   // 5
        System.out.println("clamp(-3, 1, 10):  " + clamp(-3, 1, 10));  // 1
        System.out.println("clamp(15, 1, 10):  " + clamp(15, 1, 10));  // 10
        System.out.println("clamp(3.5, 1.0, 5.0): " + clamp(3.5, 1.0, 5.0)); // 3.5

        // sortedCopy — String implements Comparable & Serializable
        List<String> unsorted = List.of("banana", "apple", "cherry", "date");
        List<String> sorted   = sortedCopy(unsorted);
        System.out.println("\nUnsorted: " + unsorted);
        System.out.println("Sorted:   " + sorted);

        // SortedBag
        SortedBag<Integer> bag = new SortedBag<>();
        bag.add(5);
        bag.add(2);
        bag.add(8);
        bag.add(1);
        System.out.println("\nSortedBag: " + bag);           // [1, 2, 5, 8]
        System.out.println("Smallest: "  + bag.smallest()); // 1
        System.out.println("Largest: "   + bag.largest());  // 8

        SortedBag<String> wordBag = new SortedBag<>();
        wordBag.add("zebra");
        wordBag.add("ant");
        wordBag.add("monkey");
        System.out.println("\nWord bag: " + wordBag);        // [ant, monkey, zebra]
    }
}
