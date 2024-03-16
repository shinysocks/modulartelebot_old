package gourpbot.botModules;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import gourpbot.BotModule;

public class Pirate extends BotModule {
	public Pirate() {
		setCommand("!yo");
	}

	@Override
	public SendMessage update(String message, String chatId) {
	    return new SendMessage(chatId, "test");
	}
}
