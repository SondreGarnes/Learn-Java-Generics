/**
 * CHAPTER 9 — REAL-WORLD EXAMPLES
 * File 1 of 3: Production-Grade Generic Stack
 *
 * ═══════════════════════════════════════════════════════════════
 * A COMPLETE GENERIC STACK
 * ═══════════════════════════════════════════════════════════════
 *
 * This is a fully fleshed-out generic Stack<E> that mirrors
 * the kind of data structure you'd write in a real codebase:
 *
 *   • Implements Iterable<E> for for-each support
 *   • Implements toString, equals, hashCode
 *   • Throws typed exceptions with descriptive messages
 *   • Uses PECS correctly on methods that accept collections
 *   • Has a static factory method
 *
 * Study this file before doing the real-world tasks.
 */

import java.util.*;

public class _01_GenericStack {

    static class Stack<E> implements Iterable<E> {

        private Object[] elements;
        private int      size = 0;
        private static final int DEFAULT_CAPACITY = 16;

        // ── Constructors ───────────────────────────────────────────
        @SuppressWarnings("unchecked")
        public Stack() {
            elements = new Object[DEFAULT_CAPACITY];
        }

        @SuppressWarnings("unchecked")
        public Stack(int initialCapacity) {
            if (initialCapacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
            elements = new Object[initialCapacity];
        }

        /** Static factory — creates a stack pre-loaded from a collection. */
        public static <E> Stack<E> of(Collection<? extends E> source) {
            Stack<E> stack = new Stack<>();
            for (E item : source) stack.push(item);
            return stack;
        }

        // ── Core operations ────────────────────────────────────────
        public void push(E item) {
            ensureCapacity();
            elements[size++] = item;
        }

        @SuppressWarnings("unchecked")
        public E pop() {
            checkNotEmpty("pop");
            E item = (E) elements[--size];
            elements[size] = null;   // help GC
            return item;
        }

        @SuppressWarnings("unchecked")
        public E peek() {
            checkNotEmpty("peek");
            return (E) elements[size - 1];
        }

        public boolean isEmpty()  { return size == 0; }
        public int     size()     { return size;       }

        /** Pushes all elements from a collection (PECS: source is a producer). */
        public void pushAll(Iterable<? extends E> source) {
            for (E item : source) push(item);
        }

        /** Pops all elements into a collection (PECS: dest is a consumer). */
        public void popAll(Collection<? super E> dest) {
            while (!isEmpty()) dest.add(pop());
        }

        // ── Iterable ───────────────────────────────────────────────
        @Override
        public Iterator<E> iterator() {
            return new Iterator<>() {
                int cursor = size - 1;   // iterate top-to-bottom

                @Override public boolean hasNext() { return cursor >= 0; }

                @SuppressWarnings("unchecked")
                @Override public E next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    return (E) elements[cursor--];
                }
            };
        }

        // ── Internals ──────────────────────────────────────────────
        private void ensureCapacity() {
            if (size == elements.length) {
                elements = Arrays.copyOf(elements, size * 2 + 1);
            }
        }

        private void checkNotEmpty(String operation) {
            if (isEmpty()) throw new EmptyStackException();
        }

        // ── Object methods ─────────────────────────────────────────
        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Stack)) return false;
            Stack<E> other = (Stack<E>) obj;
            if (size != other.size) return false;
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(elements[i], other.elements[i])) return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(Arrays.copyOf(elements, size));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Stack[");
            for (int i = size - 1; i >= 0; i--) {   // top first
                sb.append(elements[i]);
                if (i > 0) sb.append(", ");
            }
            return sb.append("]").toString();
        }
    }

    public static void main(String[] args) {

        // Basic usage
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");
        System.out.println("Stack: " + stack);           // Stack[third, second, first]
        System.out.println("Peek:  " + stack.peek());    // third
        System.out.println("Pop:   " + stack.pop());     // third
        System.out.println("Size:  " + stack.size());    // 2

        // for-each (top-to-bottom)
        System.out.print("Iterate: ");
        for (String s : stack) System.out.print(s + " ");
        System.out.println();

        // pushAll with PECS
        List<String> more = List.of("fourth", "fifth");
        stack.pushAll(more);
        System.out.println("After pushAll: " + stack);

        // popAll with PECS — pop into a List<Object>
        List<Object> destination = new ArrayList<>();
        stack.popAll(destination);
        System.out.println("PopAll result: " + destination);
        System.out.println("Stack empty: "  + stack.isEmpty());

        // Factory method
        Stack<Integer> nums = Stack.of(List.of(1, 2, 3, 4, 5));
        System.out.println("From factory: " + nums);

        // EmptyStackException
        try {
            new Stack<>().pop();
        } catch (EmptyStackException e) {
            System.out.println("EmptyStackException caught");
        }
    }
}
