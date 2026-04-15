/**
 * SOLUTIONS — Chapter 6: Generic Interfaces
 */
import java.util.ArrayList;
import java.util.List;

public class Tasks_GenericInterfaces_Solution {

    // Task 1
    static class Box<T extends Comparable<T>> implements Comparable<Box<T>> {
        private final T value;
        public Box(T value) { this.value = value; }
        public T getValue() { return value; }

        @Override
        public int compareTo(Box<T> other) {
            return this.value.compareTo(other.value);
        }

        @Override public String toString() { return "Box[" + value + "]"; }
    }

    // Task 2
    interface Converter<F, T> {
        T convert(F from);
    }

    static class StringToInteger implements Converter<String, Integer> {
        @Override public Integer convert(String from) { return Integer.parseInt(from); }
    }

    static class IntegerToString implements Converter<Integer, String> {
        @Override public String convert(Integer from) { return String.valueOf(from); }
    }

    public static <F, T> List<T> convertAll(List<F> items, Converter<F, T> conv) {
        List<T> result = new ArrayList<>(items.size());
        for (F item : items) result.add(conv.convert(item));
        return result;
    }

    // Task 3
    interface Predicate<T> {
        boolean test(T value);

        default Predicate<T> and(Predicate<T> other) {
            return value -> this.test(value) && other.test(value);
        }

        default Predicate<T> or(Predicate<T> other) {
            return value -> this.test(value) || other.test(value);
        }

        default Predicate<T> negate() {
            return value -> !this.test(value);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) result.add(item);
        }
        return result;
    }

    public static void main(String[] args) {
        // Task 1
        List<Box<Integer>> boxes = new ArrayList<>();
        boxes.add(new Box<>(5)); boxes.add(new Box<>(1)); boxes.add(new Box<>(3));
        java.util.Collections.sort(boxes);
        System.out.println(boxes);   // [Box[1], Box[3], Box[5]]

        // Task 2
        System.out.println(new StringToInteger().convert("123"));
        System.out.println(convertAll(List.of("1","2","3"), new StringToInteger()));

        // Task 3
        Predicate<Integer> isEven     = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        List<Integer> nums = List.of(-4,-3,-2,-1,0,1,2,3,4);
        System.out.println(filter(nums, isEven));
        System.out.println(filter(nums, isEven.and(isPositive)));
        System.out.println(filter(nums, isEven.negate()));
    }
}
