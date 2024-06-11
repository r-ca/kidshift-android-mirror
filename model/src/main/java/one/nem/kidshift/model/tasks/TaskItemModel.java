package one.nem.kidshift.model.tasks;

import androidx.annotation.NonNull;

import one.nem.kidshift.model.tasks.condition.TaskConditionBaseModel;

public class TaskItemModel {

    String internalId;
    String attachedChildId;
    String displayName;
    TaskConditionBaseModel condition;
    long reward;

    // constructor
    public TaskItemModel(String internalId, String displayName, String attachedChildId, TaskConditionBaseModel condition, long reward) {
        this.internalId = internalId;
        this.attachedChildId = attachedChildId;
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

    public String getAttachedChildId() {
        return attachedChildId;
    }

    public void setAttachedChildId(String attachedChildId) {
        this.attachedChildId = attachedChildId;
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

    @NonNull
    @Override
    public String toString() {
        return "TaskItemModel{" + '\n' +
                "  internalId='" + internalId + '\n' +
                "  attachedChildId='" + attachedChildId + '\n' +
                "  displayName='" + displayName + '\n' +
                "  condition=" + condition.toString() + '\n' +
                "  reward=" + reward +
                '}';
    }
}
