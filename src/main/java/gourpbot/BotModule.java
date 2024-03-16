package gourpbot;

import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class BotModule {
	private String command;
	private Bot bot;

	public BotModule(Bot bot) {
	    this.bot = bot;
	}

    public void setCommand(String command) {
        this.command = command;
        Log.log("a new BotModule has been initialized with command '" + this.command + "'", Log.FLAVOR.Success);
    }

    public String getCommand() {
        return this.command;
    }

    public void send(SendMessage message) throws TelegramApiException { bot.execute(message); }
    public void send(SendPhoto message) throws TelegramApiException { bot.execute(message); }
    public void send(SendVideo message) throws TelegramApiException { bot.execute(message); }
    public void send(SendAudio message) throws TelegramApiException { bot.execute(message); }
    public void send(SendDocument message) throws TelegramApiException { bot.execute(message); }

    public abstract void update(String message, String chatId) throws TelegramApiException;
}
