package one.nem.kidshift.model;

// TODO: parent, childを共通クラスから継承させる
public class ParentModel {
    String internalId;
    String displayName;
    String homeGroupId;
    String email;

    public ParentModel(String internalId, String displayName, String homeGroupId, String email) {
        this.internalId = internalId;
        this.displayName = displayName;
        this.homeGroupId = homeGroupId;
        this.email = email;
    }

    public ParentModel(String internalId, String displayName, String homeGroupId) {
        this.internalId = internalId;
        this.displayName = displayName;
        this.homeGroupId = homeGroupId;
    }

    public ParentModel(String internalId, String displayName) {
        this.internalId = internalId;
        this.displayName = displayName;
    }

    // Getter

    public String getInternalId() {
        return internalId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHomeGroupId() {
        return homeGroupId;
    }

    public String getEmail() {
        return email;
    }

    // Setter

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setHomeGroupId(String homeGroupId) {
        this.homeGroupId = homeGroupId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
