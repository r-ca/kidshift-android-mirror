package one.nem.kidshift.data.retrofit.model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import one.nem.kidshift.data.retrofit.model.child.ChildAddRequest;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.data.retrofit.model.child.ChildResponse;
import one.nem.kidshift.model.ChildModel;

public class ChildModelConverter {

    public static ChildAddRequest childModelToChildAddRequest(ChildModel childModel) {
        ChildAddRequest request = new ChildAddRequest();
        request.setName(childModel.getName());
        return request;
    }

    public static List<ChildModel> childListResponseToChildModelList(ChildListResponse childListResponse) {
        return childListResponse.getList().stream().map(child -> {
            ChildModel model = new ChildModel();
            model.setName(child.getName());
            model.setId(child.getId());
            return model;
        }).collect(Collectors.toList());
    }

}
