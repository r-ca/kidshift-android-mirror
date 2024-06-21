package one.nem.kidshift.data;

public interface UserSettings {

    ApiSetting getApiSetting();
    TaskSetting getTaskSetting();

    interface ApiSetting {
        String getApiBaseUrl();
    }

    interface TaskSetting {
        int getDefaultIconColor();
        String getDefaultIconEmoji();
    }
}
