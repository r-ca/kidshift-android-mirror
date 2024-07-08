package one.nem.kidshift.data.impl;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.HistoryModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

public class RewardDataImpl implements RewardData {

    private final UserSettings userSettings;
    private final KSActions ksActions;
    private final CacheWrapper cacheWrapper;
    private final KSLogger logger;


    @Inject
    public RewardDataImpl(KSLoggerFactory ksLoggerFactory, CacheWrapper cacheWrapper, UserSettings userSettings, KSActions ksActions) {
        this.userSettings = userSettings;
        this.ksActions = ksActions;
        this.cacheWrapper = cacheWrapper;
        this.logger = ksLoggerFactory.create("RewardDataImpl");
    }

    @Override
    public CompletableFuture<Integer> getTotalReward(String childId) {
        return CompletableFuture.supplyAsync(() -> {
            return ksActions.syncHistory(childId).join().stream().mapToInt(HistoryModel::getReward).sum();
        });
    }
}
