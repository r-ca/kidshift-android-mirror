package one.nem.kidshift.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import one.nem.kidshift.data.room.entity.HistoryCacheEntity;
import one.nem.kidshift.data.room.model.HistoryWithTask;

@Dao
public interface HistoryCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistory(HistoryCacheEntity history);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistoryList(List<HistoryCacheEntity> historyList);

    @Query("SELECT * FROM history_cache WHERE id = :historyId")
    HistoryCacheEntity getHistory(String historyId);

    @Query("SELECT * FROM history_cache")
    List<HistoryCacheEntity> getHistoryList();

    @Query("SELECT * FROM history_cache WHERE child_id = :childId")
    List<HistoryCacheEntity> getHistoryListByChildId(String childId);

    @Transaction
    @Query("SELECT * FROM history_cache")
    List<HistoryWithTask> getHistoryWithTasks();

    @Transaction
    @Query("SELECT * FROM history_cache WHERE child_id = :childId")
    List<HistoryWithTask> getHistoryWithTasksByChildId(String childId);

    @Query("SELECT * FROM history_cache WHERE task_id = :taskId")
    List<HistoryCacheEntity> getHistoryListByTaskId(String taskId);

    @Query("DELETE FROM history_cache WHERE id = :historyId")
    void deleteHistory(String historyId);

    @Query("DELETE FROM history_cache WHERE child_id = :childId")
    void deleteHistoryByChildId(String childId);
}
