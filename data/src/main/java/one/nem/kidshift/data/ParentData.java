package one.nem.kidshift.data;

import java.util.concurrent.CompletableFuture;

import one.nem.kidshift.model.ParentModel;

public interface ParentData {


    /**
     * 親ユーザー情報取得
     * @return 親ユーザー情報
     */
    CompletableFuture<ParentModel> getParent();

    /**
     * 親ユーザー情報更新
     * @param parent 親ユーザー情報
     */
    void updateParent(ParentModel parent);

}
