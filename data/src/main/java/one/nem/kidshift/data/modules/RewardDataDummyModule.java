package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ViewModelComponent;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.impl.RewardDataDummyImpl;

@Module
@InstallIn(FragmentComponent.class)
abstract public class RewardDataDummyModule {

    @Binds
    public abstract RewardData bindRewardData(RewardDataDummyImpl rewardDataDummyImpl);
}
