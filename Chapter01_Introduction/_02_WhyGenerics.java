/**
 * CHAPTER 1 — INTRODUCTION TO GENERICS
 * File 2 of 2: Why Do We Need Generics?
 *
 * ═══════════════════════════════════════════════════════════════
 * THE PROBLEM BEFORE GENERICS (Java 1.4 and earlier)
 * ═══════════════════════════════════════════════════════════════
 *
 * Before generics, reusable containers stored everything as Object.
 * This caused two major problems:
 *
 *   1. UNSAFE CASTING — you had to manually cast every retrieval,
 *      and a wrong cast caused a ClassCastException at RUNTIME
 *      (not caught by the compiler).
 *
 *   2. NO TYPE ENFORCEMENT — nothing stopped you from accidentally
 *      putting the wrong type into a container.
 *
 * Example (old-style, without generics):
 *
 *   List list = new ArrayList();
 *   list.add("hello");
 *   list.add(42);          // oops — no error!
 *   String s = (String) list.get(1);  // ClassCastException at runtime!
 *
 * ───────────────────────────────────────────────────────────────
 * THE SOLUTION WITH GENERICS
 * ───────────────────────────────────────────────────────────────
 *   List<String> list = new ArrayList<>();
 *   list.add("hello");
 *   list.add(42);          // COMPILE ERROR — caught immediately
 *   String s = list.get(0); // no cast needed
 *
 * The compiler does the safety checking so you don't have to.
 */

import java.util.ArrayList;
import java.util.List;

public class _02_WhyGenerics {

    // ─── OLD WAY: Object-based (pre-generics style) ──────────────
    static class OldBox {
        private Object value;

        public OldBox(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;    // caller must cast
        }
    }

    // ─── NEW WAY: Generic ─────────────────────────────────────────
    static class GenericBox<T> {
        private T value;

        public GenericBox(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;    // no cast needed
        }
    }

    public static void main(String[] args) {

        // ── Old way ──────────────────────────────────────────────
        OldBox oldBox = new OldBox("I am a String");
        // Must cast — and if you guess wrong, it blows up at runtime:
        String fromOld = (String) oldBox.getValue();
        System.out.println("Old way: " + fromOld);

        OldBox sneakyBox = new OldBox(999);   // stored as Object
        // This line compiles but throws ClassCastException at runtime!
        try {
            String oops = (String) sneakyBox.getValue();
        } catch (ClassCastException e) {
            System.out.println("Old way runtime error: " + e.getMessage());
        }

        // ── New way ───────────────────────────────────────────────
        GenericBox<String> newBox = new GenericBox<>("I am a String");
        String fromNew = newBox.getValue();   // safe, no cast
        System.out.println("New way: " + fromNew);

        // The line below would be a COMPILE ERROR — uncomment to verify:
        // GenericBox<String> bad = new GenericBox<>(999);

        // ── Generics with collections ─────────────────────────────
        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        // names.add(42);   // COMPILE ERROR — the compiler catches this

        for (String name : names) {
            System.out.println("Name: " + name);  // no cast needed
        }
    }
}
