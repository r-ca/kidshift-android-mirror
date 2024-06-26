package one.nem.kidshift.data.retrofit.model.child;

// Request to add a child
public class ChildAddRequest {
    private String name;

    // Constructor
    public ChildAddRequest(String name) {
        this.name = name;
    }

    public ChildAddRequest() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}