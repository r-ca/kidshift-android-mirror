package one.nem.kidshift.data.impl;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
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
    public void syncTasks() {

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
                ParentModel parent = new ParentModel();
                // TODO: 詰め替えをどこかにまとめる, 他のプロパティも処理する
                parent.setInternalId(responseBody.getId());
                parent.setEmail(responseBody.getEmail());
                parent.setDisplayName(responseBody.getEmail()); // Workaround
                logger.info("Parent fetched with status: " + response.code());
                logger.debug("Parent: " + parent);
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
