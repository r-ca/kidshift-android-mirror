package one.nem.kidshift.data.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.inject.Inject;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.data.retrofit.model.converter.ChildModelConverter;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class ChildDataImpl implements ChildData {

    private final KSActions ksActions;
    private final CacheWrapper cacheWrapper;
    private final KSLogger logger;

    @Inject
    public ChildDataImpl(KSActions ksActions, CacheWrapper cacheWrapper, KSLoggerFactory loggerFactory) {
        this.ksActions = ksActions;
        this.cacheWrapper = cacheWrapper;
        this.logger = loggerFactory.create("ChildDataImpl");
    }

    @Override
    public CompletableFuture<ChildModel> getChild(String childId) {
        return null;
    }

    @Override
    public CompletableFuture<List<ChildModel>> getChildList() {
        logger.debug("子供リスト取得開始");

        CompletableFuture<List<ChildModel>> cachedChildListFuture = cacheWrapper.getChildList();

        // TODO
    }

    private CompletableFuture<List<ChildModel>> fetchChildListFromServer(ChildModelCallback callback) {
        return ksActions.syncChildList().thenApply(serverChildList -> {
            if (serverChildList == null || serverChildList.isEmpty()) {
                callback.onUnchanged();
            } else {
                callback.onUpdated(serverChildList);
            }
            return serverChildList;
        }).exceptionally(e -> {
            logger.error("子供リスト取得失敗: " + e.getMessage());
            callback.onFailed(e.getMessage());
            return Collections.emptyList();
        });
    }

    private CompletableFuture<List<ChildModel>> checkForUpdates(List<ChildModel> cachedChildList, ChildModelCallback callback) {
        return ksActions.syncChildList().thenApply(serverChildList -> {
            if (serverChildList == null || serverChildList.isEmpty()) {
                callback.onUnchanged();
                return cachedChildList;
            } else {
                boolean isChanged = isChildListChanged(cachedChildList, serverChildList);
                if (isChanged) {
                    logger.debug("子供リスト取得完了: キャッシュと比較して更新あり");
                    callback.onUpdated(serverChildList);
                    return serverChildList;
                } else {
                    logger.debug("子供リスト取得完了: キャッシュと比較して変更なし");
                    callback.onUnchanged();
                    return cachedChildList;
                }
            }
        });
    }

    private boolean isChildListChanged(List<ChildModel> cachedChildList, List<ChildModel> serverChildList) {
        if (cachedChildList.size() != serverChildList.size()) {
            return true;
        }

        for (ChildModel serverChild : serverChildList) {
            boolean exists = cachedChildList.stream().anyMatch(cachedChild -> serverChild.getId().equals(cachedChild.getId()));
            if (!exists) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateChild(ChildModel child) {

    }

    @Override
    public void addChild(ChildModel child) {

    }

    @Override
    public void removeChild(String childId) {

    }

    @Override
    public CompletableFuture<Integer> issueLoginCode(String childId) {
        return null;
    }
}
