package one.nem.kidshift.data.room.utils.converter;

import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.room.entity.TaskCacheEntity;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class TaskCacheConverter {

    public static TaskCacheEntity taskModelToTaskCacheEntity(TaskItemModel taskModel) {
        TaskCacheEntity entity = new TaskCacheEntity();
        entity.id = taskModel.getId();
        entity.name = taskModel.getName();
        entity.iconEmoji = taskModel.getIconEmoji();
        entity.reward = taskModel.getReward();
        return entity;
    }

    public static List<TaskCacheEntity> taskModelListToTaskCacheEntityList(List<TaskItemModel> taskList) {
        return taskList.stream().map(TaskCacheConverter::taskModelToTaskCacheEntity).collect(Collectors.toList());
    }

    public static TaskItemModel taskCacheEntityToTaskModel(TaskCacheEntity entity) {
        return new TaskItemModel(entity.id, entity.name, entity.iconEmoji, entity.reward, null);
    }

    public static List<TaskItemModel> taskCacheEntityListToTaskModelList(List<TaskCacheEntity> entityList) {
        return entityList.stream().map(TaskCacheConverter::taskCacheEntityToTaskModel).collect(Collectors.toList());
    }


}
