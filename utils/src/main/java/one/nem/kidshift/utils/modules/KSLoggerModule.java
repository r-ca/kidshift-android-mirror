package one.nem.kidshift.utils.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.impl.KSLoggerImpl;

@Module
@InstallIn(SingletonComponent.class)
abstract public class KSLoggerModule {

    @Binds
    public abstract KSLogger bindKSLogger(KSLoggerImpl ksLoggerImpl);
}
