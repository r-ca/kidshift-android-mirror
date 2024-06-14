package one.nem.kidshift.data;

import android.graphics.Color;

public interface UserSettings {

    interface Task {
        Color getDefaultIconColor();
        String getDefaultIconEmoji();
    }
}
