package one.nem.kidshift.feature.debug.model;

public class DebugMenuListItemModel {
    private String title;
    private String description;
    private int destinationId; // Navigation destination ID
    private boolean enabled;

    public DebugMenuListItemModel(String title, String description, int destinationId, boolean enabled) {
        this.title = title;
        this.description = description;
        this.destinationId = destinationId;
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
