package one.nem.kidshift.data.modules;

import dagger.Binds;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.impl.TaskDataDummyImpl;

abstract public class TaskDataDummyModule {

    @Binds
    abstract TaskData bindTaskData(TaskDataDummyImpl taskDataDummyImpl);
}
