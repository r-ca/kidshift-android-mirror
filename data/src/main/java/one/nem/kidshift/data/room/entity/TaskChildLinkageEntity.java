package one.nem.kidshift.data.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_child_linkage")
public class TaskChildLinkageEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "task_id")
    private String taskId;

    @ColumnInfo(name = "child_id")
    private String childId;

}
