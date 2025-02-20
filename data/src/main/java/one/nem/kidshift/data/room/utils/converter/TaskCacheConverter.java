package one.nem.kidshift.data.room.utils.converter;

import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.room.entity.TaskCacheEntity;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class TaskCacheConverter {

    /**
     * TaskItemModelをTaskCacheEntityに変換する
     * @param taskModel TaskItemModel
     * @return TaskCacheEntity
     */
    public static TaskCacheEntity taskModelToTaskCacheEntity(TaskItemModel taskModel) {
        TaskCacheEntity entity = new TaskCacheEntity();
        entity.id = taskModel.getId();
        entity.name = taskModel.getName();
        entity.iconEmoji = taskModel.getIconEmoji();
        entity.reward = taskModel.getReward();
        return entity;
    }

    /**
     * TaskCacheEntityをTaskItemModelに変換する
     * @param taskList TaskItemModelリスト
     * @return TaskCacheEntityリスト
     */
    public static List<TaskCacheEntity> taskModelListToTaskCacheEntityList(List<TaskItemModel> taskList) {
        return taskList.stream().map(TaskCacheConverter::taskModelToTaskCacheEntity).collect(Collectors.toList());
    }

    /**
     * TaskCacheEntityをTaskItemModelに変換する
     * @param entity TaskCacheEntity
     * @return TaskItemModel
     */
    public static TaskItemModel taskCacheEntityToTaskModel(TaskCacheEntity entity) {
        TaskItemModel model = new TaskItemModel();
        model.setId(entity.id);
        model.setName(entity.name);
        model.setReward(entity.reward);
        model.setIconEmoji(entity.iconEmoji == null ? "" : entity.iconEmoji); // Workaround
        model.setBgColor(null); // TODO: 実装
        model.setAttachedChildren(null); // TODO: 実装
        return model;
    }

    /**
     * TaskCacheEntityリストをTaskItemModelリストに変換する
     * @param entityList TaskCacheEntityリスト
     * @return TaskItemModelリスト
     */
    public static List<TaskItemModel> taskCacheEntityListToTaskModelList(List<TaskCacheEntity> entityList) {
        return entityList.stream().map(TaskCacheConverter::taskCacheEntityToTaskModel).collect(Collectors.toList());
    }


}
