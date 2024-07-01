package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.impl.TaskDataImpl;

@Module
@InstallIn(FragmentComponent.class)
public abstract class TaskDataModule {

    @Binds
    public abstract TaskData bindTaskData(TaskDataImpl taskDataImpl);
}
