package gourpbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SuppressWarnings("deprecation")
public class Bot extends TelegramLongPollingBot {
    private BotModule[] modules;
    private String name;
    private String token;

    public Bot(String name, String token, BotModule[] modules) {
        this.name = name;
        this.token = token;
        this.modules = modules;

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            Log.log("unable to register bot.. " + e.getMessage(), Log.FLAVOR.Err);
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String message = update.getMessage().getText();
            String id = update.getMessage().getChatId().toString();

            for (BotModule module : modules) {
                // new Thread(() -> module.update("boodle")).start();
            }
            
            // try {
            //     execute(message); // Call method to send the message
            // } catch (TelegramApiException e) {
            //     e.printStackTrace();
            // }
        }
    }
    
    private String reverse(String text) {
        char[] text_char = text.toCharArray();
        char[] rev = new char[text_char.length];
        for (int i = text_char.length; i >= 0; i--) {
            rev[i % text_char.length] = text_char[i];
        }
        System.out.println(rev.toString());
        return rev.toString();
    }

}
