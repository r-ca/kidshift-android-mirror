package one.nem.kidshift.data.retrofit.model.child;

// Base class for children
public class ChildBaseItem {
    private String id;
    private String name;

    // Constructor
    public ChildBaseItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChildBaseItem() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
