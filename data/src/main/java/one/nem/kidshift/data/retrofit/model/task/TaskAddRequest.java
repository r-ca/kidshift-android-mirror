package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

// Request to add a task, with optional attached children
public class TaskAddRequest extends TaskBaseItem {
    private List<String> attachedChildren;

    // Getters and setters
    public List<String> getAttachedChildren() {
        return attachedChildren;
    }

    public void setAttachedChildren(List<String> attachedChildren) {
        this.attachedChildren = attachedChildren;
    }
}