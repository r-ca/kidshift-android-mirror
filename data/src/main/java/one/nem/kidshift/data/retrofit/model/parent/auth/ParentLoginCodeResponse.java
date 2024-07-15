package one.nem.kidshift.data.retrofit.model.parent.auth;

public class ParentLoginCodeResponse {
    private int code;

    public ParentLoginCodeResponse(int code) {
        this.code = code;
    }

    public ParentLoginCodeResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
