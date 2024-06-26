package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

// Response for a list of tasks
public class TaskListResponse {
    private List<TaskBaseItem> list;

    // Getters and setters
    public List<TaskBaseItem> getList() {
        return list;
    }

    public void setList(List<TaskBaseItem> list) {
        this.list = list;
    }
}
