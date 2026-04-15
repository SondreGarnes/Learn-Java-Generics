/**
 * CHAPTER 3 — GENERIC METHODS
 * File 1 of 3: Basic Generic Methods
 *
 * ═══════════════════════════════════════════════════════════════
 * WHAT IS A GENERIC METHOD?
 * ═══════════════════════════════════════════════════════════════
 *
 * A generic method declares its own type parameter(s) independently
 * of the class it lives in. The type parameter is declared BEFORE
 * the return type:
 *
 *   public <T> T identity(T value) { return value; }
 *        ^^^                ^^^
 *    type param        used in signature
 *
 * This is different from a generic CLASS where T is declared on the class.
 * A generic method's T is scoped to that method alone.
 *
 * ───────────────────────────────────────────────────────────────
 * TYPE INFERENCE
 * ───────────────────────────────────────────────────────────────
 * The compiler infers T from the arguments you pass — you rarely
 * need to specify it explicitly:
 *
 *   swap(arr);           // T inferred from arr's component type
 *   <String>swap(arr);   // explicit — usually unnecessary
 *
 * ───────────────────────────────────────────────────────────────
 * GENERIC METHODS vs GENERIC CLASSES
 * ───────────────────────────────────────────────────────────────
 * Use a generic CLASS when:
 *   • Multiple methods share the same type parameter
 *   • State (fields) involve the type
 *
 * Use a generic METHOD when:
 *   • Only one or a few methods need type parameters
 *   • The operation is stateless / utility-style
 */

import java.util.Arrays;

public class _01_BasicGenericMethods {

    /**
     * identity — returns its argument unchanged.
     * Simplest possible generic method; shows the <T> syntax.
     */
    public static <T> T identity(T value) {
        return value;
    }

    /**
     * swap — exchanges elements at index i and j in any array.
     * Note: T[] means "array of T".
     */
    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * printArray — prints any array regardless of element type.
     */
    public static <T> void printArray(T[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    /**
     * firstNonNull — returns the first non-null element in a varargs list,
     * or null if all are null. Demonstrates varargs with generics.
     *
     * @SafeVarargs suppresses the "heap pollution" warning for generic varargs.
     */
    @SafeVarargs
    public static <T> T firstNonNull(T... values) {
        for (T v : values) {
            if (v != null) return v;
        }
        return null;
    }

    /**
     * contains — checks whether an array contains a specific element.
     * Uses equals() for comparison.
     */
    public static <T> boolean contains(T[] arr, T target) {
        for (T element : arr) {
            if (element.equals(target)) return true;
        }
        return false;
    }

    public static void main(String[] args) {

        // identity — T inferred as String, then Integer
        System.out.println(identity("hello"));   // hello
        System.out.println(identity(99));         // 99

        // swap
        String[] words = {"apple", "banana", "cherry"};
        System.out.print("Before swap: "); printArray(words);
        swap(words, 0, 2);
        System.out.print("After swap:  "); printArray(words);

        Integer[] numbers = {1, 2, 3, 4, 5};
        swap(numbers, 1, 3);
        System.out.print("Swapped ints: "); printArray(numbers);

        // firstNonNull
        String result = firstNonNull(null, null, "found!", "ignored");
        System.out.println("First non-null: " + result);   // found!

        // contains
        System.out.println(contains(words, "cherry"));   // true
        System.out.println(contains(words, "mango"));    // false
    }
}
