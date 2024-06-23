package one.nem.kidshift.data.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_child_linkage")
public class TaskChildLinkageEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "task_id")
    public String taskId;

    @ColumnInfo(name = "child_id")
    public String childId;

}
