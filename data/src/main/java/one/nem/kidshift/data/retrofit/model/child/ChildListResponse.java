package one.nem.kidshift.data.retrofit.model.child;

// Response for a list of children
public class ChildListResponse {
    private List<ChildResponse> list;

    // Getters and setters
    public List<ChildResponse> getList() {
        return list;
    }

    public void setList(List<ChildResponse> list) {
        this.list = list;
    }
}
