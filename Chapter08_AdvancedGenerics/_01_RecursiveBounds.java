/**
 * CHAPTER 8 — ADVANCED GENERICS
 * File 1 of 3: Recursive Bounds & Self-Referential Patterns
 *
 * ═══════════════════════════════════════════════════════════════
 * RECURSIVE TYPE BOUNDS
 * ═══════════════════════════════════════════════════════════════
 *
 * The most common form:
 *
 *   <T extends Comparable<T>>
 *
 * Read: "T must be comparable to itself."
 *
 * This is called a "recursive bound" because T appears in its own bound.
 * It ensures that compareTo() accepts a T, not just any Object:
 *
 *   interface Comparable<T> {
 *       int compareTo(T other);   // T must accept T as argument
 *   }
 *
 * Without the recursive bound you'd only know T extends Comparable<?>,
 * meaning compareTo() could return garbage for unrelated types.
 *
 * ───────────────────────────────────────────────────────────────
 * BUILDER PATTERN WITH RECURSIVE BOUNDS
 * ───────────────────────────────────────────────────────────────
 * A famous use of recursive bounds is the "fluent builder" or
 * "curiously recurring template pattern" (CRTP):
 *
 *   abstract class Builder<T extends Builder<T>> {
 *       abstract T self();
 *       T withName(String name) { ... ; return self(); }
 *   }
 *
 * Each subclass returns itself from fluent methods, preserving
 * the concrete subclass type through the chain.
 */

import java.util.List;

public class _01_RecursiveBounds {

    // ─── 1. Recursive bound for comparison ───────────────────────
    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static <T extends Comparable<T>> T clamp(T value, T lo, T hi) {
        if (value.compareTo(lo) < 0) return lo;
        if (value.compareTo(hi) > 0) return hi;
        return value;
    }

    // ─── 2. Fluent builder with recursive bound (CRTP) ───────────
    abstract static class AnimalBuilder<B extends AnimalBuilder<B>> {
        protected String name;
        protected int    age;

        // Returns B (the concrete subclass), not AnimalBuilder
        @SuppressWarnings("unchecked")
        public B name(String name) {
            this.name = name;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B age(int age) {
            this.age = age;
            return (B) this;
        }
    }

    static class Dog {
        final String name;
        final int    age;
        final String breed;

        Dog(DogBuilder b) {
            name  = b.name;
            age   = b.age;
            breed = b.breed;
        }

        @Override public String toString() {
            return "Dog{name=" + name + ", age=" + age + ", breed=" + breed + "}";
        }
    }

    static class DogBuilder extends AnimalBuilder<DogBuilder> {
        private String breed;

        public DogBuilder breed(String breed) {
            this.breed = breed;
            return this;   // returns DogBuilder, not AnimalBuilder
        }

        public Dog build() { return new Dog(this); }
    }

    // ─── 3. Enum-like pattern with recursive bound ────────────────
    // Each Planet knows how to compare itself to other Planets.
    enum Planet {
        MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE;

        // Enums implement Comparable<Enum<E extends Enum<E>>> — Java's own CRTP!
        public boolean isCloserToSunThan(Planet other) {
            return this.ordinal() < other.ordinal();
        }
    }

    public static void main(String[] args) {

        // Recursive bound for comparison
        System.out.println("Max(3,7): " + max(3, 7));           // 7
        System.out.println("Max(\"apple\",\"pear\"): " + max("apple", "pear")); // pear
        System.out.println("Clamp(15, 1, 10): " + clamp(15, 1, 10));  // 10
        System.out.println("Clamp(5, 1, 10):  " + clamp(5, 1, 10));   // 5

        // Fluent builder
        Dog dog = new DogBuilder()
                .name("Rex")      // returns DogBuilder (not AnimalBuilder)
                .age(3)           // returns DogBuilder
                .breed("Husky")   // specific to DogBuilder
                .build();
        System.out.println(dog);

        // Planet enum (Java's own recursive bound in action)
        System.out.println("Earth closer than Mars: " +
                Planet.EARTH.isCloserToSunThan(Planet.MARS));      // true
        System.out.println("Saturn closer than Venus: " +
                Planet.SATURN.isCloserToSunThan(Planet.VENUS));    // false
    }
}
