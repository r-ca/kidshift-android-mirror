package one.nem.kidshift.utils.models.feature;

public class FeatureFlagItemModel {
    private String key;
    private boolean value;
    private boolean defaultValue;
    private boolean isOverrideAllowed;

    public FeatureFlagItemModel(String key, boolean value, boolean defaultValue, boolean isOverrideAllowed) {
        this.key = key;
        this.value = value;
        this.defaultValue = defaultValue;
        this.isOverrideAllowed = isOverrideAllowed;
    }

    public FeatureFlagItemModel(String key, boolean defaultValue, boolean isOverrideAllowed) {
        this.key = key;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.isOverrideAllowed = isOverrideAllowed;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean getIsOverrideAllowed() {
        return isOverrideAllowed;
    }

    public void setIsOverrideAllowed(boolean isOverrideAllowed) {
        this.isOverrideAllowed = isOverrideAllowed;
    }

    public boolean state() {
        return isOverrideAllowed ? value : defaultValue;
    }
}
