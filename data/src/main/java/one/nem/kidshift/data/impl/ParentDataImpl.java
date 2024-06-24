package one.nem.kidshift.data.impl;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.model.ParentModel;
import retrofit2.Call;

public class ParentDataImpl implements ParentData {

    private KidShiftApiService kidshiftApiService;

    @Inject
    public ParentDataImpl(KidShiftApiService kidshiftApiService) {
        this.kidshiftApiService = kidshiftApiService;
    }

    @Override
    public CompletableFuture<ParentModel> getParent() {
        return CompletableFuture.supplyAsync(() -> {
            ParentInfoResponse parentInfoResponse;
            Call<ParentInfoResponse> response = kidshiftApiService.getParentInfo();
            try {
                parentInfoResponse = response.execute().body();
                ParentModel parent = new ParentModel();
                assert parentInfoResponse != null;
                parent.setInternalId(parentInfoResponse.getId());
                parent.setEmail(parentInfoResponse.getEmail());
                parent.setDisplayName(parentInfoResponse.getDisplayName());
                // TODO: 他のプロパティも処理する
                return parent;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void updateParent(ParentModel parent) {

    }
}
