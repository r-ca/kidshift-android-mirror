package one.nem.kidshift.utils;

import java.util.Map;

import one.nem.kidshift.utils.models.feature.FeatureFlagItemModel;

public interface FeatureFlag {

    public boolean isEnabled(String key);

    public void setOverride(String key, boolean value) throws IllegalArgumentException;

    public void resetOverride(String key) throws IllegalArgumentException;

    public void resetAllOverrides();

    public Map<String, FeatureFlagItemModel> getFeatureFlagMap();
}
