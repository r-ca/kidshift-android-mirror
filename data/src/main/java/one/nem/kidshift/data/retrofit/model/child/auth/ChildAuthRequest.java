package one.nem.kidshift.data.retrofit.model.child.auth;

public class ChildAuthRequest {
    private String loginCode;

    public ChildAuthRequest(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }
}
