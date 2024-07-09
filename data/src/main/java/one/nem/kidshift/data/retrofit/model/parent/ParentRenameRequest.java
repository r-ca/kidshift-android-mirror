package one.nem.kidshift.data.retrofit.model.parent;

public class ParentRenameRequest {
    private String displayName;

    /**
    * コンストラクタ (全プロパティ)
    * @param displayName 表示名
    */
    public ParentRenameRequest(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
