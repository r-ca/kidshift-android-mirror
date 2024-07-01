package one.nem.kidshift.model;

public class UserBaseModel {

    private String id;
    private String name;

    public UserBaseModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserBaseModel(String name) {
        this.name = name;
    }

    public UserBaseModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
