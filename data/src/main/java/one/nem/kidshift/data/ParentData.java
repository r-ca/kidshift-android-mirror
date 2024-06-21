package one.nem.kidshift.data;

import one.nem.kidshift.model.ParentModel;

public interface ParentData {

    /**
     * 親ユーザー情報取得
     * @param parentId 親ID
     * @return ParentModel 親ユーザー情報
     */
    ParentModel getParent(String parentId);

    /**
     * 親ユーザー情報更新
     * @param parent 親ユーザー情報
     */
    void updateParent(ParentModel parent);

}
