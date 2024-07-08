package one.nem.kidshift.data;

import one.nem.kidshift.model.ParentModel;

public interface UserSettings {

    /**
     * API設定のインスタンスを取得
     * @return ApiSetting API設定
     */
    ApiSetting getApiSetting();

    /**
     * タスク設定のインスタンスを取得
     * @return TaskSetting タスク設定
     */
    TaskSetting getTaskSetting();

    /**
     * アプリ共通設定のインスタンスを取得
     * @return AppCommonSetting アプリ共通設定
     */
    AppCommonSetting getAppCommonSetting();

    /**
     * キャッシュのインスタンスを取得
     * @return SharedPrefCache キャッシュ
     */
    SharedPrefCache getCache();

    interface AppCommonSetting {
        boolean isLoggedIn();
        void setLoggedIn(boolean loggedIn);

        String getAccessToken();
        void setAccessToken(String token);

        boolean isChildMode();
        void setChildMode(boolean childMode);

        String getChildId();
        void setChildId(String childId);
    }

    interface SharedPrefCache {
        ParentModel getParent();
        void setParent(ParentModel parent);
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
