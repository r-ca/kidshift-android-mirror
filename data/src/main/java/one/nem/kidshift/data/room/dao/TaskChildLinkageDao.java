package one.nem.kidshift.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import one.nem.kidshift.data.room.entity.TaskChildLinkageEntity;

@Dao
public interface TaskChildLinkageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskChildLinkageEntity taskChildLinkageEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaskChildLinkageEntity> taskChildLinkageEntities);
}
