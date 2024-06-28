package one.nem.kidshift.data.retrofit.model.converter;

import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.model.ParentModel;

public class ParentModelConverter {
    public static ParentModel parentListResponseToParentModel(ParentInfoResponse parentInfoResponse) {
        return new ParentModel(parentInfoResponse.getId(), parentInfoResponse.getDisplayName(), parentInfoResponse.getEmail());
    }

    public static ParentModel parentInfoResponseToParentModel(ParentInfoResponse parentInfoResponse) {
        return new ParentModel(parentInfoResponse.getId(), parentInfoResponse.getDisplayName(), parentInfoResponse.getEmail());
    }

    public static ParentInfoResponse parentModelToParentInfoResponse(ParentModel parentModel) {
        return new ParentInfoResponse(parentModel.getId(), parentModel.getName(), parentModel.getEmail());
    }

    public static ParentModel parentModelToParentModel(ParentModel parentModel) {
        return new ParentModel(parentModel.getId(), parentModel.getName(), parentModel.getEmail());
    }

}
