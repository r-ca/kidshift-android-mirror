package one.nem.kidshift.data.room.utils.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.room.entity.ChildCacheEntity;
import one.nem.kidshift.model.ChildModel;

public class ChildCacheConverter {

    /**
     * ChildModelをChildCacheEntityに変換する
     * @param childModel ChildModel
     * @return ChildCacheEntity
     */
    public static ChildCacheEntity childModelToChildCacheEntity(ChildModel childModel) {
        ChildCacheEntity entity = new ChildCacheEntity();
        entity.id = childModel.getId();
        entity.name = childModel.getName();
        return entity;
    }

    /**
     * ChildCacheEntityをChildModelに変換する
     * @param childList ChildModelリスト
     * @return ChildCacheEntityリスト
     */
    public static List<ChildCacheEntity> childModelListToChildCacheEntityList(List<ChildModel> childList) {
        return childList.stream().map(ChildCacheConverter::childModelToChildCacheEntity).collect(Collectors.toList());
    }

    /**
     * ChildCacheEntityをChildModelに変換する
     * @param entity ChildCacheEntity
     * @return ChildModel
     */
    public static ChildModel childCacheEntityToChildModel(ChildCacheEntity entity) {
        return new ChildModel(entity.id, entity.name);
    }

    public static List<ChildModel> childCacheEntityListToChildModelList(List<ChildCacheEntity> result) {
        List<ChildModel> childList = new ArrayList<>();
        for (ChildCacheEntity entity : result) {
            childList.add(childCacheEntityToChildModel(entity));
        }
        return childList;
    }
}
