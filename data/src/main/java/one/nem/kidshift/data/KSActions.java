package one.nem.kidshift.data;

/**
 * データの同期など, ユーザーからの操作に基づかない処理を行う
 */
public interface KSActions {

    void syncTasks();

    void syncChildList();

    /**
     * 親ユーザー情報同期
     */
    void syncParent();

}
