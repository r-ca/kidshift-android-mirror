package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.impl.ChildDataImpl;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ChildDataModule {

    @Binds
    abstract ChildData provideChildData(ChildDataImpl childDataImpl);
}
