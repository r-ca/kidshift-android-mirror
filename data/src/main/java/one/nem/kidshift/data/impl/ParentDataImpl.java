package one.nem.kidshift.data.impl;

import javax.inject.Inject;

import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.model.ParentModel;

public class ParentDataImpl implements ParentData {

    private KidShiftApiService kidshiftApiService;

    @Inject
    public ParentDataImpl(KidShiftApiService kidshiftApiService) {
        this.kidshiftApiService = kidshiftApiService;
    }

    @Override
    public ParentModel getParent(String parentId) {
        return null;
    }

    @Override
    public void updateParent(ParentModel parent) {

    }
}
