package one.nem.kidshift.model.callback;

import java.util.List;

import one.nem.kidshift.model.ChildModel;

public interface ChildModelCallback {
    void onUnchanged();
    void onUpdated(List<ChildModel> childModelList);
    void onFailed(String message);
}