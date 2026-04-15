/**
 * CHAPTER 7 — TASKS: Type Erasure
 *
 * These tasks focus on UNDERSTANDING the implications of type erasure
 * and working around its limitations.
 *
 * Solutions: Solutions/Chapter07/02_Tasks_TypeErasure_Solution.java
 */

import java.util.ArrayList;
import java.util.List;

public class _02_Tasks_TypeErasure {

    // ═══════════════════════════════════════════════════════════════
    // TASK 1 — Explain what happens (no code needed — write comments)
    // ═══════════════════════════════════════════════════════════════
    // For each snippet below, describe:
    //   a) Does it compile?
    //   b) What does the erased version look like?
    //   c) Is there a runtime risk?
    //
    // Write your answers as comments inside the method analyzeSnippets().

    public static void analyzeSnippets() {
        // Snippet A:
        List<String> a = new ArrayList<>();
        a.add("hello");
        // Object o = a.get(0);    // What type comes back?
        // String s = a.get(0);    // What does the compiler insert here?

        // YOUR ANALYSIS FOR SNIPPET A:
        // a) Compiles? ...
        // b) Erased version: ...
        // c) Runtime risk? ...


        // Snippet B:
        // List<String> b = new ArrayList<Integer>();  // does this compile?

        // YOUR ANALYSIS FOR SNIPPET B:
        // a) Compiles? ...
        // b) Why or why not? ...


        // Snippet C:
        List<?> c = new ArrayList<String>();
        // c.add("hello");   // does this compile?
        Object item = c.get(0);

        // YOUR ANALYSIS FOR SNIPPET C:
        // a) Can we add "hello"? ...
        // b) Why can we only read as Object? ...


        // Snippet D:
        // Can we do: new T()   inside a generic class?
        // Can we do: T[] arr = new T[10]  inside a generic class?

        // YOUR ANALYSIS FOR SNIPPET D:
        // a) ...
        // b) ...
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 2 — TypeSafeList using a Class<T> token
    // ═══════════════════════════════════════════════════════════════
    // Because of type erasure, we can't check instanceof List<String>
    // at runtime. A common workaround is to store a Class<T> token.
    //
    // Implement TypeSafeList<T>:
    //   • Stores a Class<T> token (passed to constructor)
    //   • Wraps an ArrayList<Object> internally
    //   • add(Object obj) — adds ONLY if obj is an instance of T;
    //     otherwise throw ClassCastException with a descriptive message
    //   • get(int index) — returns the element cast to T
    //   • size()         — returns the number of elements
    //   • toString()     — delegates to the inner list

    static class TypeSafeList<T> {
        // TODO: fields

        public TypeSafeList(Class<T> type) {
            // TODO
        }

        public void add(Object obj) {
            // TODO — check type at runtime using the token
        }

        public T get(int index) {
            return null; // replace
        }

        public int size() {
            return 0; // replace
        }

        @Override
        public String toString() {
            return ""; // replace
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TASK 3 — Heap pollution simulation
    // ═══════════════════════════════════════════════════════════════
    // "Heap pollution" is when a variable of a parameterized type refers
    // to an object that is NOT of that parameterized type. It can happen
    // through unchecked casts or raw types.
    //
    // The method below has an intentional unsafe cast. Your task:
    //   a) Read the code and predict what happens when it runs.
    //   b) Add a comment explaining WHY the ClassCastException occurs.
    //   c) Fix the method signature or usage to make it safe.
    //
    // NOTE: this currently produces an UNCHECKED WARNING or error.
    //       Read carefully before running.

    @SuppressWarnings("unchecked")
    public static <T> T dangerousCast(Object obj) {
        return (T) obj;   // unchecked cast — T is erased to Object here
    }

    // ═══════════════════════════════════════════════════════════════
    // TEST
    // ═══════════════════════════════════════════════════════════════
    public static void main(String[] args) {

        // Task 1 — no output needed, just fill in the comments above

        // Task 2
        System.out.println("=== Task 2: TypeSafeList ===");
        TypeSafeList<String> safeList = new TypeSafeList<>(String.class);
        safeList.add("hello");
        safeList.add("world");
        System.out.println("Size: " + safeList.size());    // 2
        System.out.println("Get 0: " + safeList.get(0));  // hello
        System.out.println(safeList);

        try {
            safeList.add(42);  // should throw — Integer is not String
        } catch (ClassCastException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        // Task 3
        System.out.println("\n=== Task 3: dangerousCast ===");
        // This appears to work — but why?
        String s = dangerousCast("hello");
        System.out.println("Seems fine: " + s);

        // This will throw at the ASSIGNMENT site, not inside dangerousCast.
        // Predict the exception before uncommenting:
        try {
            String bad = dangerousCast(42);  // 42 is Integer, assigned to String
            System.out.println("Oops: " + bad);
        } catch (ClassCastException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        // Write your explanation as a comment:
        // WHY does the exception occur at the assignment, not inside dangerousCast?
        // ANSWER: ...
    }
}
