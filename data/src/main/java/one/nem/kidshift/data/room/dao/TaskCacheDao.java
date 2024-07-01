package one.nem.kidshift.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import one.nem.kidshift.data.room.entity.TaskCacheEntity;

@Dao
public interface TaskCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskCacheEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskList(List<TaskCacheEntity> taskList);

    @Query("SELECT * FROM task_cache WHERE id = :taskId")
    TaskCacheEntity getTask(String taskId);

    @Query("SELECT * FROM task_cache")
    List<TaskCacheEntity> getTaskList();
}
