package interfaces;

public interface Imanageable {
    // Create
    void create();

    // Read
    void read();

    static void readAll() {} // Will be implemented in each class

    // Update
    void update();

    // Delete
    void delete();
}
