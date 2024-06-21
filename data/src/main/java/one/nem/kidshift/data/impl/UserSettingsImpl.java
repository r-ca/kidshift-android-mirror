package one.nem.kidshift.data.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import javax.inject.Inject;

import one.nem.kidshift.data.UserSettings;
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

    public class ApiSettingImpl implements UserSettings.ApiSetting {

        transient
        SharedPrefUtils sharedPrefUtils;

        String apiBaseUrl;

        ApiSettingImpl() {
            sharedPrefUtils = sharedPrefUtilsFactory.create("user_settings");
            ApiSettingImpl apiSetting = sharedPrefUtils.getObject("api_setting", ApiSettingImpl.class);
            // TODO: リフレクションつかって一括でやる？(プロパティ数があまりにも増えるなら?), 三項演算子やめる?, デフォルト値の設定方法を改善する
            if (apiSetting != null) {
                apiBaseUrl = apiSetting.getApiBaseUrl() == null ? "https://kidshift-beta.nem.one/" :
                        Objects.equals(apiSetting.getApiBaseUrl(), "") ? "https://kidshift-beta.nem.one/" : apiSetting.getApiBaseUrl();
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

        SharedPrefUtils sharedPrefUtils;

        int defaultIconColor;

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
