package one.nem.kidshift.data.retrofit.model.converter;

import one.nem.kidshift.data.retrofit.model.task.TaskResponse;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class TaskModelConverter {

    public static TaskItemModel taskResponseToTaskItemModel(TaskResponse taskResponse) {
        TaskItemModel model = new TaskItemModel();
        model.setId(taskResponse.getId());
        model.setName(taskResponse.getName());
        model.setReward(taskResponse.getReward());
        model.setBgColor(taskResponse.getBgColor() == null ? "" : taskResponse.getBgColor());
        model.setIconEmoji(taskResponse.getIconEmoji() == null ? "" : taskResponse.getIconEmoji());
        return model;
    }
}
