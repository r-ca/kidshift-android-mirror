package one.nem.kidshift.data.room.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import one.nem.kidshift.data.room.entity.HistoryCacheEntity;
import one.nem.kidshift.data.room.entity.TaskCacheEntity;

public class HistoryWithTask {
    @Embedded
    public HistoryCacheEntity history;

    @Relation(
            parentColumn = "task_id",
            entityColumn = "id"
    )
    public TaskCacheEntity task;
}