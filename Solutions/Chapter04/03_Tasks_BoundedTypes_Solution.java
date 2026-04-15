/**
 * SOLUTIONS — Chapter 4: Bounded Type Parameters
 */
import java.util.List;
import java.util.ArrayList;

public class Tasks_BoundedTypes_Solution {

    // Task 1
    public static <T extends Number> double average(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("List must not be empty");
        double sum = 0;
        for (T n : list) sum += n.doubleValue();
        return sum / list.size();
    }

    // Task 2
    public static <T extends Comparable<T>> boolean isSorted(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) return false;
        }
        return true;
    }

    // Task 3
    public static <T extends Comparable<T>> void printMinMax(List<T> list) {
        if (list.isEmpty()) { System.out.println("Empty"); return; }
        T min = list.get(0), max = list.get(0);
        for (T element : list) {
            if (element.compareTo(min) < 0) min = element;
            if (element.compareTo(max) > 0) max = element;
        }
        System.out.println("Min: " + min + "  Max: " + max);
    }

    // Task 4
    static class BoundedPair<T extends Comparable<T>> {
        private final T a, b;

        public BoundedPair(T a, T b) { this.a = a; this.b = b; }

        public T min() { return a.compareTo(b) <= 0 ? a : b; }
        public T max() { return a.compareTo(b) >= 0 ? a : b; }

        public boolean overlaps(BoundedPair<T> other) {
            return this.min().compareTo(other.max()) <= 0
                && other.min().compareTo(this.max()) <= 0;
        }

        @Override public String toString() { return "[" + min() + ", " + max() + "]"; }
    }

    public static void main(String[] args) {
        System.out.println(average(List.of(1,2,3,4,5)));    // 3.0
        System.out.println(isSorted(List.of(1,2,3,4)));     // true
        System.out.println(isSorted(List.of(1,3,2,4)));     // false
        printMinMax(List.of(5,3,9,1,7));

        BoundedPair<Integer> p1 = new BoundedPair<>(3,7);
        BoundedPair<Integer> p2 = new BoundedPair<>(5,10);
        BoundedPair<Integer> p3 = new BoundedPair<>(8,15);
        System.out.println(p1.overlaps(p2)); // true
        System.out.println(p1.overlaps(p3)); // false
        System.out.println(p2.overlaps(p3)); // true
    }
}
