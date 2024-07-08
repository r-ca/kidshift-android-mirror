package one.nem.kidshift.data.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import one.nem.kidshift.data.room.converter.DateTypeConverter;

@Entity(tableName = "history_cache")
@TypeConverters({DateTypeConverter.class})
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
