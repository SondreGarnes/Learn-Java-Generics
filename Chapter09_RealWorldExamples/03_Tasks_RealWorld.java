/**
 * CHAPTER 9 — TASKS: Real-World Generics
 *
 * These are the most challenging tasks in the course. They combine
 * everything you've learned: generic classes, methods, bounded types,
 * wildcards, interfaces, and real design patterns.
 *
 * Solutions: Solutions/Chapter09/03_Tasks_RealWorld_Solution.java
 */

import java.util.*;
import java.util.function.*;

public class Tasks_RealWorld {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — Generic LRU Cache
    // ═══════════════════════════════════════════════════════════════
    // Implement an LRUCache<K, V> (Least Recently Used):
    //
    //   LRUCache(int capacity)       — maximum number of entries
    //   V get(K key)                 — returns value or null; marks as recently used
    //   void put(K key, V value)     — inserts/updates; evicts LRU entry if full
    //   int size()
    //   boolean containsKey(K key)
    //
    // Hint: LinkedHashMap with accessOrder=true does exactly this.
    //       Override removeEldestEntry() to auto-evict when capacity is exceeded.

    static class LRUCache<K, V> {
        // TODO: field (hint: extend/compose LinkedHashMap)

        public LRUCache(int capacity) {
            // TODO
        }

        public V get(K key) {
            return null; // replace
        }

        public void put(K key, V value) {
            // TODO
        }

        public int size() { return 0; } // replace

        public boolean containsKey(K key) { return false; } // replace

        @Override
        public String toString() { return ""; } // replace
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — Generic Option (Maybe monad)
    // ═══════════════════════════════════════════════════════════════
    // Implement Option<T> — a container that either holds a value
    // (Some) or is empty (None). This is similar to java.util.Optional
    // but built from scratch to practice generics.
    //
    //   Option.some(T value) — creates a present Option
    //   Option.none()        — creates an absent Option
    //   isPresent()          — true if value exists
    //   get()                — returns value; throws if absent
    //   <R> Option<R> map(Function<T,R>)  — transforms value if present
    //   <R> Option<R> flatMap(Function<T, Option<R>>)  — chained Option ops
    //   T orElse(T defaultValue)          — value or default
    //   void ifPresent(Consumer<T> action)— runs action if present

    static class Option<T> {
        // TODO: fields + private constructor

        public static <T> Option<T> some(T value) {
            return null; // replace
        }

        @SuppressWarnings("unchecked")
        public static <T> Option<T> none() {
            return null; // replace
        }

        public boolean isPresent() { return false; } // replace

        public T get() { return null; } // replace — throw if absent

        public <R> Option<R> map(Function<T, R> mapper) { return null; } // replace

        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) { return null; } // replace

        public T orElse(T defaultValue) { return null; } // replace

        public void ifPresent(Consumer<T> action) { } // replace

        @Override
        public String toString() { return isPresent() ? "Some(" + get() + ")" : "None"; }
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — Generic Graph (adjacency list)
    // ═══════════════════════════════════════════════════════════════
    // Implement a simple directed Graph<V> where V is the vertex type.
    //
    //   addVertex(V vertex)
    //   addEdge(V from, V to)
    //   List<V> getNeighbors(V vertex)   — returns vertices reachable from vertex
    //   boolean hasEdge(V from, V to)
    //   Set<V> vertices()                — returns all vertices
    //   List<V> bfs(V start)             — breadth-first traversal from start

    static class Graph<V> {
        // TODO: adjacency map field

        public void addVertex(V vertex) {
            // TODO
        }

        public void addEdge(V from, V to) {
            // TODO — ensure both vertices exist, then add edge
        }

        public List<V> getNeighbors(V vertex) {
            return Collections.emptyList(); // replace
        }

        public boolean hasEdge(V from, V to) {
            return false; // replace
        }

        public Set<V> vertices() {
            return Collections.emptySet(); // replace
        }

        public List<V> bfs(V start) {
            // TODO — standard BFS using a Queue
            return new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Graph" + ""; // replace with adjacency list string
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // Task 1 — LRU Cache
        System.out.println("=== Task 1: LRU Cache ===");
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        System.out.println("Size: " + cache.size());          // 3
        System.out.println("get(a): " + cache.get("a"));      // 1 — 'a' is now most recent

        cache.put("d", 4);   // evicts 'b' (LRU after 'a' was accessed)
        System.out.println("contains b: " + cache.containsKey("b"));  // false
        System.out.println("contains a: " + cache.containsKey("a"));  // true
        System.out.println("contains c: " + cache.containsKey("c"));  // false (was LRU before 'a' was re-accessed)
        System.out.println("contains d: " + cache.containsKey("d"));  // true

        // Task 2 — Option
        System.out.println("\n=== Task 2: Option ===");
        Option<String> some  = Option.some("hello");
        Option<String> none  = Option.none();

        System.out.println(some);                           // Some(hello)
        System.out.println(none);                           // None
        System.out.println(some.isPresent());               // true
        System.out.println(none.isPresent());               // false

        Option<Integer> length = some.map(String::length);
        System.out.println("Mapped: " + length);            // Some(5)

        Option<Integer> noneLength = none.map(String::length);
        System.out.println("None mapped: " + noneLength);   // None

        System.out.println("orElse: " + none.orElse("default"));  // default

        some.ifPresent(s -> System.out.println("Present: " + s)); // Present: hello

        Option<String> chained = some
                .map(String::toUpperCase)
                .flatMap(s -> s.length() > 3 ? Option.some(s) : Option.none());
        System.out.println("Chained: " + chained);          // Some(HELLO)

        // Task 3 — Graph BFS
        System.out.println("\n=== Task 3: Graph ===");
        Graph<String> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");

        System.out.println("Vertices: " + graph.vertices());          // [A, B, C, D, E]
        System.out.println("Neighbors of A: " + graph.getNeighbors("A")); // [B, C]
        System.out.println("Has A->B: " + graph.hasEdge("A", "B"));   // true
        System.out.println("Has A->E: " + graph.hasEdge("A", "E"));   // false
        System.out.println("BFS from A: " + graph.bfs("A"));          // [A, B, C, D, E]
    }
}
