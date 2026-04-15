/**
 * CHAPTER 5 — WILDCARDS
 * File 2 of 4: Upper-Bounded Wildcard (? extends T)
 *
 * ═══════════════════════════════════════════════════════════════
 * UPPER-BOUNDED WILDCARD:  List<? extends Number>
 * ═══════════════════════════════════════════════════════════════
 *
 * "A list of some type that is Number or a subtype of Number."
 *
 * This accepts: List<Number>, List<Integer>, List<Double>, List<Long>...
 * It does NOT accept: List<String>, List<Object>
 *
 * ───────────────────────────────────────────────────────────────
 * READING vs WRITING
 * ───────────────────────────────────────────────────────────────
 * With List<? extends Number>:
 *
 *   READING:  You get back a Number (the upper bound).  ✓
 *   WRITING:  You CANNOT add — the compiler doesn't know the exact type.
 *
 *   List<? extends Number> list = new ArrayList<Integer>();
 *   Number n = list.get(0);   // OK — guaranteed to be Number or subtype
 *   list.add(42);             // COMPILE ERROR — might not be Integer!
 *
 * ───────────────────────────────────────────────────────────────
 * PECS — Producer Extends, Consumer Super
 * ───────────────────────────────────────────────────────────────
 * This is the golden rule for wildcards:
 *
 *   • If you only READ from a collection  → use "? extends T" (Producer)
 *   • If you only WRITE to a collection   → use "? super T"   (Consumer)
 *   • If you do both                       → use T directly (no wildcard)
 *
 * Upper-bounded (? extends T) = PRODUCER — you produce (read) values from it.
 */

import java.util.List;
import java.util.ArrayList;

public class _02_UpperBoundedWildcard {

    /**
     * sumOfList — works with any list of Number subtypes.
     * We only READ from the list (producer), so ? extends Number fits.
     */
    public static double sumOfList(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {      // get() returns Number — safe to use
            sum += n.doubleValue();
        }
        return sum;
    }

    /**
     * maxElement — returns the largest element.
     * ? extends Comparable<T> is a common pattern for sorted operations.
     */
    public static <T extends Comparable<T>> T maxElement(List<? extends T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("Empty list");
        T max = list.get(0);
        for (T element : list) {
            if (element.compareTo(max) > 0) max = element;
        }
        return max;
    }

    /**
     * copy — copies all elements from src into dest.
     *
     * src  = producer  → ? extends T  (we read from it)
     * dest = consumer  → ? super T    (we write to it)  ← preview of next file
     *
     * This is the classic PECS signature from java.util.Collections.copy().
     */
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (T item : src) {
            dest.add(item);    // add to dest is safe — dest accepts T or supertype
        }
    }

    /**
     * flatten — flattens a List<List<? extends T>> into a single List<T>.
     */
    public static <T> List<T> flatten(List<? extends List<? extends T>> nested) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> inner : nested) {
            result.addAll(inner);
        }
        return result;
    }

    public static void main(String[] args) {

        // sumOfList — accepts Integer, Double, Long lists
        List<Integer> ints    = List.of(1, 2, 3, 4, 5);
        List<Double>  doubles = List.of(1.5, 2.5, 3.0);
        List<Long>    longs   = List.of(100L, 200L, 300L);

        System.out.println("Sum ints:    " + sumOfList(ints));    // 15.0
        System.out.println("Sum doubles: " + sumOfList(doubles)); // 7.0
        System.out.println("Sum longs:   " + sumOfList(longs));   // 600.0

        // maxElement
        System.out.println("Max int:    " + maxElement(ints));              // 5
        System.out.println("Max double: " + maxElement(doubles));           // 3.0
        System.out.println("Max string: " + maxElement(List.of("c","a","b"))); // c

        // copy
        List<Number> dest = new ArrayList<>();
        copy(dest, ints);    // copy Integer list into Number list
        copy(dest, doubles); // also copy Double list into same Number list
        System.out.println("Copied dest: " + dest);

        // flatten
        List<List<Integer>> nested = List.of(
            List.of(1, 2, 3),
            List.of(4, 5),
            List.of(6, 7, 8, 9)
        );
        List<Integer> flat = flatten(nested);
        System.out.println("Flattened: " + flat);   // [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
