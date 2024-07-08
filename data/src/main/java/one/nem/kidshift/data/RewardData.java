package one.nem.kidshift.data;

import java.util.concurrent.CompletableFuture;

public interface RewardData {
    /**
     * 現時点の合計報酬額を取得する
     * @return Integer 合計報酬額
     */
    CompletableFuture<Integer> getTotalReward(String childId);
}
