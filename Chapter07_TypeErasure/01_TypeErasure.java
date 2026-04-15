/**
 * CHAPTER 7 — TYPE ERASURE
 * File 1 of 2: How Type Erasure Works
 *
 * ═══════════════════════════════════════════════════════════════
 * WHAT IS TYPE ERASURE?
 * ═══════════════════════════════════════════════════════════════
 *
 * Java generics are a COMPILE-TIME feature only. At runtime, the JVM
 * has NO knowledge of generic type arguments. The compiler:
 *
 *   1. Uses type parameters to check correctness.
 *   2. Erases them and replaces with their bounds (or Object).
 *   3. Inserts casts where needed automatically.
 *
 * So this source code:
 *   List<String> list = new ArrayList<>();
 *   list.add("hello");
 *   String s = list.get(0);
 *
 * Compiles to bytecode equivalent to:
 *   List list = new ArrayList();          // raw type
 *   list.add("hello");
 *   String s = (String) list.get(0);     // auto-inserted cast
 *
 * ───────────────────────────────────────────────────────────────
 * WHAT GETS ERASED
 * ───────────────────────────────────────────────────────────────
 *   <T>           →  Object
 *   <T extends Number>  →  Number  (the bound becomes the type)
 *   List<String>  →  List
 *   Pair<K,V>     →  Pair
 *
 * ───────────────────────────────────────────────────────────────
 * CONSEQUENCES OF TYPE ERASURE
 * ───────────────────────────────────────────────────────────────
 *   1. Cannot use instanceof with parameterized types:
 *        if (obj instanceof List<String>)   // COMPILE ERROR
 *        if (obj instanceof List<?>)         // OK — wildcard only
 *
 *   2. Cannot create instances of T:
 *        new T()    // COMPILE ERROR
 *
 *   3. Cannot create arrays of parameterized types:
 *        new List<String>[10]    // COMPILE ERROR
 *
 *   4. Static fields/methods cannot use the class T:
 *        static T field;        // COMPILE ERROR in a class Foo<T>
 *
 *   5. Overloading by generic parameter doesn't work:
 *        void foo(List<String> l) { }
 *        void foo(List<Integer> l) { }   // DUPLICATE after erasure!
 *
 * ───────────────────────────────────────────────────────────────
 * REIFIABLE TYPES
 * ───────────────────────────────────────────────────────────────
 * A type is "reifiable" if its full type information is available at
 * runtime. Reifiable types include:
 *   • Primitives (int, double, ...)
 *   • Non-generic classes (String, Integer, ...)
 *   • Raw types (List, Map, ...)
 *   • Unbounded wildcards (List<?>, Map<?,?>)
 *
 * Parameterized types like List<String> are NOT reifiable.
 */

import java.util.ArrayList;
import java.util.List;

public class TypeErasure {

    // ─── Demonstrate that T becomes Object after erasure ──────────
    static class Erased<T> {
        private T value;

        public Erased(T value) { this.value = value; }

        // At runtime this returns Object — the cast is inserted by compiler
        public T getValue() { return value; }

        // At runtime T is just Object, so we can only use Object methods
        public String valueAsString() { return value.toString(); }
    }

    // ─── Class with bounded T — erases to Number ──────────────────
    static class NumberHolder<T extends Number> {
        private T value;
        public NumberHolder(T value) { this.value = value; }

        // At runtime value is Number — doubleValue() is available
        public double doubled() { return value.doubleValue() * 2; }
    }

    // ─── Workaround: pass Class<T> token to retain type at runtime ─
    static class TypeAware<T> {
        private final Class<T> type;
        private T value;

        public TypeAware(Class<T> type, T value) {
            this.type  = type;
            this.value = value;
        }

        public Class<T> getType() { return type; }

        // Can now do instanceof-like check at runtime
        public boolean isInstance(Object obj) {
            return type.isInstance(obj);
        }

        @Override
        public String toString() {
            return "TypeAware<" + type.getSimpleName() + ">[" + value + "]";
        }
    }

    public static void main(String[] args) {

        // Both List<String> and List<Integer> are just "List" at runtime
        List<String>  strings  = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        System.out.println("Same class? " + (strings.getClass() == integers.getClass())); // true
        System.out.println("Class name: " + strings.getClass().getName()); // java.util.ArrayList

        // Erased<T>
        Erased<String> e1 = new Erased<>("hello");
        Erased<Integer> e2 = new Erased<>(42);
        System.out.println(e1.getValue());          // hello
        System.out.println(e2.getValue());          // 42
        System.out.println(e1.valueAsString());     // hello

        // instanceof with raw type (OK) vs parameterized (not allowed)
        Object obj = new ArrayList<String>();
        if (obj instanceof ArrayList) {             // raw — OK
            System.out.println("Is an ArrayList");
        }
        // if (obj instanceof ArrayList<String>) { } // COMPILE ERROR

        // Class token workaround
        TypeAware<String> ta = new TypeAware<>(String.class, "world");
        System.out.println(ta);
        System.out.println("Is String: " + ta.isInstance("yes"));   // true
        System.out.println("Is Integer:" + ta.isInstance(42));       // false

        // Bounded erasure — T extends Number → Number
        NumberHolder<Integer> nh = new NumberHolder<>(21);
        System.out.println("Doubled: " + nh.doubled());  // 42.0
    }
}
