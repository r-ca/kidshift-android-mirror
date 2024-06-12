package one.nem.kidshift.utils.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import dagger.hilt.android.qualifiers.ApplicationContext;
import one.nem.kidshift.utils.SharedPrefUtils;

public class SharedPrefUtilsImpl implements SharedPrefUtils {

    private final Context applicationContext;
    private final String name;
    private final Gson gson;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @AssistedInject
    public SharedPrefUtilsImpl(@ApplicationContext Context applicationContext, @Assisted String name) {
        this.applicationContext = applicationContext;
        this.name = name;
        this.gson = new Gson();

        // Get the shared preferences
        sharedPreferences = applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    public <T> String saveObject(String key, T object) {
        String json = gson.toJson(object);
        editor.putString(key, json).apply();
        return key;
    }

    @Override
    public <T> String saveObject(T object) {
        String key = String.valueOf(sharedPreferences.getAll().size());
        return saveObject(key, object);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        String json = sharedPreferences.getString(key, null);
        return gson.fromJson(json, clazz);
    }

    @Override
    public <T> List<T> getObjects(Class<T> clazz) {
        // SharedPreferenceの中身を全て取得
        List<T> list = null;
        for (String key : sharedPreferences.getAll().keySet()) {
            String json = sharedPreferences.getString(key, null);
            T object = gson.fromJson(json, clazz);
            list.add(object);
        }
        return list;
    }

    @Override
    public void reset() {
        editor.clear().apply();
    }

    @Override
    public void remove(String key) {
        editor.remove(key).apply();
    }
}
