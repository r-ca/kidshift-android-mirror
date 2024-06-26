package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

// Request to add a task, with optional attached children
public class TaskAddRequest extends TaskBaseItem {
    private List<String> attachedChildren;

    // Full
    public TaskAddRequest(String name, String iconEmoji, String bgColor, int reward, List<String> attachedChildren) {
        super(name, iconEmoji, bgColor, reward);
        this.attachedChildren = attachedChildren;
    }

    // Required
    public TaskAddRequest(String name, int reward, List<String> attachedChildren) {
        super(name, reward);
        this.attachedChildren = attachedChildren;
    }

    // Empty
    public TaskAddRequest() {
    }

    // Extend
    public TaskAddRequest(TaskBaseItem taskBaseItem, List<String> attachedChildren) {
        super(taskBaseItem.getName(), taskBaseItem.getIconEmoji(), taskBaseItem.getBgColor(), taskBaseItem.getReward());
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