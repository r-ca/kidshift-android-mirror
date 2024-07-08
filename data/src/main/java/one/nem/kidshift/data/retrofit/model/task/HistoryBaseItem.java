package one.nem.kidshift.data.retrofit.model.task;

import java.util.Date;

public class HistoryBaseItem {
    private String id;
    private String taskId;
    private String childId;
    private Date registeredAt;

    public HistoryBaseItem(String id, String taskId, String childId, Date registeredAt) {
        this.id = id;
        this.taskId = taskId;
        this.childId = childId;
        this.registeredAt = registeredAt;
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
}
