package one.nem.kidshift.data.retrofit.model.child;

import java.util.Date;

// Response for detailed information about a child
public class ChildDetailsResponse extends ChildBaseItem {
    private Date createdAt;
    private String homeGroupId;

    // Getters and setters
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getHomeGroupId() {
        return homeGroupId;
    }

    public void setHomeGroupId(String homeGroupId) {
        this.homeGroupId = homeGroupId;
    }
}
