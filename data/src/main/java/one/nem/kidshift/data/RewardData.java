package one.nem.kidshift.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import one.nem.kidshift.model.HistoryModel;

public interface RewardData {
    /**
     * 現時点の合計報酬額を取得する
     * @return Integer 合計報酬額
     */
    CompletableFuture<Integer> getTotalReward(String childId);

    CompletableFuture<List<HistoryModel>> getRewardHistoryList();

    CompletableFuture<List<HistoryModel>> getRewardHistoryList(String childId);


}
