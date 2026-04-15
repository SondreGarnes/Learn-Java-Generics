/**
 * CHAPTER 3 — GENERIC METHODS
 * File 2 of 3: Static Generic Methods & Type Inference
 *
 * ═══════════════════════════════════════════════════════════════
 * STATIC GENERIC METHODS
 * ═══════════════════════════════════════════════════════════════
 *
 * Static methods CAN be generic even if the enclosing class is not.
 * They are especially common in utility / helper classes.
 *
 * Important: a static method CANNOT use the class-level type parameter.
 *
 *   class Foo<T> {
 *       T instanceField;                 // OK — uses class T
 *
 *       static T brokenMethod() { ... } // COMPILE ERROR — static context
 *                                       // cannot see class T
 *
 *       static <T> T okMethod(T x) { return x; }  // OK — its own <T>
 *   }
 *
 * ───────────────────────────────────────────────────────────────
 * RETURNING GENERIC TYPES
 * ───────────────────────────────────────────────────────────────
 * A static factory method is a common pattern:
 *
 *   public static <T> List<T> listOf(T... elements) { ... }
 *
 * The caller writes: List<String> l = listOf("a", "b");
 * The compiler infers T = String from the arguments.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaticGenericMethods {

    /**
     * repeat — returns a List containing the given value n times.
     */
    public static <T> List<T> repeat(T value, int times) {
        List<T> result = new ArrayList<>(times);
        for (int i = 0; i < times; i++) {
            result.add(value);
        }
        return result;
    }

    /**
     * reversed — returns a NEW list that is the reverse of the input.
     * Does not modify the original list.
     */
    public static <T> List<T> reversed(List<T> list) {
        List<T> copy = new ArrayList<>(list);
        Collections.reverse(copy);
        return copy;
    }

    /**
     * zip — combines two lists element-by-element into a list of String pairs.
     * Stops at the shorter list.
     *
     * zip([1,2,3], ["a","b","c"]) → ["(1,a)", "(2,b)", "(3,c)"]
     */
    public static <A, B> List<String> zip(List<A> listA, List<B> listB) {
        List<String> result = new ArrayList<>();
        int size = Math.min(listA.size(), listB.size());
        for (int i = 0; i < size; i++) {
            result.add("(" + listA.get(i) + "," + listB.get(i) + ")");
        }
        return result;
    }

    /**
     * coalesce — returns the first non-null value in a list, or a default.
     */
    public static <T> T coalesce(List<T> values, T defaultValue) {
        for (T v : values) {
            if (v != null) return v;
        }
        return defaultValue;
    }

    /**
     * singleton — creates a single-element immutable list.
     * Mirrors the pattern of java.util.Collections.singletonList.
     */
    public static <T> List<T> singleton(T element) {
        return Collections.singletonList(element);
    }

    public static void main(String[] args) {

        // repeat
        List<String> dots = repeat(".", 5);
        System.out.println("Repeat: " + dots);          // [., ., ., ., .]

        List<Integer> zeros = repeat(0, 4);
        System.out.println("Repeat int: " + zeros);     // [0, 0, 0, 0]

        // reversed
        List<String> words = new ArrayList<>(List.of("a", "b", "c", "d"));
        List<String> rev   = reversed(words);
        System.out.println("Original: " + words);       // [a, b, c, d]
        System.out.println("Reversed: " + rev);         // [d, c, b, a]

        // zip
        List<Integer> nums  = List.of(1, 2, 3);
        List<String>  chars = List.of("x", "y", "z");
        List<String>  zipped = zip(nums, chars);
        System.out.println("Zipped: " + zipped);        // [(1,x), (2,y), (3,z)]

        // coalesce
        List<String> candidates = new ArrayList<>();
        candidates.add(null);
        candidates.add(null);
        candidates.add("found");
        candidates.add("ignored");
        System.out.println("Coalesce: " + coalesce(candidates, "default")); // found

        // singleton
        List<Double> single = singleton(Math.PI);
        System.out.println("Singleton: " + single);     // [3.141592653589793]
    }
}
