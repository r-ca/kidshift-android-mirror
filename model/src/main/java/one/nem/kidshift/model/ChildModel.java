package one.nem.kidshift.model;

// TODO: parent, childを共通クラスから継承させる
public class ChildModel {
    String internalId;
    String displayName;
    String homeGroupId;

    public ChildModel(String internalId, String displayName, String homeGroupId) {
        this.internalId = internalId;
        this.displayName = displayName;
        this.homeGroupId = homeGroupId;
    }

    public ChildModel(String internalId, String displayName) {
        this.internalId = internalId;
        this.displayName = displayName;
    }

    public ChildModel(String internalId) {
        this.internalId = internalId;
    }

    public ChildModel() {
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHomeGroupId() {
        return homeGroupId;
    }

    public void setHomeGroupId(String homeGroupId) {
        this.homeGroupId = homeGroupId;
    }
}
