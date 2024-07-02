package one.nem.kidshift.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import one.nem.kidshift.model.tasks.TaskItemModel;

public interface TaskData {

    // 親側

    /**
     * 存在する全てのタスクを取得する
     * @return CompletableFuture<List<TaskItemModel>> タスクリスト
     */
    CompletableFuture<List<TaskItemModel>> getTasks();

    /**
     * アタッチされている全てのタスクを取得する
     * @param childId 子ID
     * @return CompletableFuture<List<TaskItemModel>> タスクリスト
     */
    CompletableFuture<List<TaskItemModel>> getTasks(String childId);

    /**
     * タスクを追加する
     * @param task タスク
     */
    void addTask(TaskItemModel task);

    /**
     * タスクを削除する
     * @param taskId タスクID
     */
    void removeTask(String taskId);

    /**
     * タスクを更新する
     * @param task タスク
     */
    void updateTask(TaskItemModel task);

    // 子側

    /**
     * タスクの詳細を取得する
     * @param taskId タスクID
     * @return TaskItemModel タスク
     */
    CompletableFuture<TaskItemModel> getTask(String taskId);

    /**
     * タスクの完了を記録する
     * @param taskId タスクID
     * @param childId 子ID
     */
    void recordTaskCompletion(String taskId, String childId);
}
