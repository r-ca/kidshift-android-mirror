package one.nem.kidshift.utils.models;

import java.util.Date;

import one.nem.kidshift.utils.enums.LogLevelEnum;

public class LogModel {
    private Date eventTime;
    private LogLevelEnum logLevel;
    private String[] tags;
    private String message;

    public LogModel(Date eventTime, LogLevelEnum logLevel, String[] tags, String message) {
        this.eventTime = eventTime;
        this.logLevel = logLevel;
        this.tags = tags;
        this.message = message;
    }

    public LogModel(LogLevelEnum logLevel, String[] tags, String message) {
        this.eventTime = new Date();
        this.logLevel = logLevel;
        this.tags = tags;
        this.message = message;
    }

    public LogModel() {
    }

    // Getters and Setters

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public LogLevelEnum getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevelEnum logLevel) {
        this.logLevel = logLevel;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
