package one.nem.kidshift.model.callback;

import one.nem.kidshift.model.tasks.TaskItemModel;

public interface TaskItemModelCallback {

    void onUnchanged();

    void onUpdated(TaskItemModel taskItem);

    void onFailed(String message);
}
