package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.impl.RewardDataImpl;

@Module
@InstallIn(FragmentComponent.class)
public abstract class RewardDataModule {

    @Binds
    public abstract RewardData bindRewardData(RewardDataImpl rewardDataImpl);
}