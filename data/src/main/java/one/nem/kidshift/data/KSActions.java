package one.nem.kidshift.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.tasks.TaskItemModel;

/**
 * データの同期などを提供する内部ユーティリティ
 */
public interface KSActions {

    /**
     * タスク, 子供情報を同期してTaskItemModelを取得
     */
    CompletableFuture<List<TaskItemModel>> syncTasks();

    CompletableFuture<List<ChildModel>> syncChildList();

    /**
     * 親ユーザー情報同期
     */
    CompletableFuture<ParentModel> syncParent();

}
