package one.nem.kidshift.utils.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.utils.FeatureFlag;
import one.nem.kidshift.utils.impl.FeatureFlagImpl;

@Module
@InstallIn(SingletonComponent.class)
abstract public class FeatureFlagModule {

    @Binds
    public abstract FeatureFlag bindFeatureFlag(FeatureFlagImpl featureFlagImpl);
}
