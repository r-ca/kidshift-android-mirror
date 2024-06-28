package one.nem.kidshift.data.retrofit.model.converter;

import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.model.ParentModel;

public class ParentModelConverter {

    public static ParentModel parentInfoResponseToParentModel(ParentInfoResponse parentInfoResponse) {
        return new ParentModel(parentInfoResponse.getId(), parentInfoResponse.getDisplayName(), parentInfoResponse.getEmail());
    }

    public static ParentInfoResponse parentModelToParentInfoResponse(ParentModel parentModel) {
        return new ParentInfoResponse(parentModel.getId(), parentModel.getName(), parentModel.getEmail());
    }

}
