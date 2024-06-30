package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.security.auth.callback.Callback;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.retrofit.model.converter.TaskModelConverter;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
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
            Thread thread = new Thread(() -> {
                // TODO-rca: ちゃんと比較して呼ぶ
                ksActions.syncTasks().thenAccept(callback::onUpdated);
            });
            thread.start();
            return cacheWrapper.getTaskList().thenApply(taskList -> {
                if (taskList == null || taskList.isEmpty()) {
                    try {
                        thread.join();
                        return cacheWrapper.getTaskList().join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
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
