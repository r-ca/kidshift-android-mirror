package one.nem.kidshift.data;

import java.util.List;

import one.nem.kidshift.model.tasks.TaskItemModel;

public interface TaskData {
    /**
     * 存在する全てのタスクを取得する
     * @return List<TaskItemModel> タスクリスト
     */
    List<TaskItemModel> getTasks();
}
