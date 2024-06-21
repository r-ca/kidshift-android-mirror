package one.nem.kidshift.data.impl;

import javax.inject.Inject;

import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.utils.factory.SharedPrefUtilsFactory;

public class UserSettingsImpl implements UserSettings {

    SharedPrefUtilsFactory sharedPrefUtilsFactory;

    @Inject
    public UserSettingsImpl(SharedPrefUtilsFactory sharedPrefUtilsFactory) {
        this.sharedPrefUtilsFactory = sharedPrefUtilsFactory;
    }

    @Override
    public ApiSetting getApiSetting() {
        return new ApiSettingImpl();
    }

    @Override
    public TaskSetting getTaskSetting() {
        return new TaskSettingImpl();
    }

    public class ApiSettingImpl implements UserSettings.ApiSetting {
        @Override
        public String getApiBaseUrl() {
            return "https://kidshift-beta.nem.one/";
        }

        @Override
        public void setApiBaseUrl(String url) {

        }
    }

    public class TaskSettingImpl implements UserSettings.TaskSetting {
        @Override
        public int getDefaultIconColor() {
            return 0;
        }

        @Override
        public void setDefaultIconColor(int color) {

        }

        @Override
        public String getDefaultIconEmoji() {
            return "";
        }

        @Override
        public void setDefaultIconEmoji(String emoji) {

        }
    }
}
