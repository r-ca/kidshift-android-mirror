package one.nem.kidshift.utils.impl;

import static one.nem.kidshift.utils.enums.LogLevelEnum.INFO;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.SharedPrefUtils;
import one.nem.kidshift.utils.enums.LogLevelEnum;
import one.nem.kidshift.utils.factory.SharedPrefUtilsFactory;
import one.nem.kidshift.utils.models.LogModel;

public class KSLoggerImpl implements KSLogger {

    private ArrayList<String> tags = new ArrayList<String>();
    @AssistedInject
    public KSLoggerImpl(@Assisted String name) {
        tags.clear();
        tags.add(name);
    }

    @Override
    public KSLogger addTag(String tag) {
        tags.add(tag);
        return this;
    }

    @Override
    public void info(String message) {
        log(new LogModel(LogLevelEnum.INFO, tags.toArray(new String[0]), message));
    }

    @Override
    public void warn(String message) {
        log(new LogModel(LogLevelEnum.WARN, tags.toArray(new String[0]), message));
    }

    @Override
    public void error(String message) {
        log(new LogModel(LogLevelEnum.ERROR, tags.toArray(new String[0]), message));
    }

    @Override
    public void debug(String message) {
        log(new LogModel(LogLevelEnum.DEBUG, tags.toArray(new String[0]), message));
    }

    @Override
    public void trace(String message) {
        log(new LogModel(LogLevelEnum.TRACE, tags.toArray(new String[0]), message));
    }

    @Override
    public void fatal(String message) {
        log(new LogModel(LogLevelEnum.FATAL, tags.toArray(new String[0]), message));
    }

    private void log(LogModel log) {
        outputLog(log);
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
