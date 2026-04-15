/**
 * CHAPTER 1 — INTRODUCTION TO GENERICS
 * File 1 of 2: What Are Generics?
 *
 * ═══════════════════════════════════════════════════════════════
 * WHAT ARE GENERICS?
 * ═══════════════════════════════════════════════════════════════
 *
 * Generics allow you to write classes, interfaces, and methods that
 * work with ANY type while still providing COMPILE-TIME type safety.
 *
 * The core idea: instead of hardcoding a specific type (e.g. String),
 * you use a TYPE PARAMETER (a placeholder, commonly written as T) that
 * gets filled in by the caller.
 *
 *   class Box<T> {        // T is the type parameter
 *       T value;          // T is used like a real type
 *   }
 *
 *   Box<String> b = new Box<>();   // T = String for this instance
 *   Box<Integer> c = new Box<>();  // T = Integer for this instance
 *
 * ───────────────────────────────────────────────────────────────
 * NAMING CONVENTIONS (by Java tradition — not enforced by compiler)
 * ───────────────────────────────────────────────────────────────
 *   T   — general Type
 *   E   — Element (used in collections like List<E>)
 *   K   — Key   (used in maps: Map<K, V>)
 *   V   — Value (used in maps: Map<K, V>)
 *   N   — Number
 *   R   — Return type (common in functional interfaces)
 *
 * ───────────────────────────────────────────────────────────────
 * BENEFITS
 * ───────────────────────────────────────────────────────────────
 *   1. Type safety   — the compiler rejects wrong types at compile time.
 *   2. No casting    — you get the correct type back without (Type) casts.
 *   3. Reusability   — one class/method works for many types.
 *   4. Readability   — code clearly documents what types it expects.
 */
public class _01_WhatAreGenerics {

    // ─── A simple generic box ────────────────────────────────────
    // T is the type parameter. You can read "<T>" as "of type T".
    static class Box<T> {
        private T value;

        public Box(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;          // returns T — no cast needed by the caller
        }

        @Override
        public String toString() {
            return "Box[" + value + "]";
        }
    }

    public static void main(String[] args) {

        // When we write Box<String>, the compiler substitutes T → String.
        Box<String> stringBox = new Box<>("Hello, Generics!");
        String s = stringBox.getValue();   // no cast needed — type is String
        System.out.println(stringBox);     // Box[Hello, Generics!]

        // Same class, different type parameter.
        Box<Integer> intBox = new Box<>(42);
        int n = intBox.getValue();         // unboxed automatically
        System.out.println(intBox);        // Box[42]

        // The compiler PREVENTS mixing types:
        // Box<String> bad = new Box<>(123);  // ← compile error!

        System.out.println("String box type: " + stringBox.getValue().getClass().getSimpleName());
        System.out.println("Integer box type: " + intBox.getValue().getClass().getSimpleName());
    }
}
