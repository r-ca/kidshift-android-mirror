package one.nem.kidshift.model;

import java.util.Date;

public class HistoryModel {
    private String id;
    private String taskId;
    private String taskName;
    private String childId;
    private Date registeredAt;
    private int reward;
    private boolean isPaid;

    public HistoryModel(String id, String taskId, String taskName, String childId, Date registeredAt, int reward, boolean isPaid) {
        this.id = id;
        this.taskId = taskId;
        this.taskName = taskName;
        this.childId = childId;
        this.registeredAt = registeredAt;
        this.reward = reward;
        this.isPaid = isPaid;
    }

    public HistoryModel(String id, String taskId, String childId, Date registeredAt) { // 他モデルとのマージが必要なので
        this.id = id;
        this.taskId = taskId;
        this.childId = childId;
        this.registeredAt = registeredAt;
    }

    public HistoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
