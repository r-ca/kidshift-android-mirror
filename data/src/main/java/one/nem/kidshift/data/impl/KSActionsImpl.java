package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.converter.ParentModelConverter;
import one.nem.kidshift.data.retrofit.model.converter.TaskModelConverter;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.data.room.KidShiftDatabase;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;
import retrofit2.Call;
import retrofit2.Response;

public class KSActionsImpl implements KSActions {

    private UserSettings userSettings;
    private KidShiftApiService kidShiftApiService;
    private KSLogger logger;
    private CacheWrapper cacheWrapper;

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

    private CompletableFuture<TaskSyncResult> doSyncTaskChild() {
        return CompletableFuture.supplyAsync(() -> {
            return null; // TODO 実装
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
