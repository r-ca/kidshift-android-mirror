package one.nem.kidshift.data.retrofit.model.converter;

import java.util.ArrayList;
import java.util.List;

import one.nem.kidshift.data.retrofit.model.task.HistoryListResponse;
import one.nem.kidshift.data.retrofit.model.task.HistoryResponse;
import one.nem.kidshift.model.HistoryModel;

public class HistoryModelConverter { // TODO: JavaDoc

    public static HistoryModel historyResponseToHistoryModel(HistoryResponse historyResponse) {
        HistoryModel historyModel = new HistoryModel();
        historyModel.setId(historyResponse.getId());
        historyModel.setTaskId(historyResponse.getTaskId());
        historyModel.setChildId(historyResponse.getChildId());
        historyModel.setRegisteredAt(historyResponse.getRegisteredAt());
        return historyModel;
    }

    public static HistoryResponse historyModelToHistoryResponse(HistoryModel historyModel) {
        HistoryResponse historyResponse = new HistoryResponse();
        historyResponse.setId(historyModel.getId());
        historyResponse.setTaskId(historyModel.getTaskId());
        historyResponse.setChildId(historyModel.getChildId());
        historyResponse.setRegisteredAt(historyModel.getRegisteredAt());
        return historyResponse;
    }

    public static List<HistoryModel> historyListResponseToHistoryModelList(HistoryListResponse historyListResponse) {
        List<HistoryModel> historyModelList = new ArrayList<>();
        for (HistoryResponse historyResponse : historyListResponse.getList()) {
            historyModelList.add(historyResponseToHistoryModel(historyResponse));
        }
        return historyModelList;
    }
}