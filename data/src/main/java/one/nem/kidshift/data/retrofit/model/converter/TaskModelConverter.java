package one.nem.kidshift.data.retrofit.model.converter;

import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
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

    public static TaskResponse taskItemModelToTaskResponse(TaskItemModel taskItemModel) {
        TaskResponse response = new TaskResponse();
        response.setId(taskItemModel.getId());
        response.setName(taskItemModel.getName());
        response.setReward(taskItemModel.getReward());
        response.setBgColor(taskItemModel.getBgColor());
        response.setIconEmoji(taskItemModel.getIconEmoji());
        return response;
    }

    public static List<TaskItemModel> taskResponseListToTaskItemModelList(TaskListResponse taskListResponse) {
        return taskListResponse.getList().stream().map(TaskModelConverter::taskResponseToTaskItemModel).collect(Collectors.toList());
    }
}
