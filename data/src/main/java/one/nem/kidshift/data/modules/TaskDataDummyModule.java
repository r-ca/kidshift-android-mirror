package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.impl.TaskDataDummyImpl;

@Module
@InstallIn(FragmentComponent.class)
abstract public class TaskDataDummyModule {

    @Binds
    public abstract TaskData bindTaskData(TaskDataDummyImpl taskDataDummyImpl);
}
