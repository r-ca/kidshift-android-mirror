package one.nem.kidshift.data.retrofit.model.parent.auth;

public class ParentAuthResponse {
    private String accessToken;

    /**
     * コンストラクタ (全プロパティ)
     * @param accessToken アクセストークン
     */
    public ParentAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
