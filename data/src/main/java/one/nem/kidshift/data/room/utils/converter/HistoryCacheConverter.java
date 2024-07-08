package one.nem.kidshift.data.room.utils.converter;

import java.util.ArrayList;
import java.util.List;

import one.nem.kidshift.data.room.entity.HistoryCacheEntity;
import one.nem.kidshift.data.room.model.HistoryWithTask;
import one.nem.kidshift.model.HistoryModel;

public class HistoryCacheConverter {

    public static HistoryCacheEntity historyModelToHistoryCacheEntity(HistoryModel historyModel) {
        HistoryCacheEntity historyCacheEntity = new HistoryCacheEntity();
        historyCacheEntity.id = historyModel.getId();
        historyCacheEntity.taskId = historyModel.getTaskId();
        historyCacheEntity.childId = historyModel.getChildId();
        historyCacheEntity.registeredAt = historyModel.getRegisteredAt();
        return historyCacheEntity;
    }

    public static HistoryModel historyCacheEntityToHistoryModel(HistoryCacheEntity historyCacheEntity) {
        HistoryModel historyModel = new HistoryModel();
        historyModel.setId(historyCacheEntity.id);
        historyModel.setTaskId(historyCacheEntity.taskId);
        historyModel.setChildId(historyCacheEntity.childId);
        historyModel.setRegisteredAt(historyCacheEntity.registeredAt);
        return historyModel;
    }

    public static List<HistoryCacheEntity> historyModelListToHistoryCacheEntityList(List<HistoryModel> historyModelList) {
        List<HistoryCacheEntity> historyCacheEntityList = new ArrayList<>();
        for (HistoryModel historyModel : historyModelList) {
            historyCacheEntityList.add(historyModelToHistoryCacheEntity(historyModel));
        }
        return historyCacheEntityList;
    }

    public static List<HistoryModel> historyCacheEntityListToHistoryModelList(List<HistoryCacheEntity> historyCacheEntityList) {
        List<HistoryModel> historyModelList = new ArrayList<>();
        for (HistoryCacheEntity historyCacheEntity : historyCacheEntityList) {
            historyModelList.add(historyCacheEntityToHistoryModel(historyCacheEntity));
        }
        return historyModelList;
    }

    public static List<HistoryModel> historyWithTaskListToHistoryModelList(List<HistoryWithTask> result) {
        List<HistoryModel> historyModelList = new ArrayList<>();
        for (HistoryWithTask historyWithTask : result) {
            HistoryModel historyModel = new HistoryModel();
            historyModel.setId(historyWithTask.history.id);
            historyModel.setTaskId(historyWithTask.history.taskId);
            historyModel.setChildId(historyWithTask.history.childId);
            historyModel.setRegisteredAt(historyWithTask.history.registeredAt);
            historyModel.setTaskName(historyWithTask.task.name);
            historyModel.setReward(historyWithTask.task.reward);
            historyModelList.add(historyModel);
        }
        return historyModelList;
    }
}
