package one.nem.kidshift.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import one.nem.kidshift.data.room.dao.ChildCacheDao;
import one.nem.kidshift.data.room.dao.HistoryCacheDao;
import one.nem.kidshift.data.room.dao.TaskCacheDao;
import one.nem.kidshift.data.room.dao.TaskChildLinkageDao;
import one.nem.kidshift.data.room.entity.ChildCacheEntity;
import one.nem.kidshift.data.room.entity.HistoryCacheEntity;
import one.nem.kidshift.data.room.entity.TaskCacheEntity;
import one.nem.kidshift.data.room.entity.TaskChildLinkageEntity;

@Database(entities = {ChildCacheEntity.class, TaskCacheEntity.class, TaskChildLinkageEntity.class, HistoryCacheEntity.class}, version = 2)
public abstract class KidShiftDatabase extends RoomDatabase {

    public abstract ChildCacheDao childCacheDao();

    public abstract TaskCacheDao taskCacheDao();

    public abstract TaskChildLinkageDao taskChildLinkageDao();

    public abstract HistoryCacheDao historyCacheDao();

}
