package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.impl.ParentDataImpl;

@Module
@InstallIn(FragmentComponent.class)
public abstract class ParentDataModule {

    @Binds
    public abstract ParentData bindParentData(ParentDataImpl parentDataImpl);
}
