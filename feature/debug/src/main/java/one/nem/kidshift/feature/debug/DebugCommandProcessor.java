package one.nem.kidshift.feature.debug;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.utils.FeatureFlag;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.models.LogModel;
import one.nem.kidshift.utils.models.feature.FeatureFlagItemModel;

public class DebugCommandProcessor {

    KSLogger ksLogger;
    FeatureFlag featureFlag;

    public DebugCommandProcessor(
            KSLogger ksLogger,
            FeatureFlag featureFlag
    ) {
        this.ksLogger = ksLogger;
        this.featureFlag = featureFlag;
    }

    public String execute(String command) {
        try {
            return processCommand(command);
        } catch (InvalidCommandException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Something went wrong! \n" + e.getMessage();
        }
    }

    private String processCommand(String command) throws Exception {
        // Parse to Array
        String[] commandArray = command.split(" ");

        // Check if command is valid
        switch (commandArray[0]) {
            case "ping":
                return "pong";
            case "echo":
                return executeEcho(commandArray);
            case "log":
                return executeLog(commandArray);
            case "flag":
                return executeFlag(commandArray);
            default:
                throw new InvalidCommandException();
        }
    }

    private String executeLog(String[] commandArray) {
        switch (commandArray[1]) {
            case "get":
                switch (commandArray[2]) {
                    case "all":
                        List<LogModel> logs = ksLogger.getHistory();
                        StringBuilder logString = new StringBuilder();
                        for (LogModel log : logs) {
                            logString.append(log.getMessage());
                            logString.append("\n");
                        }
                        return logString.toString();
                    default:
                        return "TODO";
                }
            case "insert":
                String[] logArray = Arrays.copyOfRange(commandArray, 3, commandArray.length);
                switch (commandArray[2]) {
                    case "info":
                        ksLogger.info(String.join(" ", logArray));
                        return "Log inserted";
                    case "warn":
                        ksLogger.warn(String.join(" ", logArray));
                        return "Log inserted";
                    case "error":
                        ksLogger.error(String.join(" ", logArray));
                        return "Log inserted";
                    case "debug":
                        ksLogger.debug(String.join(" ", logArray));
                        return "Log inserted";
                    case "trace":
                        ksLogger.trace(String.join(" ", logArray));
                        return "Log inserted";
                    case "fatal":
                        ksLogger.fatal(String.join(" ", logArray));
                        return "Log inserted";
                    default:
                        return "TODO";
                }
            default:
                return "TODO";
        }
    }

    private String executeFlag(String[] commandArray) {
        switch (commandArray[1]) {
            case "get":
                if (commandArray.length == 3) {
                    FeatureFlagItemModel featureFlagItemModel = featureFlag.getFeatureFlagMap().get(commandArray[2]);
                    return makeFeatureFlagResponse(featureFlagItemModel);
                } else {
                    if (commandArray[2].equals("all")) {
                        StringBuilder featureFlagString = new StringBuilder();
                        for (FeatureFlagItemModel featureFlagItemModel : featureFlag.getFeatureFlagMap().values()) {
                            featureFlagString.append(makeFeatureFlagResponse(featureFlagItemModel));
                            featureFlagString.append("\n");
                        }
                        return featureFlagString.toString();
                    } else {
                        return "TODO";
                    }
                }
            case "set":
                if (commandArray.length == 5) {
                    try {
                        boolean value = Boolean.parseBoolean(commandArray[4]);
                        featureFlag.getFeatureFlagMap().get(commandArray[2]).setValue(value);
                    } catch (IllegalArgumentException e) {
                        return e.getMessage();
                    } catch (NullPointerException e) {
                        return "Feature Flag not found";
                    } catch (Exception e) {
                        return "Something went wrong! \n" + e.getMessage();
                    }
                    return "Success";
                } else {
                    return "TODO";
                }
            case "reset":
                if (commandArray.length == 3) {
                    featureFlag.getFeatureFlagMap().get(commandArray[2]).setValue(featureFlag.getFeatureFlagMap().get(commandArray[2]).getDefaultValue());
                    return "Success";
                } else {
                    return "TODO";
                }
            default:
                // debug
                if (this.featureFlag == null) {
                    return "Feature Flag is null";
                } else {
                    return "Feature Flag is not null";
                }
        }
    }

    private String makeFeatureFlagResponse(FeatureFlagItemModel featureFlagItemModel) {
        return "Key: " + featureFlagItemModel.getKey() + "\n" +
                "\tValue: " + featureFlagItemModel.getValue() + "\n" +
                "\tDefault Value: " + featureFlagItemModel.getDefaultValue() + "\n" +
                "\tIs Override Allowed: " + featureFlagItemModel.getIsOverrideAllowed();
    }

    private String executeEcho(String[] commandArray) {
        String[] echoArray = Arrays.copyOfRange(commandArray, 1, commandArray.length);
        return String.join(" ", echoArray);
    }

    // Exceptions
    private static class InvalidCommandException extends Exception {
        public InvalidCommandException() {
            super("Invalid Command");
        }
    }
}
