package one.nem.kidshift.data.retrofit.model.child;

import java.util.Date;

// Response for detailed information about a child
public class ChildDetailsResponse extends ChildBaseItem {
    private Date createdAt;
    private String homeGroupId;

    // Constructor
    public ChildDetailsResponse(String id, String name, Date createdAt, String homeGroupId) {
        super(id, name);
        this.createdAt = createdAt;
        this.homeGroupId = homeGroupId;
    }

    public ChildDetailsResponse() {
    }

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
