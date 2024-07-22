package one.nem.kidshift.data.retrofit.model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import one.nem.kidshift.data.retrofit.model.task.HistoryListResponse;
import one.nem.kidshift.data.retrofit.model.task.HistoryResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskResponse;
import one.nem.kidshift.model.HistoryModel;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class HistoryModelConverter { // TODO: JavaDoc

    public static HistoryModel historyResponseToHistoryModel(HistoryResponse historyResponse) {
        HistoryModel historyModel = new HistoryModel();
        historyModel.setId(historyResponse.getId());
        historyModel.setTaskId(historyResponse.getTaskId());
        historyModel.setChildId(historyResponse.getChildId());
        historyModel.setRegisteredAt(historyResponse.getRegisteredAt());
        historyModel.setPaid(historyResponse.isPaid());
        return historyModel;
    }

    public static HistoryResponse historyModelToHistoryResponse(HistoryModel historyModel) {
        HistoryResponse historyResponse = new HistoryResponse();
        historyResponse.setId(historyModel.getId());
        historyResponse.setTaskId(historyModel.getTaskId());
        historyResponse.setChildId(historyModel.getChildId());
        historyResponse.setRegisteredAt(historyModel.getRegisteredAt());
        historyResponse.setPaid(historyModel.isPaid());
        return historyResponse;
    }

    public static List<HistoryModel> historyListResponseToHistoryModelList(HistoryListResponse historyListResponse) {
        List<HistoryModel> historyModelList = new ArrayList<>();
        for (HistoryResponse historyResponse : historyListResponse.getList()) {
            historyModelList.add(historyResponseToHistoryModel(historyResponse));
        }
        return historyModelList;
    }

    public static HistoryListResponse historyModelListToHistoryListResponse(List<HistoryModel> historyModelList) {
        HistoryListResponse historyListResponse = new HistoryListResponse();
        List<HistoryResponse> historyResponseList = new ArrayList<>();
        for (HistoryModel historyModel : historyModelList) {
            historyResponseList.add(historyModelToHistoryResponse(historyModel));
        }
        historyListResponse.setList(historyResponseList);
        return historyListResponse;
    }

    private static TaskResponse emptyTaskItemModel() {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId("");
        taskResponse.setName("Critical Error occurred(Your data (on server) is may be corrupted.)");
        taskResponse.setReward(0);
        return taskResponse;
    }

    public static List<HistoryModel> historyListResponseAndTaskListResponseToHistoryModelList(HistoryListResponse historyListResponse, TaskListResponse taskListResponse) {
        List<HistoryModel> historyModelList = new ArrayList<>();
        for (HistoryResponse historyResponse : historyListResponse.getList()) {
            HistoryModel historyModel = historyResponseToHistoryModel(historyResponse);
            if (taskListResponse == null || taskListResponse.getList() == null || taskListResponse.getList().isEmpty()) {
                continue;
            }
            TaskItemModel taskItemModel = TaskModelConverter.taskResponseToTaskItemModel(
                    Objects.requireNonNull(taskListResponse.getList().stream()
                            .filter(taskResponse -> taskResponse.getId().equals(historyModel.getTaskId()))
                            .findFirst().orElse(emptyTaskItemModel())));
            historyModel.setTaskName(taskItemModel.getName());
            historyModel.setReward(taskItemModel.getReward());
            historyModelList.add(historyModel);
        }
        return historyModelList;
    }
}