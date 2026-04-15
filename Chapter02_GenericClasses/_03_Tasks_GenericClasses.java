/**
 * CHAPTER 2 — TASKS: Generic Classes
 *
 * Complete each task below. Each task has a clear description and hints.
 * Compile and test your code by running main() at the bottom.
 *
 * Solutions are in: Solutions/Chapter02/03_Tasks_GenericClasses_Solution.java
 */
public class _03_Tasks_GenericClasses {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — Generic Stack
    // ═══════════════════════════════════════════════════════════════
    // Implement a generic Stack<T> class backed by an array or a list.
    // It must support:
    //   push(T item)    — adds item to the top
    //   pop()           — removes and returns the top item; throw if empty
    //   peek()          — returns the top item without removing it
    //   isEmpty()       — returns true if the stack has no elements
    //   size()          — returns the number of elements
    //
    // Hint: java.util.ArrayList<T> makes the implementation easy.
    //       For pop() on an empty stack, throw new RuntimeException("Stack is empty").

    static class Stack<T> {
        // TODO: add field(s)

        // TODO: push
        public void push(T item) {
        }

        // TODO: pop
        public T pop() {
            return null; // replace with real implementation
        }

        // TODO: peek
        public T peek() {
            return null; // replace with real implementation
        }

        // TODO: isEmpty
        public boolean isEmpty() {
            return false; // replace with real implementation
        }

        // TODO: size
        public int size() {
            return 0; // replace with real implementation
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — Generic KeyValueStore
    // ═══════════════════════════════════════════════════════════════
    // Implement a simple key-value store: KeyValueStore<K, V>.
    // Internally use a java.util.HashMap<K, V>.
    // It must support:
    //   put(K key, V value)   — stores the mapping
    //   get(K key)            — returns the value or null if not found
    //   containsKey(K key)    — returns true if the key exists
    //   remove(K key)         — removes the key and returns the old value
    //   size()                — number of stored entries

    static class KeyValueStore<K, V> {
        // TODO: add field(s)

        // TODO: put
        public void put(K key, V value) {
        }

        // TODO: get
        public V get(K key) {
            return null; // replace
        }

        // TODO: containsKey
        public boolean containsKey(K key) {
            return false; // replace
        }

        // TODO: remove
        public V remove(K key) {
            return null; // replace
        }

        // TODO: size
        public int size() {
            return 0; // replace
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — Generic Wrapper with transform
    // ═══════════════════════════════════════════════════════════════
    // Create a class Wrapper<T> that holds a value of type T and
    // provides a method:
    //
    //   <R> Wrapper<R> transform(java.util.function.Function<T, R> mapper)
    //
    // transform() applies the mapper function to the held value and
    // returns a new Wrapper holding the result.
    //
    // Example:
    //   Wrapper<String> w = new Wrapper<>("42");
    //   Wrapper<Integer> n = w.transform(Integer::parseInt);
    //   n.getValue()  →  42

    static class Wrapper<T> {
        // TODO: field

        public Wrapper(T value) {
            // TODO
        }

        public T getValue() {
            return null; // replace
        }

        // TODO: implement transform
        public <R> Wrapper<R> transform(java.util.function.Function<T, R> mapper) {
            return null; // replace
        }

        @Override
        public String toString() {
            return "Wrapper[" + getValue() + "]";
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST — run this to verify your implementations
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // ── Task 1: Stack ────────────────────────────────────────
        System.out.println("=== Task 1: Stack ===");
        Stack<String> stack = new Stack<>();
        System.out.println("Empty: " + stack.isEmpty());   // true

        stack.push("first");
        stack.push("second");
        stack.push("third");

        System.out.println("Size: " + stack.size());       // 3
        System.out.println("Peek: " + stack.peek());       // third
        System.out.println("Pop:  " + stack.pop());        // third
        System.out.println("Pop:  " + stack.pop());        // second
        System.out.println("Size: " + stack.size());       // 1
        System.out.println("Empty: " + stack.isEmpty());   // false

        try {
            stack.pop();  // "first"
            stack.pop();  // should throw
        } catch (RuntimeException e) {
            System.out.println("Exception on empty pop: " + e.getMessage());
        }

        // ── Task 2: KeyValueStore ────────────────────────────────
        System.out.println("\n=== Task 2: KeyValueStore ===");
        KeyValueStore<String, Integer> store = new KeyValueStore<>();
        store.put("apples", 5);
        store.put("bananas", 3);
        store.put("cherries", 12);

        System.out.println("apples: "  + store.get("apples"));       // 5
        System.out.println("bananas: " + store.get("bananas"));      // 3
        System.out.println("Has grapes: " + store.containsKey("grapes")); // false
        System.out.println("Size: " + store.size());                  // 3

        store.remove("bananas");
        System.out.println("Size after remove: " + store.size());    // 2
        System.out.println("bananas now: " + store.get("bananas"));  // null

        // ── Task 3: Wrapper ──────────────────────────────────────
        System.out.println("\n=== Task 3: Wrapper ===");
        Wrapper<String> strWrapper = new Wrapper<>("42");
        System.out.println("Original: " + strWrapper);    // Wrapper[42]

        Wrapper<Integer> intWrapper = strWrapper.transform(Integer::parseInt);
        System.out.println("Transformed: " + intWrapper); // Wrapper[42]

        Wrapper<String> backToStr = intWrapper.transform(n -> "Number is " + n);
        System.out.println("Back: " + backToStr);         // Wrapper[Number is 42]
    }
}
