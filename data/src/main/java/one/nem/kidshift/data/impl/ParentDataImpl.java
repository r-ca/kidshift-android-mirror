package one.nem.kidshift.data.impl;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.model.ParentModel;
import retrofit2.Call;
import retrofit2.Response;

public class ParentDataImpl implements ParentData {

    private KidShiftApiService kidshiftApiService;

    private UserSettings userSettings;

    @Inject
    public ParentDataImpl(KidShiftApiService kidshiftApiService, UserSettings userSettings) {
        this.kidshiftApiService = kidshiftApiService;
        this.userSettings = userSettings;
    }

    @Override
    public CompletableFuture<ParentModel> getParent() {
        return CompletableFuture.supplyAsync(() -> {
            Response<ParentInfoResponse> parentInfoResponse;
            Call<ParentInfoResponse> response = kidshiftApiService.getParentInfo();
            try {
                parentInfoResponse = response.execute();
                ParentInfoResponse responseBody = parentInfoResponse.body();
                ParentModel parent = new ParentModel();
                assert parentInfoResponse != null;
                parent.setInternalId(responseBody.getId());
                parent.setEmail(responseBody.getEmail());
                parent.setDisplayName(responseBody.getDisplayName().isEmpty() ? responseBody.getEmail() : responseBody.getDisplayName());
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
