package gourpbot.botmodules;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import gourpbot.Bot;
import gourpbot.BotModule;

public class Test extends BotModule {
	public Test(Bot bot) {
		super(bot);
		setCommand("!test");
	}

	@Override
	public void update(String message, String chatId) throws TelegramApiException {
		send(new SendMessage(chatId, "testing"));
	}
}
