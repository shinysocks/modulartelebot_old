package gourpbot.botmodules;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import gourpbot.BotModule;

public class Test extends BotModule {
	public Test() {
		setCommand("!test");
	}

	@Override
	public SendMessage update(String message, String chatId) {
	    return new SendMessage(chatId, "testing");
	}
}
