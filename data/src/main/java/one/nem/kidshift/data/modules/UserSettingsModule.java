package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.impl.UserSettingsImpl;

@Module
@InstallIn(SingletonComponent.class)
public abstract class UserSettingsModule {
    @Binds
    public abstract UserSettings bindUserSettings(UserSettingsImpl userSettingsImpl);
}
