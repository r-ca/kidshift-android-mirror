package one.nem.kidshift.data;

import one.nem.kidshift.model.ChildModel;

public interface ChildData {

    /**
     * 子ユーザー情報取得
     * @param childId 子ID
     * @return ChildModel 子ユーザー情報
     */
    ChildModel getChild(String childId);

    /**
     * 子ユーザー情報更新
     * @param child 子ユーザー情報
     */
    void updateChild(ChildModel child);

    /**
     * 子ユーザー追加
     * @param child 子ユーザー情報
     */
    void addChild(ChildModel child);
}
