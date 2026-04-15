/**
 * CHAPTER 5 — WILDCARDS
 * File 3 of 4: Lower-Bounded Wildcard (? super T)
 *
 * ═══════════════════════════════════════════════════════════════
 * LOWER-BOUNDED WILDCARD:  List<? super Integer>
 * ═══════════════════════════════════════════════════════════════
 *
 * "A list of some type that is Integer, or a SUPERTYPE of Integer."
 *
 * This accepts: List<Integer>, List<Number>, List<Object>
 * It does NOT accept: List<Double> (Double is not a supertype of Integer)
 *
 * ───────────────────────────────────────────────────────────────
 * READING vs WRITING
 * ───────────────────────────────────────────────────────────────
 * With List<? super Integer>:
 *
 *   WRITING: You CAN add Integer (and subtypes).         ✓
 *   READING: You only get back Object (not Integer).
 *
 *   List<? super Integer> list = new ArrayList<Number>();
 *   list.add(42);           // OK — Integer fits into Number
 *   Object o = list.get(0); // only Object is safe to read as
 *
 * ───────────────────────────────────────────────────────────────
 * PECS RECAP
 * ───────────────────────────────────────────────────────────────
 *   ? extends T  →  Producer (read-only, you get T)
 *   ? super T    →  Consumer (write-only, you add T)
 *
 * Real-world example from Java standard library:
 *   void sort(List<T> list, Comparator<? super T> c)
 *
 * A Comparator<Number> can compare Integers (because Integer extends Number).
 * So if you have a List<Integer>, you can pass a Comparator<Number>.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LowerBoundedWildcard {

    /**
     * addNumbers — adds integers 1..n to any list that can hold Integer
     * or a supertype (Number, Object).
     * Uses ? super Integer because we WRITE (consume) integers into the list.
     */
    public static void addNumbers(List<? super Integer> list, int n) {
        for (int i = 1; i <= n; i++) {
            list.add(i);   // safe — list accepts Integer or supertype
        }
    }

    /**
     * forEach — applies an action to each element.
     * The Consumer<? super T> lets you pass a Consumer<Object> for a List<String>.
     * This mirrors java.lang.Iterable.forEach.
     */
    public static <T> void forEach(List<T> list, Consumer<? super T> action) {
        for (T element : list) {
            action.accept(element);
        }
    }

    /**
     * fill — fills a list with a constant value.
     * dest is a consumer of T (we write T values into it).
     */
    public static <T> void fill(List<? super T> dest, T value, int times) {
        for (int i = 0; i < times; i++) {
            dest.add(value);
        }
    }

    /**
     * Demonstrate the PECS principle fully with a copy example.
     * src  produces T  →  ? extends T
     * dest consumes T  →  ? super T
     */
    public static <T> void safeCopy(List<? extends T> src, List<? super T> dest) {
        for (T item : src) {
            dest.add(item);
        }
    }

    public static void main(String[] args) {

        // addNumbers into List<Integer>
        List<Integer> intList = new ArrayList<>();
        addNumbers(intList, 5);
        System.out.println("Integer list: " + intList);  // [1, 2, 3, 4, 5]

        // addNumbers into List<Number> (Number is a supertype of Integer)
        List<Number> numList = new ArrayList<>();
        addNumbers(numList, 3);
        System.out.println("Number list:  " + numList);  // [1, 2, 3]

        // addNumbers into List<Object>
        List<Object> objList = new ArrayList<>();
        addNumbers(objList, 2);
        System.out.println("Object list:  " + objList);  // [1, 2]

        // forEach with Consumer<Object> on a List<String>
        System.out.println("\nforEach with Consumer<Object>:");
        List<String> words = List.of("apple", "banana", "cherry");
        Consumer<Object> printer = o -> System.out.println("  -> " + o);
        forEach(words, printer);

        // fill
        System.out.println("\nfill:");
        List<Number> filled = new ArrayList<>();
        fill(filled, 3.14, 4);   // fill Number list with Integer values
        System.out.println("Filled: " + filled);   // [3.14, 3.14, 3.14, 3.14]

        // safeCopy — PECS in action
        System.out.println("\nsafeCopy:");
        List<Integer> source = List.of(10, 20, 30);
        List<Number>  target = new ArrayList<>();
        safeCopy(source, target);
        System.out.println("Copied: " + target);   // [10, 20, 30]
    }
}
