package gourpbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class BotModule {
    private String command;

    public void setCommand(String command) {
        this.command = command;
        Log.log("a new BotModule has been initialized with command '" + this.command + "'", Log.FLAVOR.Success);
    }

    public String getCommand() {
        return this.command;
    }

    public abstract SendMessage update(String message, String chatId);
}
