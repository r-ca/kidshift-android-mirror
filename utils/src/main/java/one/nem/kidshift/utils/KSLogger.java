package one.nem.kidshift.utils;

public interface KSLogger {
    KSLogger getChildLogger(String tag);
    KSLogger get(String tag);
    KSLogger setTag(String tag);
    KSLogger addTag(String tag);
    void info(String message);
    void warn(String message);
    void error(String message);
    void debug(String message);
    void trace(String message);
    void fatal(String message);
}
