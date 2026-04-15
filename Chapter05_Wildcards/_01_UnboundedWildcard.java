/**
 * CHAPTER 5 — WILDCARDS
 * File 1 of 4: Unbounded Wildcard (?)
 *
 * ═══════════════════════════════════════════════════════════════
 * WHAT IS A WILDCARD?
 * ═══════════════════════════════════════════════════════════════
 *
 * A wildcard (?) represents an UNKNOWN type in a generic type argument.
 * It is used in VARIABLE/PARAMETER types — NOT in class or method
 * type parameter declarations.
 *
 *   List<?>       — a list of some unknown type
 *   List<T>       — a list of a known (but generic) type T
 *
 * ───────────────────────────────────────────────────────────────
 * UNBOUNDED WILDCARD:  List<?>
 * ───────────────────────────────────────────────────────────────
 * "I don't know or care what type this list holds."
 *
 * When you read from a List<?>, you get back Object (the only safe
 * type). When you try to add to it, the compiler REFUSES — because
 * it doesn't know whether the list is List<String>, List<Integer>, etc.
 *
 *   List<?> list = new ArrayList<String>();
 *   Object o = list.get(0);    // OK — Object is always safe
 *   list.add("hello");         // COMPILE ERROR — type unknown
 *   list.add(null);            // OK — null is the only addable value
 *
 * ───────────────────────────────────────────────────────────────
 * WHEN TO USE UNBOUNDED WILDCARD
 * ───────────────────────────────────────────────────────────────
 *   • When your method only uses methods from Object (size, isEmpty, etc.)
 *   • When you want to accept ANY parameterized type
 *   • When you're printing, counting, or inspecting without caring about type
 *
 * ───────────────────────────────────────────────────────────────
 * WHY NOT JUST USE List<Object>?
 * ───────────────────────────────────────────────────────────────
 * List<String> is NOT a subtype of List<Object>.
 * But List<String> IS a subtype of List<?>.
 *
 * So List<?> lets you accept any List regardless of element type,
 * while List<Object> only accepts List<Object> itself.
 */

import java.util.ArrayList;
import java.util.List;

public class _01_UnboundedWildcard {

    /**
     * printList — prints any list regardless of element type.
     * Uses <?> because we never add to the list, only read as Object.
     */
    public static void printList(List<?> list) {
        System.out.print("[");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));   // returns Object
            if (i < list.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    /**
     * countNulls — counts null elements in any list.
     */
    public static int countNulls(List<?> list) {
        int count = 0;
        for (Object element : list) {
            if (element == null) count++;
        }
        return count;
    }

    /**
     * areEqual — checks if two lists have the same size and same
     * string representation of each element (simplified equality).
     */
    public static boolean sameSize(List<?> a, List<?> b) {
        return a.size() == b.size();
    }

    /**
     * Demonstrates why List<String> is NOT a subtype of List<Object>
     * but IS a subtype of List<?>.
     */
    static void demonstrateSubtyping() {
        List<String> strings = new ArrayList<>(List.of("a", "b", "c"));

        // This would be a compile error:
        // List<Object> objects = strings;  // NOT allowed

        // But this works:
        List<?> wildcard = strings;          // allowed!
        System.out.println("Via wildcard: " + wildcard.get(0));

        // Cannot add to wildcard (type safety):
        // wildcard.add("d");   // COMPILE ERROR
        wildcard.add(null);      // null is the only exception
    }

    public static void main(String[] args) {

        List<String>  strings  = List.of("hello", "world");
        List<Integer> integers = List.of(1, 2, 3);
        List<Double>  doubles  = List.of(3.14, 2.71);

        // printList accepts all of them
        printList(strings);   // [hello, world]
        printList(integers);  // [1, 2, 3]
        printList(doubles);   // [3.14, 2.71]

        // countNulls
        List<String> withNulls = new ArrayList<>();
        withNulls.add("a");
        withNulls.add(null);
        withNulls.add("b");
        withNulls.add(null);
        System.out.println("Nulls: " + countNulls(withNulls));  // 2

        // sameSize
        System.out.println("Same size: " + sameSize(strings, integers));  // false
        System.out.println("Same size: " + sameSize(strings, doubles));   // true

        demonstrateSubtyping();
    }
}
