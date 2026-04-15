/**
 * SOLUTIONS — Chapter 2: Generic Classes
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class _03_Tasks_GenericClasses_Solution {

    // ── Task 1: Generic Stack ─────────────────────────────────────
    static class Stack<T> {
        private final ArrayList<T> items = new ArrayList<>();

        public void push(T item) {
            items.add(item);
        }

        public T pop() {
            if (isEmpty()) throw new RuntimeException("Stack is empty");
            return items.remove(items.size() - 1);
        }

        public T peek() {
            if (isEmpty()) throw new RuntimeException("Stack is empty");
            return items.get(items.size() - 1);
        }

        public boolean isEmpty() { return items.isEmpty(); }
        public int size()        { return items.size();    }
    }

    // ── Task 2: KeyValueStore ─────────────────────────────────────
    static class KeyValueStore<K, V> {
        private final HashMap<K, V> map = new HashMap<>();

        public void put(K key, V value)   { map.put(key, value);          }
        public V get(K key)               { return map.get(key);           }
        public boolean containsKey(K key) { return map.containsKey(key);   }
        public V remove(K key)            { return map.remove(key);        }
        public int size()                 { return map.size();             }
    }

    // ── Task 3: Wrapper with transform ───────────────────────────
    static class Wrapper<T> {
        private final T value;

        public Wrapper(T value) { this.value = value; }
        public T getValue()     { return value;        }

        public <R> Wrapper<R> transform(Function<T, R> mapper) {
            return new Wrapper<>(mapper.apply(value));
        }

        @Override
        public String toString() { return "Wrapper[" + value + "]"; }
    }

    public static void main(String[] args) {

        // Task 1
        Stack<String> stack = new Stack<>();
        System.out.println(stack.isEmpty());
        stack.push("first"); stack.push("second"); stack.push("third");
        System.out.println(stack.size());   // 3
        System.out.println(stack.peek());   // third
        System.out.println(stack.pop());    // third
        System.out.println(stack.pop());    // second
        try { stack.pop(); stack.pop(); }
        catch (RuntimeException e) { System.out.println("Exception: " + e.getMessage()); }

        // Task 2
        KeyValueStore<String, Integer> store = new KeyValueStore<>();
        store.put("apples", 5); store.put("bananas", 3); store.put("cherries", 12);
        System.out.println(store.get("apples"));
        store.remove("bananas");
        System.out.println(store.size());    // 2
        System.out.println(store.get("bananas")); // null

        // Task 3
        Wrapper<String> w = new Wrapper<>("42");
        Wrapper<Integer> n = w.transform(Integer::parseInt);
        System.out.println(n);   // Wrapper[42]
        Wrapper<String> back = n.transform(x -> "Number is " + x);
        System.out.println(back);
    }
}
