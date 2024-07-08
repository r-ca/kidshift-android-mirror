package one.nem.kidshift.data.retrofit.model.child.auth;

public class ChildAuthResponse {
    private String accessToken;
    private String childId;

    public ChildAuthResponse() {
    }

    public ChildAuthResponse(String accessToken, String childId) {
        this.accessToken = accessToken;
        this.childId = childId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
