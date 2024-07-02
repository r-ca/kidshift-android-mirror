package one.nem.kidshift.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import one.nem.kidshift.data.room.entity.ChildCacheEntity;

@Dao
public interface ChildCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChild(ChildCacheEntity child);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChildList(List<ChildCacheEntity> childList);

    @Query("SELECT * FROM child_cache WHERE id = :childId")
    ChildCacheEntity getChild(String childId);

    @Query("SELECT * FROM child_cache")
    List<ChildCacheEntity> getChildList();

}
