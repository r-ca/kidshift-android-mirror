package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.data.retrofit.model.converter.ChildModelConverter;
import one.nem.kidshift.data.retrofit.model.converter.ParentModelConverter;
import one.nem.kidshift.data.retrofit.model.converter.TaskModelConverter;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;
import retrofit2.Call;
import retrofit2.Response;

public class KSActionsImpl implements KSActions {

    private final UserSettings userSettings;
    private final KidShiftApiService kidShiftApiService;
    private final KSLogger logger;
    private final CacheWrapper cacheWrapper;

    @Inject
    public KSActionsImpl(UserSettings userSettings, KidShiftApiService kidShiftApiService, KSLogger logger, CacheWrapper cacheWrapper) {
        this.userSettings = userSettings;
        this.kidShiftApiService = kidShiftApiService;
        this.logger = logger;
        this.cacheWrapper = cacheWrapper;
        logger.setTag("KSActions");
    }

    @Override
    public CompletableFuture<List<TaskItemModel>> syncTasks() {
        return doSyncTaskChild().thenApply(result -> result.taskList);
    }

    @Override
    public CompletableFuture<List<ChildModel>> syncChildList() {
        return doSyncTaskChild().thenApply(result -> result.childList);
    }

    private static class TaskSyncResult {
        public List<TaskItemModel> taskList;
        public List<ChildModel> childList;
    }

    /**
     * タスクと子供リストを同期する(ラッパー)
     * @return CompletableFuture<TaskSyncResult> タスクと子供リスト
     */
    private CompletableFuture<TaskSyncResult> doSyncTaskChild() {
        return fetchChildListAsync().thenCombine(fetchTaskListAsync(), (childListResponse, taskListResponse) -> {
            Thread cacheThread = new Thread(() -> {
                logger.debug("キャッシュ更新スレッド開始(スレッドID: " + Thread.currentThread().getId() + ")");
                cacheWrapper.updateCache(ChildModelConverter.childListResponseToChildModelList(childListResponse),
                        TaskModelConverter.taskListResponseToTaskItemModelList(taskListResponse)).join();
                logger.info("キャッシュ更新完了");
            });
            cacheThread.start();
            try {
                // Workaround:  DBオペレーションを待たずに結果を先に返したいが、
                //              (現状(呼び出し元が)戻り値をそのままCallbackに渡しているために)
                //              キャッシュが空の場合のキャッシュ再取得のタイミングが調整できなくなる.
                cacheThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new TaskSyncResult() {
                {
                    taskList = TaskModelConverter.taskListResponseToTaskItemModelList(taskListResponse);
                    childList = ChildModelConverter.childListResponseToChildModelList(childListResponse);
                }
            };
        });
    }

    /**
     * バックエンドからタスクリストをFetchする
     * @return CompletableFuture<TaskListResponse> バックエンドから取得したタスクリスト
     */
    private CompletableFuture<TaskListResponse> fetchTaskListAsync() {
        return CompletableFuture.supplyAsync(() -> {
            Call<TaskListResponse> call = kidShiftApiService.getTasks();
            try {
                Response<TaskListResponse> response = call.execute();
                if (!response.isSuccessful()) {
                    logger.error("Error fetching task list: " + response.errorBody().string());
                    throw new RuntimeException("Error fetching task list: " + response.errorBody().string());
                }
                TaskListResponse responseBody = response.body();
                return responseBody;
            } catch (Exception e) {
                logger.error("Error fetching task list");
                throw new RuntimeException(e);

            }
        });
    }

    /**
     * バックエンドから子供リストをFetchする
     * @return CompletableFuture<ChildListResponse> バックエンドから取得した子供リスト
     */
    private CompletableFuture<ChildListResponse> fetchChildListAsync() {
        return CompletableFuture.supplyAsync(() -> {
            Call<ChildListResponse> call = kidShiftApiService.getChildList();
            try {
                Response<ChildListResponse> response = call.execute();
                if (!response.isSuccessful()) {
                    logger.error("Error fetching child list: " + response.errorBody().string());
                    throw new RuntimeException("Error fetching child list: " + response.errorBody().string());
                }
                ChildListResponse responseBody = response.body();
                return responseBody;
            } catch (Exception e) {
                logger.error("Error fetching child list");
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<ParentModel> syncParent() {
        logger.info("syncParent called and started");
        return CompletableFuture.supplyAsync(() -> {
            logger.info("fetching...");
            Call<ParentInfoResponse> call = kidShiftApiService.getParentInfo();
            try {
                Response<ParentInfoResponse> response = call.execute();
                if (!response.isSuccessful()) {
                    logger.error("Error fetching parent info: " + response.errorBody().string());
                    throw new RuntimeException("Error fetching parent info: " + response.errorBody().string());
                }
                ParentInfoResponse responseBody = response.body();
                ParentModel parent = ParentModelConverter.parentInfoResponseToParentModel(responseBody);
                // Save to cache
                userSettings.getCache().setParent(parent);
                logger.info("Parent saved to cache");
                return parent;
            } catch (Exception e) {
                logger.error("Error fetching parent info");
                throw new RuntimeException(e);
            }
        });
    }
}
