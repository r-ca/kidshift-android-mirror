package one.nem.kidshift.data;

import java.util.concurrent.CompletableFuture;

import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.model.ParentModel;

/**
 * データの同期など, ユーザーからの操作に基づかない処理を行う
 */
public interface KSActions {

    CompletableFuture<TaskListResponse> syncTasks();

    void syncChildList();

    /**
     * 親ユーザー情報同期
     */
    CompletableFuture<ParentModel> syncParent();

}
