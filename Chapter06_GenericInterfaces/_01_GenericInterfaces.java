/**
 * CHAPTER 6 — GENERIC INTERFACES
 * File 1 of 2: Defining and Implementing Generic Interfaces
 *
 * ═══════════════════════════════════════════════════════════════
 * GENERIC INTERFACES
 * ═══════════════════════════════════════════════════════════════
 *
 * An interface can declare type parameters just like a class:
 *
 *   interface Transformer<T, R> {
 *       R transform(T input);
 *   }
 *
 * When a class implements a generic interface it has three choices:
 *
 *   1. PROVIDE concrete types:
 *      class StringToInt implements Transformer<String, Integer> { ... }
 *
 *   2. PASS THROUGH the type parameter:
 *      class IdentityTransformer<T> implements Transformer<T, T> { ... }
 *
 *   3. REMAIN RAW (avoid! — loses type safety):
 *      class Bad implements Transformer { ... }
 *
 * ───────────────────────────────────────────────────────────────
 * WELL-KNOWN GENERIC INTERFACES IN JAVA
 * ───────────────────────────────────────────────────────────────
 *   Comparable<T>         — natural ordering
 *   Comparator<T>         — external ordering
 *   Iterable<T>           — supports for-each
 *   Function<T, R>        — maps T → R
 *   Predicate<T>          — tests T → boolean
 *   Supplier<T>           — produces T
 *   Consumer<T>           — consumes T
 *   List<E>, Map<K,V>     — collection interfaces
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class _01_GenericInterfaces {

    // ─── 1. Simple generic interface ─────────────────────────────
    interface Transformer<T, R> {
        R transform(T input);
    }

    // Implement with concrete types
    static class StringLength implements Transformer<String, Integer> {
        @Override
        public Integer transform(String input) {
            return input.length();
        }
    }

    // Implement with pass-through type parameter
    static class Identity<T> implements Transformer<T, T> {
        @Override
        public T transform(T input) {
            return input;
        }
    }

    // ─── 2. Generic interface with bound ─────────────────────────
    interface Summable<T extends Number> {
        T sum(List<T> elements);
        default boolean isEmpty(List<T> elements) { return elements.isEmpty(); }
    }

    static class IntSummer implements Summable<Integer> {
        @Override
        public Integer sum(List<Integer> elements) {
            int total = 0;
            for (int n : elements) total += n;
            return total;
        }
    }

    // ─── 3. Generic interface extending another generic interface ─
    interface ReadableStore<K, V> {
        V get(K key);
        boolean contains(K key);
    }

    interface WritableStore<K, V> {
        void put(K key, V value);
        void remove(K key);
    }

    // Combine both — a read-write store
    interface Store<K, V> extends ReadableStore<K, V>, WritableStore<K, V> {
        int size();
    }

    static class SimpleStore<K, V> implements Store<K, V> {
        private final java.util.HashMap<K, V> map = new java.util.HashMap<>();

        @Override public V get(K key)           { return map.get(key);            }
        @Override public boolean contains(K key) { return map.containsKey(key);   }
        @Override public void put(K key, V value){ map.put(key, value);           }
        @Override public void remove(K key)      { map.remove(key);               }
        @Override public int size()              { return map.size();              }

        @Override public String toString()       { return map.toString();          }
    }

    // ─── 4. Implementing Iterable<T> ─────────────────────────────
    static class Range implements Iterable<Integer> {
        private final int start, end;

        public Range(int start, int end) {
            this.start = start;
            this.end   = end;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                int current = start;
                @Override public boolean hasNext() { return current < end; }
                @Override public Integer next()    { return current++;     }
            };
        }
    }

    public static void main(String[] args) {

        // Transformer
        Transformer<String, Integer> len = new StringLength();
        System.out.println("Length of 'hello': " + len.transform("hello")); // 5

        Identity<String> id = new Identity<>();
        System.out.println("Identity: " + id.transform("unchanged"));       // unchanged

        // Lambda as Transformer (functional interface)
        Transformer<Integer, String> intToStr = n -> "num=" + n;
        System.out.println(intToStr.transform(42));                         // num=42

        // Summable
        IntSummer summer = new IntSummer();
        System.out.println("Sum: " + summer.sum(List.of(1, 2, 3, 4, 5))); // 15
        System.out.println("Empty: " + summer.isEmpty(new ArrayList<>())); // true

        // Store
        Store<String, Integer> store = new SimpleStore<>();
        store.put("one", 1);
        store.put("two", 2);
        store.put("three", 3);
        System.out.println("Store: " + store);
        System.out.println("Get 'two': " + store.get("two"));   // 2
        store.remove("two");
        System.out.println("Size after remove: " + store.size()); // 2

        // Range (Iterable)
        System.out.print("Range 1..5: ");
        for (int n : new Range(1, 6)) {
            System.out.print(n + " ");    // 1 2 3 4 5
        }
        System.out.println();
    }
}
