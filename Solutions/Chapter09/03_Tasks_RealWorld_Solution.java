/**
 * SOLUTIONS — Chapter 9: Real-World Generics
 */
import java.util.*;
import java.util.function.*;

public class Tasks_RealWorld_Solution {

    // Task 1 — LRU Cache
    static class LRUCache<K, V> {
        private final int capacity;
        private final LinkedHashMap<K, V> map;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            // accessOrder=true: get() moves entry to the end (most recent)
            this.map = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > capacity;
                }
            };
        }

        public V get(K key)           { return map.get(key);           }
        public void put(K key, V value){ map.put(key, value);           }
        public int size()             { return map.size();             }
        public boolean containsKey(K key) { return map.containsKey(key); }

        @Override public String toString() { return map.toString(); }
    }

    // Task 2 — Option
    static class Option<T> {
        private static final Option<?> NONE = new Option<>(null);

        private final T value;

        private Option(T value) { this.value = value; }

        public static <T> Option<T> some(T value) { return new Option<>(value); }

        @SuppressWarnings("unchecked")
        public static <T> Option<T> none() { return (Option<T>) NONE; }

        public boolean isPresent() { return this != NONE; }

        public T get() {
            if (!isPresent()) throw new NoSuchElementException("Option is empty");
            return value;
        }

        public <R> Option<R> map(Function<T, R> mapper) {
            return isPresent() ? Option.some(mapper.apply(value)) : Option.none();
        }

        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return isPresent() ? mapper.apply(value) : Option.none();
        }

        public T orElse(T defaultValue) {
            return isPresent() ? value : defaultValue;
        }

        public void ifPresent(Consumer<T> action) {
            if (isPresent()) action.accept(value);
        }

        @Override public String toString() {
            return isPresent() ? "Some(" + value + ")" : "None";
        }
    }

    // Task 3 — Graph
    static class Graph<V> {
        private final Map<V, List<V>> adjacency = new LinkedHashMap<>();

        public void addVertex(V vertex) {
            adjacency.putIfAbsent(vertex, new ArrayList<>());
        }

        public void addEdge(V from, V to) {
            addVertex(from);
            addVertex(to);
            adjacency.get(from).add(to);
        }

        public List<V> getNeighbors(V vertex) {
            return adjacency.getOrDefault(vertex, Collections.emptyList());
        }

        public boolean hasEdge(V from, V to) {
            List<V> neighbors = adjacency.get(from);
            return neighbors != null && neighbors.contains(to);
        }

        public Set<V> vertices() { return adjacency.keySet(); }

        public List<V> bfs(V start) {
            List<V> visited = new ArrayList<>();
            Set<V>  seen    = new LinkedHashSet<>();
            Queue<V> queue  = new LinkedList<>();

            queue.add(start);
            seen.add(start);

            while (!queue.isEmpty()) {
                V current = queue.poll();
                visited.add(current);
                for (V neighbor : getNeighbors(current)) {
                    if (!seen.contains(neighbor)) {
                        seen.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            return visited;
        }

        @Override public String toString() { return adjacency.toString(); }
    }

    public static void main(String[] args) {

        // Task 1
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        cache.put("a", 1); cache.put("b", 2); cache.put("c", 3);
        System.out.println(cache.get("a"));   // 1  — 'a' is now MRU
        cache.put("d", 4);                    // evicts 'b' (LRU was 'b')
        System.out.println(cache.containsKey("b")); // false
        System.out.println(cache.containsKey("a")); // true
        System.out.println(cache.containsKey("d")); // true

        // Task 2
        Option<String> some = Option.some("hello");
        Option<String> none = Option.none();
        System.out.println(some);                               // Some(hello)
        System.out.println(none);                               // None
        System.out.println(some.map(String::length));           // Some(5)
        System.out.println(none.map(String::length));           // None
        System.out.println(none.orElse("default"));            // default
        some.ifPresent(s -> System.out.println("Present: " + s));

        // Task 3
        Graph<String> graph = new Graph<>();
        graph.addEdge("A","B"); graph.addEdge("A","C");
        graph.addEdge("B","D"); graph.addEdge("C","D");
        graph.addEdge("D","E");
        System.out.println("BFS from A: " + graph.bfs("A")); // [A, B, C, D, E]
        System.out.println("A->B: " + graph.hasEdge("A","B")); // true
        System.out.println("A->E: " + graph.hasEdge("A","E")); // false
    }
}
