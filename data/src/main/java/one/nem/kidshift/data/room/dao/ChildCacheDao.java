package one.nem.kidshift.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import one.nem.kidshift.data.room.entity.ChildCacheEntity;
i

@Dao
public interface ChildCacheDao {

    @Insert
    void insertChild(ChildCacheEntity child);

    @Query("SELECT * FROM child_cache WHERE id = :childId")
    ChildCacheEntity getChild(String childId);

    @Query("SELECT * FROM child_cache")
    List<ChildCacheEntity> getChildList();

}
