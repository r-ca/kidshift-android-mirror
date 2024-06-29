package one.nem.kidshift.data.room.utils.converter;

import one.nem.kidshift.data.room.entity.ChildCacheEntity;
import one.nem.kidshift.model.ChildModel;

public class ChildCacheConverter {

    public static ChildCacheEntity childModelToChildCacheEntity(ChildModel childModel) {
        ChildCacheEntity entity = new ChildCacheEntity();
        entity.id = childModel.getId();
        entity.name = childModel.getName();
        return entity;
    }

    public static ChildModel childCacheEntityToChildModel(ChildCacheEntity entity) {
        return new ChildModel(entity.id, entity.name);
    }
}
