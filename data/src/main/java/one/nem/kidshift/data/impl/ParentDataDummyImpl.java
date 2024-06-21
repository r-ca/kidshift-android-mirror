package one.nem.kidshift.data.impl;

import javax.inject.Inject;

import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.model.ParentModel;

public class ParentDataDummyImpl implements ParentData {

    @Inject
    public ParentDataDummyImpl() {
    }

    @Override
    public ParentModel getParent(String parentId) {
        return new ParentModel("ID", "Parent Name", "homeGroupId", "hoge@example.com");
    }

    @Override
    public void updateParent(ParentModel parent) {

    }
}
