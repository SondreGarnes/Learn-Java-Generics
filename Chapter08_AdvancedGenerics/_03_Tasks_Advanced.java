/**
 * CHAPTER 8 — TASKS: Advanced Generics
 *
 * Solutions: Solutions/Chapter08/03_Tasks_Advanced_Solution.java
 */

import java.util.*;
import java.util.function.*;

public class _03_Tasks_Advanced {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — Generic Binary Search Tree (BST) node
    // ═══════════════════════════════════════════════════════════════
    // Implement a BSTNode<T extends Comparable<T>> that supports:
    //   insert(T value)            — inserts into the BST
    //   contains(T value)          — true if value is in the tree
    //   inOrder(List<T> result)    — fills result with in-order traversal
    //
    // A BST places values less than the node to the left,
    // greater or equal to the right.
    //
    // You only need the node class — no separate Tree wrapper.

    static class BSTNode<T extends Comparable<T>> {
        T value;
        BSTNode<T> left, right;

        public BSTNode(T value) {
            this.value = value;
        }

        // TODO: insert
        public void insert(T val) {
        }

        // TODO: contains
        public boolean contains(T val) {
            return false; // replace
        }

        // TODO: inOrder — adds values in sorted order to the list
        public void inOrder(List<T> result) {
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — Fluent Pipeline builder
    // ═══════════════════════════════════════════════════════════════
    // Implement a Pipeline<T> class that represents a chain of
    // transformations on a value. It should support:
    //
    //   Pipeline.of(value)       — creates a pipeline holding `value`
    //   pipe(Function<T,R>)      — applies function, returns Pipeline<R>
    //   get()                    — retrieves the current value
    //
    // Example:
    //   Pipeline.of("  hello world  ")
    //           .pipe(String::trim)
    //           .pipe(String::toUpperCase)
    //           .pipe(s -> s.replace(" ", "_"))
    //           .get()
    //   → "HELLO_WORLD"

    static class Pipeline<T> {
        // TODO: field

        private Pipeline(T value) {
            // TODO
        }

        public static <T> Pipeline<T> of(T value) {
            return null; // replace
        }

        public <R> Pipeline<R> pipe(Function<T, R> function) {
            return null; // replace
        }

        public T get() {
            return null; // replace
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — Generic EventBus
    // ═══════════════════════════════════════════════════════════════
    // Design a simple EventBus that allows registering typed listeners
    // and publishing typed events.
    //
    //   subscribe(Class<E> eventType, Consumer<E> listener)
    //   publish(E event)
    //
    // When publish(event) is called, all listeners registered for
    // event.getClass() should be invoked.
    //
    // Hint: Store listeners as Map<Class<?>, List<Consumer<?>>>
    // Use @SuppressWarnings("unchecked") where needed for the cast.

    static class EventBus {
        // TODO: field

        public <E> void subscribe(Class<E> eventType, Consumer<E> listener) {
            // TODO
        }

        public <E> void publish(E event) {
            // TODO
        }
    }

    // ─── Sample event classes ──────────────────────────────────────
    static class LoginEvent {
        final String username;
        LoginEvent(String username) { this.username = username; }
        @Override public String toString() { return "LoginEvent[" + username + "]"; }
    }

    static class LogoutEvent {
        final String username;
        LogoutEvent(String username) { this.username = username; }
        @Override public String toString() { return "LogoutEvent[" + username + "]"; }
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // Task 1
        System.out.println("=== Task 1: BST ===");
        BSTNode<Integer> root = new BSTNode<>(5);
        root.insert(3);
        root.insert(7);
        root.insert(1);
        root.insert(4);
        root.insert(6);
        root.insert(9);

        List<Integer> inOrder = new ArrayList<>();
        root.inOrder(inOrder);
        System.out.println("In-order: " + inOrder);         // [1, 3, 4, 5, 6, 7, 9]
        System.out.println("Contains 4: " + root.contains(4)); // true
        System.out.println("Contains 8: " + root.contains(8)); // false

        BSTNode<String> strRoot = new BSTNode<>("mango");
        strRoot.insert("apple");
        strRoot.insert("zebra");
        strRoot.insert("banana");
        List<String> strOrder = new ArrayList<>();
        strRoot.inOrder(strOrder);
        System.out.println("String BST: " + strOrder);  // [apple, banana, mango, zebra]

        // Task 2
        System.out.println("\n=== Task 2: Pipeline ===");
        String result = Pipeline.of("  hello world  ")
                .pipe(String::trim)
                .pipe(String::toUpperCase)
                .pipe(s -> s.replace(" ", "_"))
                .get();
        System.out.println("Pipeline result: " + result);  // HELLO_WORLD

        int numResult = Pipeline.of("42")
                .pipe(Integer::parseInt)
                .pipe(n -> n * 2)
                .pipe(n -> n + 8)
                .get();
        System.out.println("Num pipeline: " + numResult);  // 92

        // Task 3
        System.out.println("\n=== Task 3: EventBus ===");
        EventBus bus = new EventBus();

        bus.subscribe(LoginEvent.class,  e -> System.out.println("LOGIN:  " + e));
        bus.subscribe(LoginEvent.class,  e -> System.out.println("AUDIT:  " + e.username + " logged in"));
        bus.subscribe(LogoutEvent.class, e -> System.out.println("LOGOUT: " + e));

        bus.publish(new LoginEvent("alice"));
        bus.publish(new LoginEvent("bob"));
        bus.publish(new LogoutEvent("alice"));
    }
}
