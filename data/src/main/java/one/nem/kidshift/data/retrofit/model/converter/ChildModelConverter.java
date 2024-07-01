package one.nem.kidshift.data.retrofit.model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.retrofit.model.child.ChildAddRequest;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.data.retrofit.model.child.ChildResponse;
import one.nem.kidshift.model.ChildModel;

public class ChildModelConverter {

    // 日本語

    /**
     * 子供モデルを子供追加リクエストに変換する
     * @param childModel 子供モデル
     * @return 子供追加リクエスト
     */
    public static ChildAddRequest childModelToChildAddRequest(ChildModel childModel) {
        ChildAddRequest request = new ChildAddRequest();
        request.setName(childModel.getName());
        return request;
    }

    /**
     * 子供リストレスポンスを子供モデルリストに変換する
     * @param childListResponse 子供リストレスポンス
     * @return 子供モデルリスト
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
