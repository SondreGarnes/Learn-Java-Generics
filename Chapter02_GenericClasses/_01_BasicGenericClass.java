/**
 * CHAPTER 2 — GENERIC CLASSES
 * File 1 of 3: Basic Generic Class
 *
 * ═══════════════════════════════════════════════════════════════
 * DECLARING A GENERIC CLASS
 * ═══════════════════════════════════════════════════════════════
 *
 * Syntax:
 *
 *   class ClassName<TypeParam1, TypeParam2, ...> {
 *       // TypeParam1, TypeParam2 can be used anywhere inside the class
 *   }
 *
 * Rules:
 *   • Type parameters live in angle brackets <> after the class name.
 *   • They can be used as field types, method parameter types, and
 *     return types anywhere inside the class body.
 *   • They are resolved when the caller provides the concrete type:
 *       new ClassName<String>()   →  TypeParam1 becomes String
 *
 * ───────────────────────────────────────────────────────────────
 * DIAMOND OPERATOR (<>)
 * ───────────────────────────────────────────────────────────────
 * Since Java 7, you can omit the type on the right-hand side and
 * let the compiler infer it — this is the "diamond operator":
 *
 *   GenericBox<String> box = new GenericBox<>();   // <> inferred as String
 *
 * ───────────────────────────────────────────────────────────────
 * RAW TYPES (AVOID!)
 * ───────────────────────────────────────────────────────────────
 * Omitting the type parameter entirely gives you a "raw type":
 *
 *   GenericBox box = new GenericBox();  // raw type — avoid this!
 *
 * Raw types exist only for backward compatibility. They lose all
 * type safety and generate compiler warnings.
 */
public class _01_BasicGenericClass {

    /**
     * A generic container that holds exactly one value of type T.
     * T is resolved to a concrete type when the object is created.
     */
    static class Container<T> {
        private T item;

        // Constructor: accepts a T, stores it
        public Container(T item) {
            this.item = item;
        }

        // Getter: returns T — no casting needed by the caller
        public T getItem() {
            return item;
        }

        // Setter: replaces the stored value with another T
        public void setItem(T item) {
            this.item = item;
        }

        // isEmpty checks if the container holds null
        public boolean isEmpty() {
            return item == null;
        }

        @Override
        public String toString() {
            return "Container{item=" + item + "}";
        }
    }

    public static void main(String[] args) {

        // T = String
        Container<String> stringContainer = new Container<>("Java");
        System.out.println(stringContainer);
        System.out.println("Item: " + stringContainer.getItem());

        stringContainer.setItem("Generics");
        System.out.println("After set: " + stringContainer);

        // T = Double
        Container<Double> doubleContainer = new Container<>(3.14);
        double pi = doubleContainer.getItem();   // no cast needed
        System.out.println("Pi: " + pi);

        // T = Boolean
        Container<Boolean> boolContainer = new Container<>(true);
        if (boolContainer.getItem()) {
            System.out.println("Flag is true");
        }

        // null container
        Container<String> empty = new Container<>(null);
        System.out.println("Is empty: " + empty.isEmpty());
    }
}
