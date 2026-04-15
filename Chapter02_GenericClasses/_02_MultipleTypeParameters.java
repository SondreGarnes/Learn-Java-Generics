/**
 * CHAPTER 2 — GENERIC CLASSES
 * File 2 of 3: Multiple Type Parameters
 *
 * ═══════════════════════════════════════════════════════════════
 * MULTIPLE TYPE PARAMETERS
 * ═══════════════════════════════════════════════════════════════
 *
 * A class can have more than one type parameter, separated by commas:
 *
 *   class Pair<A, B> {
 *       A first;
 *       B second;
 *   }
 *
 * The most famous real-world example is java.util.Map<K, V>
 * where K is the key type and V is the value type.
 *
 * ───────────────────────────────────────────────────────────────
 * WHEN TO USE MULTIPLE PARAMETERS
 * ───────────────────────────────────────────────────────────────
 *   • When your abstraction naturally pairs or groups distinct types.
 *   • When one type parameter alone is too restrictive.
 *
 * Common patterns:
 *   Pair<K, V>     — key-value pair
 *   Either<L, R>   — holds one of two possible types (functional style)
 *   Triple<A,B,C>  — three-element tuple
 *   Result<T, E>   — a value T or an error E
 */
public class _02_MultipleTypeParameters {

    /**
     * Pair<A, B> holds two values of potentially different types.
     * This is a general-purpose tuple — extremely useful in practice.
     */
    static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            this.first  = first;
            this.second = second;
        }

        public A getFirst()  { return first;  }
        public B getSecond() { return second; }

        /** Returns a new Pair with the elements swapped. */
        public Pair<B, A> swap() {
            return new Pair<>(second, first);
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    /**
     * Triple<A, B, C> — a three-element tuple.
     * Notice how adding a third parameter is straightforward.
     */
    static class Triple<A, B, C> {
        private final A first;
        private final B second;
        private final C third;

        public Triple(A first, B second, C third) {
            this.first  = first;
            this.second = second;
            this.third  = third;
        }

        public A getFirst()  { return first;  }
        public B getSecond() { return second; }
        public C getThird()  { return third;  }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ", " + third + ")";
        }
    }

    /**
     * Result<T, E> models either a success value T or an error E.
     * This is a simplified version of the Either / Result pattern
     * common in functional programming.
     */
    static class Result<T, E extends Exception> {
        private final T value;
        private final E error;

        private Result(T value, E error) {
            this.value = value;
            this.error = error;
        }

        public static <T, E extends Exception> Result<T, E> success(T value) {
            return new Result<>(value, null);
        }

        public static <T, E extends Exception> Result<T, E> failure(E error) {
            return new Result<>(null, error);
        }

        public boolean isSuccess() { return error == null; }
        public T getValue()        { return value; }
        public E getError()        { return error; }

        @Override
        public String toString() {
            return isSuccess() ? "Success(" + value + ")" : "Failure(" + error.getMessage() + ")";
        }
    }

    public static void main(String[] args) {

        // Pair with String and Integer
        Pair<String, Integer> nameAge = new Pair<>("Alice", 30);
        System.out.println("Pair: " + nameAge);
        System.out.println("Name: " + nameAge.getFirst());
        System.out.println("Age:  " + nameAge.getSecond());

        // Swap — notice the return type flips to Pair<Integer, String>
        Pair<Integer, String> swapped = nameAge.swap();
        System.out.println("Swapped: " + swapped);

        // Triple
        Triple<String, Integer, Boolean> triple = new Triple<>("Bob", 25, true);
        System.out.println("Triple: " + triple);

        // Result
        Result<Integer, RuntimeException> ok  = Result.success(42);
        Result<Integer, RuntimeException> err = Result.failure(new RuntimeException("not found"));
        System.out.println(ok);
        System.out.println(err);
    }
}
