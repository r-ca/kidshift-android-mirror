package one.nem.kidshift.data.retrofit.model.converter;

import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.model.ParentModel;

public class ParentModelConverter {

    /**
     * ParentInfoResponseをParentModelに変換する
     * @param parentInfoResponse ParentInfoResponse
     * @return ParentModel
     */
    public static ParentModel parentInfoResponseToParentModel(ParentInfoResponse parentInfoResponse) {
        return new ParentModel(parentInfoResponse.getId(), parentInfoResponse.getDisplay_name(), parentInfoResponse.getEmail());
    }

    /**
     * ParentModelをParentInfoResponseに変換する
     * @param parentModel ParentModel
     * @return ParentInfoResponse
     */
    public static ParentInfoResponse parentModelToParentInfoResponse(ParentModel parentModel) {
        return new ParentInfoResponse(parentModel.getId(), parentModel.getName(), parentModel.getEmail());
    }

}
