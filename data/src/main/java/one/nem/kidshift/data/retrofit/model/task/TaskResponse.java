package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

// Response for a single task with attached children
public class TaskResponse extends TaskBaseItem {
    private List<String> attachedChildren;

    // Full
    public TaskResponse(String id, String name, String iconEmoji, String bgColor, int reward, List<String> attachedChildren) {
        super(id, name, iconEmoji, bgColor, reward);
        this.attachedChildren = attachedChildren;
    }

    // Required
    public TaskResponse(String id, String name, int reward, List<String> attachedChildren) {
        super(id, name, reward);
        this.attachedChildren = attachedChildren;
    }

    // Empty
    public TaskResponse() {
    }

    // Extend
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