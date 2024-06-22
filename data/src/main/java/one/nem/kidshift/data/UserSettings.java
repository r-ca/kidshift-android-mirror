package one.nem.kidshift.data;

public interface UserSettings {

    ApiSetting getApiSetting();
    TaskSetting getTaskSetting();
    AppCommon getAppCommon();

    interface AppCommon {
        boolean isLoggedIn();
        void setLoggedIn(boolean loggedIn);
    }

    interface ApiSetting {
        String getApiBaseUrl();
        void setApiBaseUrl(String url);
    }

    interface TaskSetting {
        int getDefaultIconColor();
        void setDefaultIconColor(int color);
        String getDefaultIconEmoji();
        void setDefaultIconEmoji(String emoji);

    }
}
