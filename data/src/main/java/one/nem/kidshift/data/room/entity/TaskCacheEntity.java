package one.nem.kidshift.data.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_cache")
public class TaskCacheEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int Id;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "icon_emoji")
    private String iconEmoji;

    @ColumnInfo(name = "reward")
    private int reward;
}
