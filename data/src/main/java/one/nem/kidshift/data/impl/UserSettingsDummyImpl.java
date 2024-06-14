package one.nem.kidshift.data.impl;

import android.graphics.Color;

import one.nem.kidshift.data.UserSettings;

public class UserSettingsDummyImpl implements UserSettings {

    class Task implements UserSettings.Task {
        @Override
        public int getDefaultIconColor() {
            return Color.parseColor("#FF0000");
        }

        @Override
        public String getDefaultIconEmoji() {
            return "🤔";
        }
    }
}
