package one.nem.kidshift.data;

import java.util.concurrent.CompletableFuture;

import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ParentModelCallback;

public interface ParentData {


    /**
     * 親ユーザー情報取得
     * @return 親ユーザー情報
     */
    CompletableFuture<ParentModel> getParent(ParentModelCallback callback);

    /**
     * 親ユーザー情報取得
     * @return 親ユーザー情報
     */
    CompletableFuture<ParentModel> getParentDirect();

    /**
     * 親ユーザー情報取得
     * @return 親ユーザー情報
     */
    CompletableFuture<ParentModel> getParentCache();

    /**
     * 親ユーザー情報更新
     * @param parent 親ユーザー情報
     */
    CompletableFuture<Void> updateParent(ParentModel parent);

    /**
     * 親ユーザーログインコード発行
     * @return ログインコード
     */
    CompletableFuture<Integer> issueLoginCode();

}
