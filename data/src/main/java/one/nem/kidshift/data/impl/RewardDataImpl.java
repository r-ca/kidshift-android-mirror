package one.nem.kidshift.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.HistoryModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;

public class RewardDataImpl implements RewardData {

    private final UserSettings userSettings;
    private final KSActions ksActions;
    private final CacheWrapper cacheWrapper;
    private final KSLogger logger;
    private final ChildData childData;
    private final KidShiftApiService kidShiftApiService;

    @Inject
    public RewardDataImpl(KSLoggerFactory ksLoggerFactory, CacheWrapper cacheWrapper, UserSettings userSettings, KSActions ksActions, ChildData childData, KidShiftApiService kidShiftApiService) {
        this.userSettings = userSettings;
        this.ksActions = ksActions;
        this.cacheWrapper = cacheWrapper;
        this.childData = childData;
        this.kidShiftApiService = kidShiftApiService;
        this.logger = ksLoggerFactory.create("RewardDataImpl");
    }

    @Override
    public CompletableFuture<Integer> getTotalReward(String childId) { // TODO: localCacheを使う
        return CompletableFuture.supplyAsync(() -> ksActions.syncHistory(childId).join().stream().mapToInt(HistoryModel::getReward).sum());
    }

    @Override
    public CompletableFuture<List<HistoryModel>> getRewardHistoryList() { // TODO: localCacheを使う
        List<HistoryModel> historyModels = new ArrayList<>();
        return childData.getChildListDirect().thenAccept(childModels -> {
            childModels.forEach(childModel -> {
                historyModels.addAll(ksActions.syncHistory(childModel.getId()).join());
            });
        }).thenApply(v -> historyModels);
    }

    @Override
    public CompletableFuture<List<HistoryModel>> getRewardHistoryList(String childId) { // TODO: localCacheを使う
        return CompletableFuture.supplyAsync(() -> ksActions.syncHistory(childId).join());
    }

    @Override
    public CompletableFuture<Void> payReward(String historyId) {
        return CompletableFuture.runAsync(() -> {
            Call<Void> call = kidShiftApiService.payHistory(historyId, true);
            try {
                call.execute();
            } catch (Exception e) {
                logger.error("Failed to pay reward : " + e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Void> payReward(List<String> historyIds) {
        return CompletableFuture.runAsync(() -> {
            historyIds.forEach(historyId -> { // TODO: API側でリストに対応する
                Call<Void> call = kidShiftApiService.payHistory(historyId, true);
                try {
                    call.execute();
                } catch (Exception e) {
                    logger.error("Failed to pay reward : " + e.getMessage());
                }
            });
        });
    }
}
