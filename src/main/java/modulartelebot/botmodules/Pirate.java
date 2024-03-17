package modulartelebot.botmodules;

import java.io.File;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import modulartelebot.Bot;
import modulartelebot.BotModule;
import modulartelebot.Log;

public class Pirate extends BotModule {
    private String dir;

    public Pirate(Bot bot) {
        super(bot);
        setCommand("/pirate");
        dir = "./src/main/java/modulartelebot/botmodules/pirate/";
    }

    @Override
    public void update(String message, String chatId) throws TelegramApiException {
        String query = message.split(getCommand())[1].strip();
        send(new SendMessage(chatId, String.format("trying to download '%s'...", query)));
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
            send(new SendDocument(chatId, new InputFile(f)));
            f.delete();
        }
        tempDir.delete();
    }

    private void download(String query) throws Exception {
        String spotdl = String.format("%sspotdl-amd64 '%s' --format mp3 --output %s/temp", dir, query, dir);
        // wait until download is complete
        new ProcessBuilder("/bin/bash", "-c", spotdl).start().onExit().get();        
    }
}
