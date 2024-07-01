package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

public class TaskListResponse {
    private List<TaskResponse> list;

    // コンストラクタ
    // 全プロパティ
    /**
     * コンストラクタ (全プロパティ)
     * @param list タスクリスト
     */
    public TaskListResponse(List<TaskResponse> list) {
        this.list = list;
    }

    // 空
    /**
     * コンストラクタ (空)
     */
    public TaskListResponse() {
    }

    // Getters and setters

    public List<TaskResponse> getList() {
        return list;
    }

    public void setList(List<TaskResponse> list) {
        this.list = list;
    }
}
