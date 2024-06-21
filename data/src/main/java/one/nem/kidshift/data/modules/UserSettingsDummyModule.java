package one.nem.kidshift.data.modules;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.components.SingletonComponent;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.impl.UserSettingsDummyImpl;
import one.nem.kidshift.data.impl.UserSettingsImpl;

@Module
@InstallIn(SingletonComponent.class)
abstract public class UserSettingsDummyModule {

//    @Binds
//    abstract UserSettings bindUserSettings(UserSettingsDummyImpl userSettingsDummyImpl);

    @Binds
    public abstract UserSettings bindUserSettings(UserSettingsImpl userSettingsImpl);
}
