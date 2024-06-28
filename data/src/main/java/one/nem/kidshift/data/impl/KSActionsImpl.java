package one.nem.kidshift.data.impl;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.converter.ParentModelConverter;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.utils.KSLogger;
import retrofit2.Call;
import retrofit2.Response;

public class KSActionsImpl implements KSActions {

    private UserSettings userSettings;
    private KidShiftApiService kidShiftApiService;
    private KSLogger logger;

    @Inject
    public KSActionsImpl(UserSettings userSettings, KidShiftApiService kidShiftApiService, KSLogger logger) {
        this.userSettings = userSettings;
        this.kidShiftApiService = kidShiftApiService;
        this.logger = logger;
        logger.setTag("KSActions");
    }

    @Override
    public CompletableFuture<TaskListResponse> syncTasks() {
        return CompletableFuture.supplyAsync(() -> {
            Call<TaskListResponse> call = kidShiftApiService.getTasks();
            try {
                Response<TaskListResponse> response = call.execute();
                if (!response.isSuccessful()) {
                    logger.error("Error fetching tasks: " + response.errorBody().string());
                    throw new RuntimeException("Error fetching tasks: " + response.errorBody().string());
                }
                TaskListResponse responseBody = response.body();
                logger.info("Tasks fetched with status: " + response.code());
                logger.debug("Tasks: " + responseBody.getList());
//                // Save to cache
//                userSettings.getCache().setTasks(responseBody.getList());
//                logger.info("Tasks saved to cache");
                return responseBody;
            } catch (Exception e) {
                logger.error("Error fetching tasks");
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void syncChildList() {

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
