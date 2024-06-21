package one.nem.kidshift.data.impl;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.model.ChildModel;

public class ChildDataDummyImpl implements ChildData {
    @Override
    public ChildModel getChild(String childId) {
        return null;
    }

    @Override
    public void updateChild(ChildModel child) {

    }

    @Override
    public void addChild(ChildModel child) {

    }

    @Override
    public void removeChild(String childId) {

    }
}
