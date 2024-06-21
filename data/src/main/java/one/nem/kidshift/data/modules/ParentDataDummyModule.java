package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.impl.ParentDataDummyImpl;

@Module
@InstallIn(FragmentComponent.class)
abstract public class ParentDataDummyModule {

    @Binds
    public abstract ParentData bindParentData(ParentDataDummyImpl parentDataDummyImpl);
}
