package one.nem.kidshift.feature.debug;

import java.util.Arrays;

public class DebugCommandProcessor {

    public DebugCommandProcessor() {
    }

    public String execute(String command) {
        try {
            return processCommand(command);
        } catch (InvalidCommandException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Something went wrong!";
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
            default:
                throw new InvalidCommandException();
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
