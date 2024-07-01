package one.nem.kidshift.data.impl;

import javax.inject.Inject;

import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.utils.SharedPrefUtils;
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

    @Override
    public AppCommonSetting getAppCommonSetting() {
        return new AppCommonSettingImpl();
    }

    @Override
    public SharedPrefCache getCache() {
        return new SharedPrefCacheImpl();
    }

    public class SharedPrefCacheImpl implements UserSettings.SharedPrefCache {

        transient
        SharedPrefUtils sharedPrefUtils;

        ParentModel parent;

        SharedPrefCacheImpl() {
            sharedPrefUtils = sharedPrefUtilsFactory.create("user_settings");
            SharedPrefCacheImpl sharedPrefCache = sharedPrefUtils.getObject("shared_pref_cache", SharedPrefCacheImpl.class);
            if (sharedPrefCache != null) {
                parent = sharedPrefCache.getParent();
            } else {
                parent = null;
            }
        }

        private void save() {
            sharedPrefUtils.saveObject("shared_pref_cache", this);
        }

        @Override
        public ParentModel getParent() {
            return parent;
        }

        @Override
        public void setParent(ParentModel parent) {
            this.parent = parent;
            save();
        }
    }

    public class AppCommonSettingImpl implements UserSettings.AppCommonSetting {

        transient
        SharedPrefUtils sharedPrefUtils;

        boolean loggedIn;
        String accessToken;
        boolean childMode;

        AppCommonSettingImpl() {
            sharedPrefUtils = sharedPrefUtilsFactory.create("user_settings");
            AppCommonSettingImpl appCommonSetting = sharedPrefUtils.getObject("app_common_setting", AppCommonSettingImpl.class);
            if (appCommonSetting != null) {
                loggedIn = appCommonSetting.isLoggedIn();
                accessToken = appCommonSetting.getAccessToken().isEmpty() ? "" : appCommonSetting.getAccessToken();
                childMode = appCommonSetting.isChildMode();
            } else {
                loggedIn = false;
                accessToken = "";
                childMode = false;
            }
        }

        private void save() {
            sharedPrefUtils.saveObject("app_common_setting", this);
        }

        @Override
        public boolean isLoggedIn() {
            return loggedIn;
        }

        @Override
        public void setLoggedIn(boolean loggedIn) {
            this.loggedIn = loggedIn;
            save();
        }

        @Override
        public String getAccessToken() {
            return accessToken;
        }

        @Override
        public void setAccessToken(String token) {
            accessToken = token;
            save();
        }

        @Override
        public boolean isChildMode() {
            return childMode;
        }

        @Override
        public void setChildMode(boolean childMode) {
            this.childMode = childMode;
            save();
        }
    }

    public class ApiSettingImpl implements UserSettings.ApiSetting {

        transient
        SharedPrefUtils sharedPrefUtils;

        String apiBaseUrl;

        ApiSettingImpl() {
            sharedPrefUtils = sharedPrefUtilsFactory.create("user_settings");
            ApiSettingImpl apiSetting = sharedPrefUtils.getObject("api_setting", ApiSettingImpl.class);
            // TODO: リフレクションつかって一括でやる？(プロパティ数があまりにも増えるなら?), 三項演算子やめる?, デフォルト値の設定方法を改善する
            if (apiSetting != null) {
                apiBaseUrl = apiSetting.apiBaseUrl.isEmpty() ? "https://kidshift-beta.nem.one/" : apiSetting.apiBaseUrl;
            } else {
                apiBaseUrl = "https://kidshift-beta.nem.one/";
            }
        }

        private void save() {
            sharedPrefUtils.saveObject("api_setting", this);
        }

        @Override
        public String getApiBaseUrl() {
            return apiBaseUrl;
        }

        @Override
        public void setApiBaseUrl(String url) {
            apiBaseUrl = url;
            save();
        }
    }

    public class TaskSettingImpl implements UserSettings.TaskSetting {

        transient
        SharedPrefUtils sharedPrefUtils;

        int defaultIconColor;

        String defaultIconEmoji;

        TaskSettingImpl() {
            sharedPrefUtils = sharedPrefUtilsFactory.create("user_settings");
            TaskSettingImpl taskSetting = sharedPrefUtils.getObject("task_setting", TaskSettingImpl.class);
            if (taskSetting != null) {
                defaultIconColor = taskSetting.getDefaultIconColor() == 0 ? 0 : taskSetting.getDefaultIconColor();
                defaultIconEmoji = taskSetting.getDefaultIconEmoji().isEmpty() ? "" : taskSetting.getDefaultIconEmoji();
            } else {
                defaultIconColor = 0;
                defaultIconEmoji = "";
            }
        }

        private void save() {
            sharedPrefUtils.saveObject("task_setting", this);
        }

        @Override
        public int getDefaultIconColor() {
            return defaultIconColor;
        }

        @Override
        public void setDefaultIconColor(int color) {
            defaultIconColor = color;
            save();
        }

        @Override
        public String getDefaultIconEmoji() {
            return defaultIconEmoji;
        }

        @Override
        public void setDefaultIconEmoji(String emoji) {
            defaultIconEmoji = emoji;
            save();
        }
    }
}
