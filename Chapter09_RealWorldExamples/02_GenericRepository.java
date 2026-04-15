/**
 * CHAPTER 9 — REAL-WORLD EXAMPLES
 * File 2 of 3: Generic Repository Pattern
 *
 * ═══════════════════════════════════════════════════════════════
 * THE REPOSITORY PATTERN
 * ═══════════════════════════════════════════════════════════════
 *
 * A Repository is a common design pattern in enterprise Java (Spring, etc.)
 * that separates domain objects from data-access logic.
 *
 * A generic Repository<T, ID> provides:
 *   save(T entity)
 *   findById(ID id) → Optional<T>
 *   findAll()       → List<T>
 *   delete(ID id)
 *   count()
 *
 * By making it generic, one interface works for ALL entity types.
 * Concrete repositories simply extend it with specific types.
 *
 * This pattern is at the core of Spring Data JPA's CrudRepository<T, ID>.
 */

import java.util.*;

public class GenericRepository {

    // ─── 1. The generic entity marker interface ───────────────────
    interface Entity<ID> {
        ID getId();
    }

    // ─── 2. Generic repository interface ─────────────────────────
    interface Repository<T extends Entity<ID>, ID> {
        T             save(T entity);
        Optional<T>   findById(ID id);
        List<T>       findAll();
        void          delete(ID id);
        long          count();
        boolean       existsById(ID id);
    }

    // ─── 3. In-memory base implementation ─────────────────────────
    abstract static class InMemoryRepository<T extends Entity<ID>, ID>
            implements Repository<T, ID> {

        protected final Map<ID, T> store = new LinkedHashMap<>();

        @Override public T save(T entity) {
            store.put(entity.getId(), entity);
            return entity;
        }

        @Override public Optional<T> findById(ID id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override public List<T> findAll() {
            return new ArrayList<>(store.values());
        }

        @Override public void delete(ID id) {
            store.remove(id);
        }

        @Override public long count() {
            return store.size();
        }

        @Override public boolean existsById(ID id) {
            return store.containsKey(id);
        }
    }

    // ─── 4. Domain objects ────────────────────────────────────────
    static class User implements Entity<Integer> {
        private final int    id;
        private final String name;
        private final String email;

        public User(int id, String name, String email) {
            this.id    = id;
            this.name  = name;
            this.email = email;
        }

        @Override public Integer getId() { return id; }
        public String getName()          { return name; }
        public String getEmail()         { return email; }

        @Override public String toString() {
            return "User{id=" + id + ", name=" + name + ", email=" + email + "}";
        }
    }

    static class Product implements Entity<String> {
        private final String sku;
        private final String name;
        private final double price;

        public Product(String sku, String name, double price) {
            this.sku   = sku;
            this.name  = name;
            this.price = price;
        }

        @Override public String getId() { return sku; }
        public String getName()         { return name; }
        public double getPrice()        { return price; }

        @Override public String toString() {
            return "Product{sku=" + sku + ", name=" + name + ", price=" + price + "}";
        }
    }

    // ─── 5. Concrete repositories ─────────────────────────────────
    static class UserRepository extends InMemoryRepository<User, Integer> {
        // Add user-specific queries
        public Optional<User> findByEmail(String email) {
            return store.values().stream()
                        .filter(u -> u.getEmail().equalsIgnoreCase(email))
                        .findFirst();
        }
    }

    static class ProductRepository extends InMemoryRepository<Product, String> {
        // Add product-specific queries
        public List<Product> findByMaxPrice(double maxPrice) {
            List<Product> result = new ArrayList<>();
            for (Product p : store.values()) {
                if (p.getPrice() <= maxPrice) result.add(p);
            }
            return result;
        }
    }

    public static void main(String[] args) {

        // ── UserRepository ────────────────────────────────────────
        UserRepository users = new UserRepository();
        users.save(new User(1, "Alice",   "alice@example.com"));
        users.save(new User(2, "Bob",     "bob@example.com"));
        users.save(new User(3, "Charlie", "charlie@example.com"));

        System.out.println("All users: " + users.findAll());
        System.out.println("Count: "     + users.count());                    // 3

        Optional<User> found = users.findById(2);
        found.ifPresent(u -> System.out.println("Found: " + u));

        Optional<User> byEmail = users.findByEmail("alice@example.com");
        byEmail.ifPresent(u -> System.out.println("By email: " + u));

        users.delete(2);
        System.out.println("After delete: " + users.findAll());
        System.out.println("Exists 2: "    + users.existsById(2));           // false

        // ── ProductRepository ─────────────────────────────────────
        System.out.println();
        ProductRepository products = new ProductRepository();
        products.save(new Product("SKU001", "Widget",  9.99));
        products.save(new Product("SKU002", "Gadget",  49.99));
        products.save(new Product("SKU003", "Gizmo",   19.99));
        products.save(new Product("SKU004", "Doohickey", 4.99));

        System.out.println("All products: " + products.findAll());
        System.out.println("Under $15:    " + products.findByMaxPrice(15.00));
        System.out.println("SKU002 exists: " + products.existsById("SKU002")); // true
    }
}
