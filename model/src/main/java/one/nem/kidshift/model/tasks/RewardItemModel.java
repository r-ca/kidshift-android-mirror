package one.nem.kidshift.model.tasks;

import one.nem.kidshift.model.ChildModel;

public class RewardItemModel {
    String internalId;
    String displayName;
    Integer rewardAmount;
    ChildModel assignedChild;

    public RewardItemModel(String internalId, String displayName, Integer rewardAmount, ChildModel assignedChild) {
        this.internalId = internalId;
        this.displayName = displayName;
        this.rewardAmount = rewardAmount;
        this.assignedChild = assignedChild;
    }

    public RewardItemModel(String internalId, String displayName, Integer rewardAmount) {
        this.internalId = internalId;
        this.displayName = displayName;
        this.rewardAmount = rewardAmount;
    }

    // TODO: ChildIdからChildModelを取得できる方法を実装する

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

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public ChildModel getAssignedChild() {
        return assignedChild;
    }

    public void setAssignedChild(ChildModel assignedChild) {
        this.assignedChild = assignedChild;
    }
}
