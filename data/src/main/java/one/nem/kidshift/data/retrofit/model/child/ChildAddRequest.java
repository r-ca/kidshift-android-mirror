package one.nem.kidshift.data.retrofit.model.child;

// Request to add a child
public class ChildAddRequest {
    private String name;

    /**
     * コンストラクタ (全プロパティ)
     * @param name 子供の名前
     */
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