package one.nem.kidshift.data.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.inject.Inject;

import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.data.retrofit.model.converter.ChildModelConverter;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

public class ChildDataImpl implements ChildData {

    private KidShiftApiService kidShiftApiService;

    private KSLogger logger;

    @Inject
    public ChildDataImpl(KidShiftApiService kidShiftApiService, KSLoggerFactory loggerFactory) {
        this.kidShiftApiService = kidShiftApiService;
        this.logger = loggerFactory.create("ChildDataImpl");
    }

    @Override
    public CompletableFuture<ChildModel> getChild(String childId) {
        return null;
    }

    @Override
    public CompletableFuture<List<ChildModel>> getChildList() { // TODO-rca: DBにキャッシュするように修正する
        return CompletableFuture.supplyAsync(() -> {
            Call<ChildListResponse> call = kidShiftApiService.getChildList();
            try {
                Response<ChildListResponse> response = call.execute();
                if (!response.isSuccessful()) return null; // TODO-rca: nullとするかは検討

                ChildListResponse body = response.body();
                if (body == null) return null;

                return ChildModelConverter.childListResponseToChildModelList(body);
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
