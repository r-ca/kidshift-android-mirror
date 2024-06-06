package one.nem.kidshift.utils.impl;

import static one.nem.kidshift.utils.enums.LogLevelEnum.INFO;

import android.util.Log;

import java.util.ArrayList;

import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.enums.LogLevelEnum;
import one.nem.kidshift.utils.models.LogModel;

public class KSLoggerImpl implements KSLogger {

    private ArrayList<LogModel> logs = new ArrayList<LogModel>();

    private ArrayList<String> tags = new ArrayList<String>();

    public KSLoggerImpl() {
        tags.add("UNTAGGED");
    }

    public KSLoggerImpl(String tag) {
        tags.add(tag);
    }

    @Override
    public KSLogger getChildLogger(String tag) {
        tags.add(tag);
        return this;
    }

    @Override
    public KSLogger get(String tag) {
        return new KSLoggerImpl(tag);
    }

    @Override
    public void info(String message) {
        log(new LogModel(LogLevelEnum.INFO, new String[]{}, message));
    }

    @Override
    public void warn(String message) {
        log(new LogModel(LogLevelEnum.WARN, new String[]{}, message));
    }

    @Override
    public void error(String message) {
        log(new LogModel(LogLevelEnum.ERROR, new String[]{}, message));
    }

    @Override
    public void debug(String message) {
        log(new LogModel(LogLevelEnum.DEBUG, new String[]{}, message));
    }

    @Override
    public void trace(String message) {
        log(new LogModel(LogLevelEnum.TRACE, new String[]{}, message));
    }

    @Override
    public void fatal(String message) {
        log(new LogModel(LogLevelEnum.FATAL, new String[]{}, message));
    }

    private void log(LogModel log) {
        addLog(log);
        outputLog(log);
    }

    private void addLog(LogModel log) {
        logs.add(log);
    }

    private void outputLog(LogModel log) {
        // ,区切りで出力
        String tags = log.getTags().length > 0 ? String.join(",", log.getTags()) : "UNTAGGED";

        LogLevelEnum level = log.getLogLevel();
        String message = log.getMessage();

        switch (level) {
            case INFO:
                Log.i(tags, message);
                break;
            case WARN:
                Log.w(tags, message);
                break;
            case ERROR:
                Log.e(tags, message);
                break;
            case DEBUG:
                Log.d(tags, message);
                break;
            case TRACE:
                Log.v(tags, message);
                break;
            case FATAL:
                Log.wtf(tags, message);
                break;
            default:
                Log.i(tags, message);
                break;
        }
    }
}
