package one.nem.kidshift.data.retrofit.model.child;

import java.util.List;

// Response for a list of children
public class ChildListResponse {
    private List<ChildResponse> list;

    /**
     * コンストラクタ (全プロパティ)
     * @param list 子供リスト
     */
    public ChildListResponse(List<ChildResponse> list) {
        this.list = list;
    }

    /**
     * コンストラクタ (空)
     */
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
