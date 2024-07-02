package one.nem.kidshift.utils;

import java.util.List;

import one.nem.kidshift.utils.models.LogModel;

public interface KSLogger {
    KSLogger addTag(String tag);
    List<LogModel> getHistory();
    void info(String message);
    void warn(String message);
    void error(String message);
    void debug(String message);
    void trace(String message);
    void fatal(String message);
}
