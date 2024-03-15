package gourpbot;

public abstract class BotModule {
    private String command;

    public BotModule() {
        setCommand("!command");
        Log.log("Bot Module initialized with command '" + this.command + "'", Log.FLAVOR.Success);
    }

    private void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    public abstract void update(String message, String chatId);
}
