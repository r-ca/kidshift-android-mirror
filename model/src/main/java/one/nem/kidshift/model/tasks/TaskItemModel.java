package one.nem.kidshift.model.tasks;

public class TaskItemModel {

    String internalId;
    String displayName;
    TaskConditionBaseModel condition;
    long reward;

    // constructor
    public TaskItemModel(String internalId, String displayName, TaskConditionBaseModel condition, long reward) {
        this.internalId = internalId;
        this.displayName = displayName;
        this.condition = condition;
        this.reward = reward;
    }

    // getter setter

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

    public TaskConditionBaseModel getCondition() {
        return condition;
    }

    public void setCondition(TaskConditionBaseModel condition) {
        this.condition = condition;
    }

    public long getReward() {
        return reward;
    }

    public void setReward(long reward) {
        this.reward = reward;
    }
}
