package one.nem.kidshift.utils;

import android.content.SharedPreferences;

import java.util.List;

public interface SharedPrefUtils {

    // Single
    <T> String saveObject(String key, T object);
    <T> String saveObject(T object); // auto generate key
    <T> T getObject(String key, Class<T> clazz);

    // Common
    void reset();
    void remove(String key);
}
