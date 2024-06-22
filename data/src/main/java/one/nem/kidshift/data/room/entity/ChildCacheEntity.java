package one.nem.kidshift.data.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "child_cache")
public class ChildCacheEntity {

    @PrimaryKey
    @ColumnInfo(name = "internal_id")
    private int internalId;

    @ColumnInfo(name = "display_name")
    private String displayName;
}
