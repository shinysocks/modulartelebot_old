package gourpbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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

    public abstract SendMessage update(String message, String chatId);
}
