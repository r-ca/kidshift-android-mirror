package one.nem.kidshift.utils;

public interface KidShiftLogger {
    void getChildLogger(String tag);
    void info(String message);
    void warn(String message);
    void error(String message);
    void debug(String message);
    void trace(String message);
    void fatal(String message);
}
