package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.child.ChildLoginCodeResponse;
import one.nem.kidshift.data.retrofit.model.child.ChildResponse;
import one.nem.kidshift.data.retrofit.model.converter.ChildModelConverter;
import one.nem.kidshift.data.room.utils.CacheWrapper;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class ChildDataImpl implements ChildData {
    private final KidShiftApiService kidShiftApiService;
    private final KSActions ksActions;
    private final CacheWrapper cacheWrapper;
    private final KSLogger logger;

    @Inject
    public ChildDataImpl(KidShiftApiService kidShiftApiService, KSActions ksActions, CacheWrapper cacheWrapper, KSLoggerFactory loggerFactory) {
        this.kidShiftApiService = kidShiftApiService;
        this.ksActions = ksActions;
        this.cacheWrapper = cacheWrapper;
        this.logger = loggerFactory.create("ChildDataImpl");
    }

    @Override
    public CompletableFuture<ChildModel> getChild(String childId) {
        return CompletableFuture.supplyAsync(() -> {
            Call<ChildResponse> call = kidShiftApiService.getChild(childId);
            try {
                Response<ChildResponse> response = call.execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    logger.info("子供取得成功(childId: " + response.body().getId() + ")");
                    return ChildModelConverter.childResponseToChildModel(response.body());
                } else {
                    throw new RuntimeException("HTTP Status: " + response.code());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<ChildModel>> getChildList(ChildModelCallback callback) { // TODO: リファクタリング
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("子供リスト取得開始");
            AtomicReference<List<ChildModel>> childListTmp = new AtomicReference<>();
            Thread thread = new Thread(() -> ksActions.syncChildList().thenAccept(childList -> {
                if (childListTmp.get() == null || childListTmp.get().isEmpty()) {
                    logger.debug("子供リスト取得完了: キャッシュよりはやく取得完了 or キャッシュ無し");
                    if (childList == null || childList.isEmpty()) {
                        callback.onUnchanged();
                    } else {
                        callback.onUpdated(childList);
                    }
                } else {
                    boolean isChanged =
                            childList.size() != childListTmp.get().size() ||
                                    childList.stream().anyMatch(child -> childListTmp.get().stream().noneMatch(childTmp -> child.getId().equals(childTmp.getId())));
                    if (isChanged) {
                        logger.debug("子供リスト取得完了: キャッシュと比較して変更あり");
                        callback.onUpdated(childList);
                    } else {
                        logger.debug("子供リスト取得完了: キャッシュと比較して変更なし");
                        callback.onUnchanged();
                    }
                }
            }).exceptionally(e -> {
                logger.error("子供リスト取得失敗: " + e.getMessage());
                callback.onFailed(e.getMessage());
                return null;
            }));
            thread.start();
            return cacheWrapper.getChildList().thenApply(childList -> {
                if (childList == null || childList.isEmpty()) {
                    try {
                        logger.debug("キャッシュ無: 子供リスト取得スレッド待機");
                        thread.join();
                        return cacheWrapper.getChildList().join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    logger.debug("キャッシュ有 (子供数: " + childList.size() + ")");
                    childListTmp.set(childList);
                    return childList;
                }
            }).join();
        });
    }

    @Override
    public CompletableFuture<List<ChildModel>> getChildListDirect() {
        return ksActions.syncChildList();
    }

    @Override
    public CompletableFuture<ChildModel> updateChild(ChildModel child) {
        return CompletableFuture.supplyAsync(() -> {
            Call<ChildResponse> call = kidShiftApiService.updateChild(ChildModelConverter.childModelToChildAddRequest(child), child.getId());
            try {
                Response<ChildResponse> response = call.execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    logger.info("子供更新成功(childId: " + response.body().getId() + ")");
                    return ChildModelConverter.childResponseToChildModel(response.body());
                } else {
                    throw new RuntimeException("HTTP Status: " + response.code());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<ChildModel> addChild(ChildModel child) {
        return CompletableFuture.supplyAsync(() -> {
            Call<ChildResponse> call = kidShiftApiService.addChild(ChildModelConverter.childModelToChildAddRequest(child));
            try {
                Response<ChildResponse> response = call.execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    logger.info("子供追加成功(childId: " + response.body().getId() + ")");
                    return ChildModelConverter.childResponseToChildModel(response.body());
                } else {
                    throw new RuntimeException("HTTP Status: " + response.code());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> removeChild(String childId) {
        return CompletableFuture.supplyAsync(() -> {
            Call<Void> call = kidShiftApiService.removeChild(childId);
            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful()) {
                    logger.info("子供削除成功(childId: " + childId + ")");
                    return null;
                } else {
                    throw new RuntimeException("HTTP Status: " + response.code());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Integer> issueLoginCode(String childId) {
        return CompletableFuture.supplyAsync(() -> {
            Call<ChildLoginCodeResponse> call = kidShiftApiService.issueLoginCode(childId);
            try {
                Response<ChildLoginCodeResponse> response = call.execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    return response.body().getCode();
                } else {
                    throw new RuntimeException("HTTP Status: " + response.code());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
