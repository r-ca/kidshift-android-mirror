package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.inject.Inject;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.utils.KSLogger;
import retrofit2.Call;
import retrofit2.Response;

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
        return CompletableFuture.supplyAsync(() -> {
            Call<ChildListResponse> call = kidShiftApiService.getChildList();
            try {
                Response<ChildListResponse> response = call.execute();
                if (!response.isSuccessful()) return null;

                ChildListResponse body = response.body();
                if (body == null) return null;
                return body.getList().stream().map(child -> {
                    ChildModel model = new ChildModel();
                    model.setDisplayName(child.getName().isEmpty() ? child.getId() : child.getName());
                    model.setInternalId(child.getId());
                    // 他のプロパティも処理する
                    return model;
                }).collect(Collectors.toList());
            } catch (Exception e) {
                logger.error(e.getMessage());
                return null;
            }
        });
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
