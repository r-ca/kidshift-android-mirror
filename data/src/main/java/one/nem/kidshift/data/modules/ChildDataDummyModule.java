package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.impl.ChildDataDummyImpl;

@Module
@InstallIn(FragmentComponent.class)
abstract public class ChildDataDummyModule {

    @Binds
    public abstract ChildData bindChildData(ChildDataDummyImpl childDataDummyImpl);
}
