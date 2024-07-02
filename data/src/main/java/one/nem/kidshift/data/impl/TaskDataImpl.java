package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;

public class TaskDataImpl implements TaskData {

    private KSActions ksActions;
    private CacheWrapper cacheWrapper;
    private KSLogger logger;

    @Inject
    public TaskDataImpl(KSActions ksActions, CacheWrapper cacheWrapper, KSLogger logger) {
        this.ksActions = ksActions;
        this.cacheWrapper = cacheWrapper;
        this.logger = logger.setTag("TaskDataImpl");
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> getTasks(TaskItemModelCallback callback) {
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("タスク取得開始");
            // 引数付きThreadを生成
            AtomicReference<List<TaskItemModel>> taskListTmp = new AtomicReference<>();
            Thread thread = new Thread(() -> {
                ksActions.syncTasks().thenAccept(taskList -> {
                    if (taskListTmp.get() == null || taskListTmp.get().isEmpty()) {
                        logger.debug("タスク取得完了: キャッシュよりはやく取得完了 or キャッシュ無し");
                        if (taskList == null || taskList.isEmpty()) {
                            callback.onUnchanged();
                        } else {
                            callback.onUpdated(taskList);
                        }
                    } else {
                        // キャッシュと比較して変更の有無を確認
                        boolean isChanged =
                            taskList.size() != taskListTmp.get().size() ||
                            taskList.stream().anyMatch(task -> taskListTmp.get().stream().noneMatch(taskTmp -> task.getId().equals(taskTmp.getId())));
                        if (isChanged) {
                            logger.debug("タスク取得完了: キャッシュと比較して変更あり");
                            callback.onUpdated(taskList);
                        } else {
                            logger.debug("タスク取得完了: キャッシュと比較して変更なし");
                            callback.onUnchanged();
                        }
                    }
                }).exceptionally(e -> {
                    logger.error("タスク取得失敗: " + e.getMessage());
                    callback.onFailed(e.getMessage());
                    return null;
                });
            });
            thread.start();
            return cacheWrapper.getTaskList().thenApply(taskList -> {
                if (taskList == null || taskList.isEmpty()) {
                    try {
                        logger.debug("キャッシュ無: タスク取得スレッド待機");
                        thread.join();
                        return cacheWrapper.getTaskList().join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    logger.debug("キャッシュ有 (タスク数: " + taskList.size() + ")");
                    taskListTmp.set(taskList);
                    return taskList;
                }
            }).join();
        });
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> getTasks(String childId, TaskItemModelCallback callback) {
        return null;
    }

    @Override
    public void addTask(TaskItemModel task) {

    }

    @Override
    public void removeTask(String taskId) {

    }

    @Override
    public void updateTask(TaskItemModel task) {

    }

    @Override
    public CompletableFuture<TaskItemModel> getTask(String taskId) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void recordTaskCompletion(String taskId, String childId) {

    }
}
