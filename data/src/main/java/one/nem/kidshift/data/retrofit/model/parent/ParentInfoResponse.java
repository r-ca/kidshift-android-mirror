package one.nem.kidshift.data.retrofit.model.parent;

public class ParentInfoResponse {

    private String id;
    private String email;
    private String display_name;

    /**
     * コンストラクタ (全プロパティ)
     * @param id 親ID
     * @param email メールアドレス
     * @param display_name 表示名
     */
    public ParentInfoResponse(String id, String email, String display_name) {
        this.id = id;
        this.email = email;
        this.display_name = display_name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
