package one.nem.kidshift.data;

public interface UserSettings {

    interface Api {
        String getApiBaseUrl();
    }

    interface Task {
        int getDefaultIconColor();
        String getDefaultIconEmoji();
    }
}
