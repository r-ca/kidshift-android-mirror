package one.nem.kidshift.data.retrofit.model.task;

import java.util.List;

public class HistoryListResponse {
    List<HistoryResponse> list;

    public HistoryListResponse(List<HistoryResponse> list) {
        this.list = list;
    }

    public HistoryListResponse() {
    }

    public List<HistoryResponse> getList() {
        return list;
    }

    public void setList(List<HistoryResponse> list) {
        this.list = list;
    }
}
