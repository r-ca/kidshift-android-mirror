package one.nem.kidshift.data.impl;

import one.nem.kidshift.data.UserSettings;

public class UserSettingsImpl implements UserSettings {
    @Override
    public ApiSetting getApiSetting() {
        return null;
    }

    @Override
    public TaskSetting getTaskSetting() {
        return null;
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
