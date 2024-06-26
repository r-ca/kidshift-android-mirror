package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

// Response for a single task with attached children
public class TaskResponse extends TaskBaseItem {
    private List<String> attachedChildren;

    // コンストラクタ
    // 全プロパティ
    public TaskResponse(String id, String name, String iconEmoji, String bgColor, int reward, List<String> attachedChildren) {
        super(id, name, iconEmoji, bgColor, reward);
        this.attachedChildren = attachedChildren;
    }

    // 必須プロパティ
    public TaskResponse(String id, String name, int reward, List<String> attachedChildren) {
        super(id, name, reward);
        this.attachedChildren = attachedChildren;
    }

    // 空
    public TaskResponse() {
    }

    // baseItemを指定して拡張
    public TaskResponse(TaskBaseItem taskBaseItem, List<String> attachedChildren) {
        super(taskBaseItem.getId(), taskBaseItem.getName(), taskBaseItem.getIconEmoji(), taskBaseItem.getBgColor(), taskBaseItem.getReward());
        this.attachedChildren = attachedChildren;
    }

    // Getters and setters
    public List<String> getAttachedChildren() {
        return attachedChildren;
    }

    public void setAttachedChildren(List<String> attachedChildren) {
        this.attachedChildren = attachedChildren;
    }
}