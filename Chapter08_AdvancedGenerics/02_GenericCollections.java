/**
 * CHAPTER 8 — ADVANCED GENERICS
 * File 2 of 3: Generics with Java Collections
 *
 * ═══════════════════════════════════════════════════════════════
 * GENERICS IN THE COLLECTIONS FRAMEWORK
 * ═══════════════════════════════════════════════════════════════
 *
 * The Java Collections Framework (JCF) is built entirely on generics.
 * Understanding how it uses them deepens your ability to use and
 * extend it correctly.
 *
 * Key interfaces:
 *   Collection<E>   — root interface
 *   List<E>         — ordered, allows duplicates
 *   Set<E>          — no duplicates
 *   Map<K,V>        — key-value pairs
 *   Queue<E>        — FIFO
 *   Deque<E>        — double-ended queue
 *
 * Key utility methods in java.util.Collections:
 *   sort(List<T>, Comparator<? super T>)     — PECS in action
 *   copy(List<? super T>, List<? extends T>) — PECS in action
 *   max(Collection<? extends T>, Comparator<? super T>)
 *
 * ───────────────────────────────────────────────────────────────
 * WRITING YOUR OWN COLLECTION-LIKE TYPES
 * ───────────────────────────────────────────────────────────────
 * When you build custom data structures, use the same conventions:
 *   • Type parameter E (or T) for element type
 *   • Implement Iterable<E> to support for-each
 *   • Use PECS on methods that accept/return collections
 */

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericCollections {

    /**
     * A simple functional Map operation — applies a Function to every
     * element in a list and returns a new list of results.
     * This mirrors streams: list.stream().map(f).collect(...)
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>(list.size());
        for (T item : list) result.add(f.apply(item));
        return result;
    }

    /**
     * filter — keeps only elements matching the predicate.
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) result.add(item);
        }
        return result;
    }

    /**
     * groupBy — partitions a list into a Map using a key extractor.
     * Elements with the same key go into the same bucket.
     */
    public static <T, K> Map<K, List<T>> groupBy(List<T> list, Function<T, K> keyExtractor) {
        Map<K, List<T>> result = new LinkedHashMap<>();
        for (T item : list) {
            K key = keyExtractor.apply(item);
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
        return result;
    }

    /**
     * reduce — folds a list into a single value using a combiner.
     * reduce([1,2,3,4], 0, (acc,x) -> acc + x)  →  10
     */
    public static <T, R> R reduce(List<T> list, R identity, java.util.function.BiFunction<R, T, R> combiner) {
        R result = identity;
        for (T item : list) {
            result = combiner.apply(result, item);
        }
        return result;
    }

    /**
     * frequency — counts how many times each element appears.
     */
    public static <T> Map<T, Integer> frequency(List<T> list) {
        Map<T, Integer> freq = new LinkedHashMap<>();
        for (T item : list) {
            freq.merge(item, 1, Integer::sum);
        }
        return freq;
    }

    public static void main(String[] args) {

        List<String> words = List.of("apple", "banana", "cherry", "avocado", "blueberry");

        // map
        List<Integer> lengths = map(words, String::length);
        System.out.println("Lengths: " + lengths);   // [5, 6, 6, 7, 9]

        List<String> upper = map(words, String::toUpperCase);
        System.out.println("Upper: " + upper);

        // filter
        List<String> longWords = filter(words, w -> w.length() > 6);
        System.out.println("Long words: " + longWords);  // [avocado, blueberry]

        // groupBy first letter
        Map<Character, List<String>> byLetter = groupBy(words, w -> w.charAt(0));
        System.out.println("By letter: " + byLetter);
        // {a=[apple, avocado], b=[banana, blueberry], c=[cherry]}

        // reduce — sum of lengths
        int totalLength = reduce(words, 0, (acc, w) -> acc + w.length());
        System.out.println("Total length: " + totalLength);  // 33

        // reduce — concatenate
        String joined = reduce(words, "", (acc, w) -> acc.isEmpty() ? w : acc + ", " + w);
        System.out.println("Joined: " + joined);

        // frequency
        List<String> repeated = List.of("a","b","a","c","b","a");
        System.out.println("Frequency: " + frequency(repeated));  // {a=3, b=2, c=1}
    }
}
