package one.nem.kidshift.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.model.ChildModel;

public class ChildDataDummyImpl implements ChildData {

    @Inject
    public ChildDataDummyImpl() {
    }

    @Override
    public CompletableFuture<ChildModel> getChild(String childId) {
        return CompletableFuture.supplyAsync(() -> new ChildModel("1", "子供1", "idididididid"));
    }

    @Override
    public List<ChildModel> getChildList() {
        // 仮置きデータを生成する
        List<ChildModel> childList = new ArrayList<>();

        childList.add(new ChildModel("1", "子供1", "idididididid"));
        childList.add(new ChildModel("2", "子供2", "idididididid"));
        childList.add(new ChildModel("3", "子供3", "idididididid"));

        return childList;
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

    @Override
    public int issueLoginCode(String childId) {
        return 123456;
    }
}
