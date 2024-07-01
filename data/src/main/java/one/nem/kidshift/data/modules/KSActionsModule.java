package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.impl.KSActionsImpl;

@Module
@InstallIn(SingletonComponent.class)
public abstract class KSActionsModule {

    @Binds
    public abstract KSActions bindKSActions(KSActionsImpl impl);
}
