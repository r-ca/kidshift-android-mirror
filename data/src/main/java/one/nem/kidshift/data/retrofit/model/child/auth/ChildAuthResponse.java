package one.nem.kidshift.data.retrofit.model.child.auth;

public class ChildAuthResponse {
    private String accessToken;

    public ChildAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
