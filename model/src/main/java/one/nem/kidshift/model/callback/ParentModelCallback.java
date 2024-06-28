package one.nem.kidshift.model.callback;

import one.nem.kidshift.model.ParentModel;

public interface ParentModelCallback {
    void onUnchanged();
    void onUpdated(ParentModel parent);
    void onFailed(String message);
}
