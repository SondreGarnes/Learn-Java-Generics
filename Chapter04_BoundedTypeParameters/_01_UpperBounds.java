/**
 * CHAPTER 4 — BOUNDED TYPE PARAMETERS
 * File 1 of 3: Upper Bounds (extends)
 *
 * ═══════════════════════════════════════════════════════════════
 * WHAT ARE BOUNDED TYPE PARAMETERS?
 * ═══════════════════════════════════════════════════════════════
 *
 * By default, T can be ANY type. A bound restricts T to a specific
 * family of types — those that extend/implement a given class or interface.
 *
 * UPPER BOUND syntax:
 *
 *   <T extends SomeClass>
 *   <T extends SomeInterface>
 *
 * "T extends X" means T must be X itself, or a subclass/implementor of X.
 *
 * ───────────────────────────────────────────────────────────────
 * WHY USE AN UPPER BOUND?
 * ───────────────────────────────────────────────────────────────
 * When your method/class needs to CALL METHODS on T, you must tell
 * the compiler what those methods are. The bound provides the contract.
 *
 *   // Without bound — can't call .compareTo():
 *   <T> T min(T a, T b) { return a.compareTo(b) < 0 ? a : b; }  // ERROR
 *
 *   // With bound — .compareTo() is guaranteed by Comparable<T>:
 *   <T extends Comparable<T>> T min(T a, T b) { ... }            // OK
 *
 * ───────────────────────────────────────────────────────────────
 * MULTIPLE BOUNDS
 * ───────────────────────────────────────────────────────────────
 * T can implement multiple interfaces (and extend one class):
 *
 *   <T extends Animal & Runnable & Serializable>
 *
 * Rules:
 *   • At most ONE class in the bound (and it must come first).
 *   • Any number of interfaces after the &.
 */

import java.util.List;

public class _01_UpperBounds {

    /**
     * sum — adds all elements in a list of any Number subtype
     * (Integer, Double, Long, Float, BigDecimal, ...).
     * Without <T extends Number>, we couldn't call .doubleValue().
     */
    public static <T extends Number> double sum(List<T> list) {
        double total = 0;
        for (T element : list) {
            total += element.doubleValue();  // method from Number
        }
        return total;
    }

    /**
     * min — returns the smallest element.
     * Comparable<T> provides compareTo(); the bound unlocks it.
     */
    public static <T extends Comparable<T>> T min(T a, T b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * minOfList — finds the minimum in a list using the same bound.
     */
    public static <T extends Comparable<T>> T minOfList(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("Empty list");
        T result = list.get(0);
        for (T element : list) {
            if (element.compareTo(result) < 0) {
                result = element;
            }
        }
        return result;
    }

    /**
     * Demonstrates a generic class with an upper bound.
     * NumberBox<T extends Number> can safely call Number methods on T.
     */
    static class NumberBox<T extends Number> {
        private T value;

        public NumberBox(T value) { this.value = value; }

        public T getValue()         { return value; }
        public int    intValue()    { return value.intValue(); }
        public double doubleValue() { return value.doubleValue(); }

        public boolean isZero() { return value.doubleValue() == 0.0; }

        @Override
        public String toString() {
            return "NumberBox[" + value + "]";
        }
    }

    public static void main(String[] args) {

        // sum — works with Integer, Double, etc.
        List<Integer> ints    = List.of(1, 2, 3, 4, 5);
        List<Double>  doubles = List.of(1.1, 2.2, 3.3);

        System.out.println("Sum of ints:    " + sum(ints));     // 15.0
        System.out.println("Sum of doubles: " + sum(doubles));  // 6.6

        // min
        System.out.println("Min(3,7): " + min(3, 7));           // 3
        System.out.println("Min(\"apple\",\"banana\"): " + min("apple", "banana")); // apple

        // minOfList
        List<String> words = List.of("cherry", "apple", "date", "banana");
        System.out.println("Min word: " + minOfList(words));     // apple

        // NumberBox
        NumberBox<Integer> intBox    = new NumberBox<>(42);
        NumberBox<Double>  doubleBox = new NumberBox<>(3.14);

        System.out.println(intBox    + " double value: " + intBox.doubleValue());
        System.out.println(doubleBox + " int value: "    + doubleBox.intValue());
        System.out.println("Is zero: " + new NumberBox<>(0).isZero());  // true

        // The compiler prevents invalid types:
        // NumberBox<String> bad = new NumberBox<>("text");  // compile error
    }
}
