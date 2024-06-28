package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.utils.KSLogger;

public class ChildDataImpl implements ChildData {

    private KidShiftApiService kidShiftApiService;

    private KSLogger logger;

    @Inject
    public ChildDataImpl(KidShiftApiService kidShiftApiService, KSLogger logger) {
        this.kidShiftApiService = kidShiftApiService;
        this.logger = logger;
    }

    @Override
    public CompletableFuture<ChildModel> getChild(String childId) {
        return null;
    }

    @Override
    public CompletableFuture<List<ChildModel>> getChildList() {
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

    @Override
    public CompletableFuture<Integer> issueLoginCode(String childId) {
        return null;
    }
}
