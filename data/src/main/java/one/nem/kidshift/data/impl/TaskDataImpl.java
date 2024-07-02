package one.nem.kidshift.data.impl;

import java.util.Collections;
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

    private final KSActions ksActions;
    private final CacheWrapper cacheWrapper;
    private final KSLogger logger;

    @Inject
    public TaskDataImpl(KSActions ksActions, CacheWrapper cacheWrapper, KSLogger logger) {
        this.ksActions = ksActions;
        this.cacheWrapper = cacheWrapper;
        this.logger = logger.setTag("TaskDataImpl");
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> getTasks(TaskItemModelCallback callback) {
        logger.debug("タスク取得開始");

        CompletableFuture<List<TaskItemModel>> cachedTasksFuture = cacheWrapper.getTaskList();

        return cachedTasksFuture.thenCompose(cachedTasks -> {
            if (cachedTasks == null || cachedTasks.isEmpty()) {
                logger.debug("キャッシュ無: サーバーからタスクを取得");
                return fetchTasksFromServer(callback);
            } else {
                logger.debug("キャッシュ有 (タスク数: " + cachedTasks.size() + ")");
                return checkForUpdates(cachedTasks, callback);
            }
        });
    }

    private CompletableFuture<List<TaskItemModel>> fetchTasksFromServer(TaskItemModelCallback callback) {
        return ksActions.syncTasks().thenApply(serverTasks -> {
            if (serverTasks == null || serverTasks.isEmpty()) {
                callback.onUnchanged();
            } else {
                callback.onUpdated(serverTasks);
            }
            return serverTasks;
        }).exceptionally(e -> {
            logger.error("タスク取得失敗: " + e.getMessage());
            callback.onFailed(e.getMessage());
            return Collections.emptyList();
        });
    }

    private CompletableFuture<List<TaskItemModel>> checkForUpdates(List<TaskItemModel> cachedTasks, TaskItemModelCallback callback) {
        return ksActions.syncTasks().thenApply(serverTasks -> {
            if (serverTasks == null || serverTasks.isEmpty()) {
                callback.onUnchanged();
                return cachedTasks;
            } else {
                boolean isChanged = isTaskListChanged(cachedTasks, serverTasks);
                if (isChanged) {
                    logger.debug("タスク取得完了: キャッシュと比較して変更あり");
                    callback.onUpdated(serverTasks);
                    return serverTasks;
                } else {
                    logger.debug("タスク取得完了: キャッシュと比較して変更なし");
                    callback.onUnchanged();
                    return cachedTasks;
                }
            }
        }).exceptionally(e -> {
            logger.error("タスク取得失敗: " + e.getMessage());
            callback.onFailed(e.getMessage());
            return cachedTasks;
        });
    }

    private boolean isTaskListChanged(List<TaskItemModel> cachedTasks, List<TaskItemModel> serverTasks) {
        if (cachedTasks.size() != serverTasks.size()) {
            return true;
        }

        for (TaskItemModel serverTask : serverTasks) {
            boolean exists = cachedTasks.stream().anyMatch(cachedTask -> serverTask.getId().equals(cachedTask.getId()));
            if (!exists) {
                return true;
            }
        }
        return false;
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
