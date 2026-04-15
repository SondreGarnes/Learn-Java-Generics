/**
 * SOLUTIONS — Chapter 8: Advanced Generics
 */
import java.util.*;
import java.util.function.*;

public class _03_Tasks_Advanced_Solution {

    // Task 1 — BST
    static class BSTNode<T extends Comparable<T>> {
        T value;
        BSTNode<T> left, right;

        public BSTNode(T value) { this.value = value; }

        public void insert(T val) {
            if (val.compareTo(value) < 0) {
                if (left  == null) left  = new BSTNode<>(val); else left.insert(val);
            } else {
                if (right == null) right = new BSTNode<>(val); else right.insert(val);
            }
        }

        public boolean contains(T val) {
            int cmp = val.compareTo(value);
            if (cmp == 0) return true;
            if (cmp  < 0) return left  != null && left.contains(val);
            return right != null && right.contains(val);
        }

        public void inOrder(List<T> result) {
            if (left  != null) left.inOrder(result);
            result.add(value);
            if (right != null) right.inOrder(result);
        }
    }

    // Task 2 — Pipeline
    static class Pipeline<T> {
        private final T value;

        private Pipeline(T value) { this.value = value; }

        public static <T> Pipeline<T> of(T value) { return new Pipeline<>(value); }

        public <R> Pipeline<R> pipe(Function<T, R> function) {
            return new Pipeline<>(function.apply(value));
        }

        public T get() { return value; }
    }

    // Task 3 — EventBus
    static class EventBus {
        private final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();

        public <E> void subscribe(Class<E> eventType, Consumer<E> listener) {
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
        }

        @SuppressWarnings("unchecked")
        public <E> void publish(E event) {
            List<Consumer<?>> handlers = listeners.get(event.getClass());
            if (handlers != null) {
                for (Consumer<?> handler : handlers) {
                    ((Consumer<E>) handler).accept(event);
                }
            }
        }
    }

    static class LoginEvent  { final String username; LoginEvent(String u)  { username = u; } @Override public String toString(){ return "LoginEvent["+username+"]"; } }
    static class LogoutEvent { final String username; LogoutEvent(String u) { username = u; } @Override public String toString(){ return "LogoutEvent["+username+"]"; } }

    public static void main(String[] args) {

        // Task 1
        BSTNode<Integer> root = new BSTNode<>(5);
        root.insert(3); root.insert(7); root.insert(1); root.insert(4);
        List<Integer> inOrder = new ArrayList<>();
        root.inOrder(inOrder);
        System.out.println("In-order: " + inOrder);          // [1, 3, 4, 5, 7]
        System.out.println(root.contains(4));                 // true
        System.out.println(root.contains(8));                 // false

        // Task 2
        String result = Pipeline.of("  hello world  ")
                .pipe(String::trim)
                .pipe(String::toUpperCase)
                .pipe(s -> s.replace(" ", "_"))
                .get();
        System.out.println("Pipeline: " + result);           // HELLO_WORLD

        // Task 3
        EventBus bus = new EventBus();
        bus.subscribe(LoginEvent.class,  e -> System.out.println("LOGIN:  " + e));
        bus.subscribe(LoginEvent.class,  e -> System.out.println("AUDIT:  " + e.username + " logged in"));
        bus.subscribe(LogoutEvent.class, e -> System.out.println("LOGOUT: " + e));
        bus.publish(new LoginEvent("alice"));
        bus.publish(new LogoutEvent("alice"));
    }
}
