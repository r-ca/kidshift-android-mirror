package one.nem.kidshift.feature.debug.model;

public class DebugCommandItemModel {
    String command;
    String result;

    // constructor
    public DebugCommandItemModel(String command, String result) {
        this.command = command;
        this.result = result;
    }

    // getter setter
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
