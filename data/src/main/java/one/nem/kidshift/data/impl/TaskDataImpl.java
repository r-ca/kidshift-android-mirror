package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.converter.TaskModelConverter;
import one.nem.kidshift.data.retrofit.model.task.TaskResponse;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class TaskDataImpl implements TaskData {

    private final KSActions ksActions;
    private final KidShiftApiService kidShiftApiService;
    private final CacheWrapper cacheWrapper;
    private final KSLogger logger;

    @Inject
    public TaskDataImpl(KSActions ksActions, KidShiftApiService kidShiftApiService, CacheWrapper cacheWrapper, KSLoggerFactory loggerFactory) {
        this.ksActions = ksActions;
        this.kidShiftApiService = kidShiftApiService;
        this.cacheWrapper = cacheWrapper;
        this.logger = loggerFactory.create("TaskDataImpl");
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> getTasks(TaskItemModelCallback callback) {
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("タスク取得開始");
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
    public CompletableFuture<Void> updateTask(TaskItemModel task) {
        return CompletableFuture.supplyAsync(() -> {
            Call<TaskResponse> call = kidShiftApiService.updateTask(TaskModelConverter.taskItemModelToTaskAddRequest(task), task.getId());
            try {
                Response<TaskResponse> response = call.execute();
                if (response.isSuccessful()) {
                    logger.info("タスク更新成功(taskId: " + task.getId() + ")");
//                    return response.body();
                    return null;
                } else {
                    logger.error("タスク更新失敗: HTTP Status: " + response.code());
                    throw new RuntimeException("HTTP Status: " + response.code());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<TaskItemModel> getTask(String taskId) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> recordTaskCompletion(String taskId, String childId) {
        return CompletableFuture.supplyAsync(() -> {
            Call<Void> call = kidShiftApiService.completeTask(taskId, childId);
            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {
                    logger.info("タスク完了処理成功(taskId: " + taskId + ", childId: " + childId + ")");
                    return null;
                } else {
                    logger.error("タスク完了処理失敗: HTTP Status: " + response.code());
                    throw new RuntimeException("HTTP Status: " + response.code());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
