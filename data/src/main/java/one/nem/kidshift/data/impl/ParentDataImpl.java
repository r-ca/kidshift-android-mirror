package one.nem.kidshift.data.impl;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.parent.ParentRenameRequest;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ParentModelCallback;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;

public class ParentDataImpl implements ParentData {

    private final UserSettings userSettings;
    private final KidShiftApiService kidShiftApiService;

    private final KSLogger logger;

    private final KSActions ksActions;

    @Inject
    public ParentDataImpl(KidShiftApiService kidShiftApiService, UserSettings userSettings, KSLoggerFactory ksLoggerFactory, KSActions ksActions) {
        this.kidShiftApiService = kidShiftApiService;
        this.userSettings = userSettings;
        this.logger = ksLoggerFactory.create("ParentDataImpl");
        this.ksActions = ksActions;
    }

    // 一旦キャッシュを返して, その後非同期でAPIから取得→更新があればコールバックで通知
    @Override
    public CompletableFuture<ParentModel> getParent(ParentModelCallback callback) {
        // Start thread to fetch parent info
        new Thread(() -> {
            logger.info("Fetching parent info...");
            ParentModel refreshedParent = ksActions.syncParent().join();
            if (refreshedParent == null) {
                callback.onFailed("Failed to fetch parent info");
            } else {
                // Workaround, TODO: Compare with existing parent
                callback.onUpdated(refreshedParent);
            }
        }).start();
        return CompletableFuture.supplyAsync(() -> userSettings.getCache().getParent());
    }

    @Override
    public CompletableFuture<ParentModel> getParentDirect() {
        return ksActions.syncParent();
    }

    @Override
    public CompletableFuture<ParentModel> getParentCache() {
        return CompletableFuture.supplyAsync(() -> userSettings.getCache().getParent());
    }

    @Override
    public CompletableFuture<Void> updateParent(ParentModel parent) {
        Call<ParentInfoResponse> call = kidShiftApiService.renameParent(new ParentRenameRequest(parent.getName()));
        try {
            ParentInfoResponse response = call.execute().body();
            if (response == null) {
                return CompletableFuture.completedFuture(null);
            }
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(null);
        }
    }

}
