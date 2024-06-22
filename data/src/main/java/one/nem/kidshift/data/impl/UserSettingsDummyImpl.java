package one.nem.kidshift.data.impl;

import android.graphics.Color;

import javax.inject.Inject;

import dagger.Binds;
import one.nem.kidshift.data.UserSettings;

public class UserSettingsDummyImpl implements UserSettings {

    @Inject
    public UserSettingsDummyImpl() {
    }

    @Override
    public UserSettings.TaskSetting getTaskSetting() {
        return new TaskSettingImpl();
    }

    @Override
    public AppCommonSetting getAppCommonSetting() {
        return null;
    }

    @Override
    public UserSettings.ApiSetting getApiSetting() {
        return new ApiSettingImpl();
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
            return Color.parseColor("#FF0000");
        }

        @Override
        public void setDefaultIconColor(int color) {

        }

        @Override
        public String getDefaultIconEmoji() {
            return "🤔";
        }

        @Override
        public void setDefaultIconEmoji(String emoji) {

        }
    }
}
