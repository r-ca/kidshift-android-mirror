package one.nem.kidshift.data.retrofit.model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.retrofit.model.child.ChildAddRequest;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.data.retrofit.model.child.ChildResponse;
import one.nem.kidshift.model.ChildModel;

public class ChildModelConverter {

    /**
     * ChildModelをChildAddRequestに変換する
     * @param childModel ChildModel
     * @return ChildAddRequest
     */
    public static ChildAddRequest childModelToChildAddRequest(ChildModel childModel) {
        ChildAddRequest request = new ChildAddRequest();
        request.setName(childModel.getName());
        return request;
    }

    /**
     * ChildListResponseをChildModelリストに変換する
     * @param childListResponse ChildListResponse
     * @return ChildModelリスト
     */
    public static List<ChildModel> childListResponseToChildModelList(ChildListResponse childListResponse) {
        return childListResponse.getList().stream().map(ChildModelConverter::childResponseToChildModel).collect(Collectors.toList());
    }

    /**
     * ChildResponseをChildModelに変換する
     * @param childResponse ChildResponse
     * @return ChildModel
     */
    public static ChildModel childResponseToChildModel(ChildResponse childResponse) {
        return new ChildModel(childResponse.getId(), childResponse.getName());
    }

}
