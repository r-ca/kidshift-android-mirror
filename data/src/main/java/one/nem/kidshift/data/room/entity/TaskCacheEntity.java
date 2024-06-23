package one.nem.kidshift.data.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_cache")
public class TaskCacheEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public String Id;

    @ColumnInfo(name = "display_name")
    public String displayName;

    @ColumnInfo(name = "icon_emoji")
    public String iconEmoji;

    @ColumnInfo(name = "reward")
    public int reward;
}
