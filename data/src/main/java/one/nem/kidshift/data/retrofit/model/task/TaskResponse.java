package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

// Response for a single task with attached children
public class TaskResponse extends TaskBaseItem {
    private List<String> attachedChildren;

    // Getters and setters
    public List<String> getAttachedChildren() {
        return attachedChildren;
    }

    public void setAttachedChildren(List<String> attachedChildren) {
        this.attachedChildren = attachedChildren;
    }
}