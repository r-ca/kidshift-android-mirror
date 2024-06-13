package one.nem.kidshift.utils.impl;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import one.nem.kidshift.utils.models.feature.FeatureFlagItemModel;
import one.nem.kidshift.utils.FeatureFlag;

public class FeatureFlagImpl implements FeatureFlag {

    private final Context applicationContext;

    private final SharedPreferences sharedPreferences;

    // ここを書き換えてプロファイルを書き換え
    private final Profile currentProfile = Profile.DEVELOP;

    enum Profile {
        DEVELOP("develop"),
        BETA("beta"),
        PRODUCTION("production");

        private final String name;

        Profile(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Inject
    public FeatureFlagImpl(@ApplicationContext Context applicationContext) {
        this.applicationContext = applicationContext;
        this.sharedPreferences = applicationContext.getSharedPreferences("feat_flg", Context.MODE_PRIVATE);
        init();
    }

    private void init() {
        initBase();
        switch (currentProfile) {
            case DEVELOP:
                initDevelop();
                break;
            case BETA:
                initBeta();
                break;
            case PRODUCTION:
                break;
        }
        restoreOverride();
    }

    private HashMap<String, FeatureFlagItemModel> featureFlagMap = new HashMap<>();

    // init
    private void initBase() { // ベース, (= Production)
        setFlag("isBaseEnabled", true, false);
        setFlag("isBetaEnabled", false, false);
        setFlag("isDevelopEnabled", false, false);
    }

    private void initBeta() { // 上書き
        setFlag("isBaseEnabled", false, true);
        setFlag("isBetaEnabled", true, true);
        setFlag("isDevelopEnabled", false, true);
    }

    private void initDevelop() { // 上書き
        setFlag("isBaseEnabled", false, true);
        setFlag("isBetaEnabled", false, true);
        setFlag("isDevelopEnabled", true, true);
    }

    // utils
    private void setFlag(String key, boolean defaultValue, boolean isOverrideAllowed) {
        featureFlagMap.put(key, new FeatureFlagItemModel(key, defaultValue, isOverrideAllowed));
    }

    // Restore override from shared preferences
    private void restoreOverride() {
        // 前回起動時からプロファイルが変わっている場合は、オーバーライドをリセット
        if (sharedPreferences.contains("last_profile") && !sharedPreferences.getString("last_profile", "").equals(currentProfile.getName())) {
            sharedPreferences.edit().clear().apply();
            sharedPreferences.edit().putString("last_profile", currentProfile.getName()).apply();
            return;
        }
        sharedPreferences.edit().putString("last_profile", currentProfile.getName()).apply();
        for (String key : featureFlagMap.keySet()) {
            Objects.requireNonNull(featureFlagMap.get(key))
                    .setValue(sharedPreferences.getBoolean(key, Objects.requireNonNull(featureFlagMap.get(key)).getValue()));
        }
    }

    @Override
    public Map<String, FeatureFlagItemModel> getFeatureFlagMap() {
        return null;
    }

    @Override
    public boolean isEnabled(String key) {
        return Objects.requireNonNull(featureFlagMap.get(key)).state();
    }

    @Override
    public void setOverride(String key, boolean value) throws IllegalArgumentException {
        // 存在するか, オーバーライド可能か
        if (!featureFlagMap.containsKey(key) || !Objects.requireNonNull(featureFlagMap.get(key)).getIsOverrideAllowed()) {
            throw new IllegalArgumentException("Invalid key or not allowed to override");
        }
        Objects.requireNonNull(featureFlagMap.get(key)).setValue(value);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public void resetOverride(String key) throws IllegalArgumentException {
        // 存在するか, オーバーライド可能か
        if (!featureFlagMap.containsKey(key) || !Objects.requireNonNull(featureFlagMap.get(key)).getIsOverrideAllowed()) {
            throw new IllegalArgumentException("Invalid key or not allowed to override");
        }
        Objects.requireNonNull(featureFlagMap.get(key)).setValue(Objects.requireNonNull(featureFlagMap.get(key)).getDefaultValue());
        sharedPreferences.edit().putBoolean(key, Objects.requireNonNull(featureFlagMap.get(key)).getDefaultValue()).apply();
    }

    @Override
    public void resetAllOverrides() {
        sharedPreferences.edit().clear().apply();
        init();
    }
}
