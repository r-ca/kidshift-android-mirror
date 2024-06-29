package one.nem.kidshift.data.room.utils.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.room.entity.ChildCacheEntity;
import one.nem.kidshift.model.ChildModel;

public class ChildCacheConverter {

    public static ChildCacheEntity childModelToChildCacheEntity(ChildModel childModel) {
        ChildCacheEntity entity = new ChildCacheEntity();
        entity.id = childModel.getId();
        entity.name = childModel.getName();
        return entity;
    }

    public static List<ChildCacheEntity> childModelListToChildCacheEntityList(List<ChildModel> childList) {
        return childList.stream().map(ChildCacheConverter::childModelToChildCacheEntity).collect(Collectors.toList());
    }

    public static ChildModel childCacheEntityToChildModel(ChildCacheEntity entity) {
        return new ChildModel(entity.id, entity.name);
    }
}
