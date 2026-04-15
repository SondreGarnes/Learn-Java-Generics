/**
 * SOLUTIONS — Chapter 7: Type Erasure
 */
import java.util.ArrayList;
import java.util.List;

public class _02_Tasks_TypeErasure_Solution {

    // Task 1 — Analysis answers as comments:

    public static void analyzeSnippets() {
        // Snippet A:
        List<String> a = new ArrayList<>();
        a.add("hello");
        // Object o = a.get(0);   — returns Object (erased). COMPILES.
        // String s = a.get(0);   — compiler inserts (String) cast automatically. COMPILES.
        // a) Yes, compiles.
        // b) Erased: List list = new ArrayList(); list.add("hello"); String s = (String)list.get(0);
        // c) No runtime risk — the cast is valid because we only added Strings.

        // Snippet B:
        // List<String> b = new ArrayList<Integer>();
        // a) DOES NOT COMPILE. The declared type List<String> is incompatible with ArrayList<Integer>.
        // b) The compiler rejects this before erasure — it's a type mismatch.

        // Snippet C:
        List<?> c = new ArrayList<String>();
        // c.add("hello");  — DOES NOT COMPILE. ? is unknown; compiler refuses adds.
        Object item = c.get(0);
        // a) Cannot add — compiler rejects it.
        // b) Reads return Object because ? could be any type; Object is the only safe type.

        // Snippet D:
        // new T()      — NOT allowed. After erasure T becomes Object; the JVM can't call
        //                new Object() because T's actual constructor is unknown at runtime.
        // T[] arr      — NOT directly allowed. Generic arrays cause heap pollution;
        //                use Object[] arr = new Object[10] and cast, or use ArrayList<T>.
    }

    // Task 2 — TypeSafeList
    static class TypeSafeList<T> {
        private final Class<T> type;
        private final List<Object> inner = new ArrayList<>();

        public TypeSafeList(Class<T> type) { this.type = type; }

        public void add(Object obj) {
            if (!type.isInstance(obj)) {
                throw new ClassCastException(
                    "Expected " + type.getSimpleName() + " but got " +
                    (obj == null ? "null" : obj.getClass().getSimpleName()));
            }
            inner.add(obj);
        }

        @SuppressWarnings("unchecked")
        public T get(int index) { return (T) inner.get(index); }

        public int size()       { return inner.size();  }

        @Override public String toString() { return inner.toString(); }
    }

    // Task 3 — dangerousCast analysis
    @SuppressWarnings("unchecked")
    public static <T> T dangerousCast(Object obj) {
        return (T) obj;  // The cast here erases to (Object) obj — always succeeds!
    }
    // WHY does the exception occur at the assignment, not inside dangerousCast?
    // Because inside dangerousCast, T has been erased to Object.
    // So (T) obj is effectively (Object) obj — no cast exception possible.
    // The compiler inserts the REAL cast (String) at the CALL SITE:
    //   String bad = (String) dangerousCast(42);
    // THAT is where the ClassCastException fires.

    public static void main(String[] args) {
        TypeSafeList<String> safeList = new TypeSafeList<>(String.class);
        safeList.add("hello");
        safeList.add("world");
        System.out.println(safeList.size());    // 2
        System.out.println(safeList.get(0));    // hello
        System.out.println(safeList);

        try {
            safeList.add(42);
        } catch (ClassCastException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        // dangerousCast
        String s = dangerousCast("hello");
        System.out.println("Works: " + s);

        try {
            String bad = dangerousCast(42);
            System.out.println("Oops: " + bad);
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at call site: " + e.getMessage());
        }
    }
}
