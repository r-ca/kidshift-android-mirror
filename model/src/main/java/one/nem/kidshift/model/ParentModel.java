package one.nem.kidshift.model;

// TODO: parent, childを共通クラスから継承させる
public class ParentModel extends UserBaseModel {

    private String email;

    public ParentModel(String id, String name, String email) {
        super(id, name);
        this.email = email;
    }

    private ParentModel(UserBaseModel userBaseModel, String email) {
        super(userBaseModel.getId().isEmpty() ? null : userBaseModel.getId(), userBaseModel.getName());
        this.email = email;
    }

    public ParentModel(String name, String email) {
        super(name);
        this.email = email;
    }

    public ParentModel() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
