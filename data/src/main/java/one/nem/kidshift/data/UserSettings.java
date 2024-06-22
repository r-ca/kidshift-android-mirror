package one.nem.kidshift.data;

import one.nem.kidshift.model.ParentModel;

public interface UserSettings {

    ApiSetting getApiSetting();
    TaskSetting getTaskSetting();
    AppCommonSetting getAppCommonSetting();

    interface AppCommonSetting {
        boolean isLoggedIn();
        void setLoggedIn(boolean loggedIn);

        String getAccessToken();
        void setAccessToken(String token);

        boolean isChildMode();
        void setChildMode(boolean childMode);
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
