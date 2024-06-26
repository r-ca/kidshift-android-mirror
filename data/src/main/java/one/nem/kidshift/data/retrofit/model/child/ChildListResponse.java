package one.nem.kidshift.data.retrofit.model.child;

import java.util.List;

// Response for a list of children
public class ChildListResponse {
    private List<ChildResponse> list;

    // Constructor
    public ChildListResponse(List<ChildResponse> list) {
        this.list = list;
    }

    public ChildListResponse() {
    }

    // Getters and setters
    public List<ChildResponse> getList() {
        return list;
    }

    public void setList(List<ChildResponse> list) {
        this.list = list;
    }
}
