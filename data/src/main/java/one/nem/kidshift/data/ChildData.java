package one.nem.kidshift.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.callback.ChildModelCallback;

public interface ChildData {

    /**
     * 子ユーザー情報取得
     * @param childId 子ID
     * @return ChildModel 子ユーザー情報
     */
    CompletableFuture<ChildModel> getChild(String childId);

    /**
     * 子ユーザー一覧取得
     * @return List<ChildModel> 子ユーザー一覧
     */
    CompletableFuture<List<ChildModel>> getChildList(ChildModelCallback callback);

    /**
     * 子ユーザー一覧をサーバーから直接取得(キャッシュ無視)
     * @return List<ChildModel> 子ユーザー一覧
     */
    CompletableFuture<List<ChildModel>> getChildListDirect();

    /**
     * 子ユーザー情報更新
     * @param child 子ユーザー情報
     */
    CompletableFuture<ChildModel> updateChild(ChildModel child);

    /**
     * 子ユーザー追加
     * @param child 子ユーザー情報
     */
    CompletableFuture<ChildModel> addChild(ChildModel child);

    /**
     * 子ユーザー削除
     * @param childId 子ID
     */
    CompletableFuture<Void> removeChild(String childId);

    /**
     * 子ユーザーログインコード発行
     * @param childId 子ID
     * @return int ログインコード
     */
    CompletableFuture<Integer> issueLoginCode(String childId);
}
