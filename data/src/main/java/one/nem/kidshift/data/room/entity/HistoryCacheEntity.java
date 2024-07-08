package one.nem.kidshift.data.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "history_cache")
public class HistoryCacheEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    public String id;

    @ColumnInfo(name = "child_id")
    public String childId;

    @ColumnInfo(name = "task_id")
    public String taskId;

    @ColumnInfo(name = "registered_at")
    public Date registeredAt;
}
