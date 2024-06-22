package one.nem.kidshift.data;

/**
 * データの同期など, UIとのやりとりがないバックエンド処理
 */
public interface KSActions {

    void syncTasks();

    void syncChildList();

    /**
     * 親ユーザー情報同期
     */
    void syncParent();

}
