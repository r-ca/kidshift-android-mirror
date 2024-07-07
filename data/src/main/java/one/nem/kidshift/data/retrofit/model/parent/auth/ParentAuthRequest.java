package one.nem.kidshift.data.retrofit.model.parent.auth;

public class ParentAuthRequest {
    private String email;
    private String password;

    /**
     * コンストラクタ (全プロパティ)
     * @param email メールアドレス
     * @param password パスワード
     */
    public ParentAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
