package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

public class TaskListResponse {
    private List<TaskBaseItem> list;

    // コンストラクタ
    // 全プロパティ
    public TaskListResponse(List<TaskBaseItem> list) {
        this.list = list;
    }

    // 空
    public TaskListResponse() {
    }

    // Getters and setters
    public List<TaskBaseItem> getList() {
        return list;
    }

    public void setList(List<TaskBaseItem> list) {
        this.list = list;
    }
}
