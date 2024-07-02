package one.nem.kidshift.data.impl;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ParentModelCallback;
import one.nem.kidshift.utils.KSLogger;
import retrofit2.Call;
import retrofit2.Response;

public class ParentDataImpl implements ParentData {

    private KidShiftApiService kidshiftApiService;

    private UserSettings userSettings;

    private KSLogger logger;

    private KSActions ksActions;

    @Inject
    public ParentDataImpl(KidShiftApiService kidshiftApiService, UserSettings userSettings, KSLogger logger, KSActions ksActions) {
        this.kidshiftApiService = kidshiftApiService;
        this.userSettings = userSettings;
        this.logger = logger;
        this.ksActions = ksActions;

        logger.setTag("ParentData");
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
    public void updateParent(ParentModel parent) {

    }

}
