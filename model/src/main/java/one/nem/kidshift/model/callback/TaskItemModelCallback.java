package one.nem.kidshift.model.callback;

import java.util.List;

import one.nem.kidshift.model.tasks.TaskItemModel;

public interface TaskItemModelCallback {

    void onUnchanged();

    void onUpdated(List<TaskItemModel> taskItem);

    void onFailed(String message);
}
