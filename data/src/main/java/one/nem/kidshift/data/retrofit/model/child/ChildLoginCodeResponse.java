package one.nem.kidshift.data.retrofit.model.child;

public class ChildLoginCodeResponse {
    private int code;

    public ChildLoginCodeResponse(int code) {
        this.code = code;
    }

    public ChildLoginCodeResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
