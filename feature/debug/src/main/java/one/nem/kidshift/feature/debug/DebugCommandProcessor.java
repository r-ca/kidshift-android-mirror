package one.nem.kidshift.feature.debug;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.components.FragmentComponent;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.models.LogModel;

public class DebugCommandProcessor {

    KSLogger ksLogger;

    public DebugCommandProcessor(
            KSLogger ksLogger
    ) {
        this.ksLogger = ksLogger;
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
