package one.nem.kidshift.utils.modules;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class FabManagerModule {

    @Provides
    @Singleton
    public FabManager provideFabManager(KSLoggerFactory loggerFactory) {
        return new FabManager(loggerFactory);
    }
}
