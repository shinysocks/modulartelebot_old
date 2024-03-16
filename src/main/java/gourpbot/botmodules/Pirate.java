package gourpbot.botmodules;

import java.io.File;

import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import gourpbot.Bot;
import gourpbot.BotModule;
import gourpbot.Log;

public class Pirate extends BotModule {
    private String dir;
    private Bot bot;

    public Pirate(Bot bot) {
        super(bot);
        this.bot = bot;
        setCommand("/pirate");
        dir = "./src/main/java/gourpbot/botmodules/pirate/";
    }

    @Override
    public void update(String message, String chatId) throws TelegramApiException {
        String query = message.split(getCommand())[1].strip();
        send(new SendMessage(chatId, String.format("trying to download '%s'", query)));
        // download
        try {
            download(query);
        } catch (Exception e) {
            Log.log(String.format("failed to download '%s', %s", query, e.getMessage()), Log.FLAVOR.Err);
            return;
        }

        // send song and remove temp
        File tempDir = new File(dir + "temp");
        for (File f : tempDir.listFiles()) {
            SendAudio audioMessage = new SendAudio(chatId, new InputFile(f));
            audioMessage.setCaption(query);
            audioMessage.setPerformer(bot.getBotUsername());
            send(audioMessage);
            send(new SendDocument(chatId, new InputFile(f)));
            f.delete();
        }
        tempDir.delete();
    }

    private void download(String query) throws Exception {
        String spotdl = String.format("%sspotdl-amd64 '%s' --format ogg --output %s/temp", dir, query, dir);
        // wait until download is complete
        new ProcessBuilder("/bin/bash", "-c", spotdl).start().onExit().get();        
    }
}
