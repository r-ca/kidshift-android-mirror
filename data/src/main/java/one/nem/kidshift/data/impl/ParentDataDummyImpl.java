package one.nem.kidshift.data.impl;

import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.model.ParentModel;

public class ParentDataDummyImpl implements ParentData {
    @Override
    public ParentModel getParent(String parentId) {
        return null;
    }

    @Override
    public void updateParent(ParentModel parent) {

    }
}
