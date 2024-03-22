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
    private File tempDir;

    public Pirate(Bot bot) {
        super(bot);
        addCommand("/pirate");
        addCommand("open.spotify.com");
        tempDir = new File("./temp/pirate");
    }

    @Override
    public void update(String message, String chatId) throws TelegramApiException {
        String query = "";
        if (message.contains("/pirate")) {
            query = message.split("/pirate")[1].strip();
            send(new SendMessage(chatId, String.format("downloading '%s'...", query)));
        } else {
            String[] words = message.split(" ");
            for (String word : words) {
                if (word.contains("open.spotify.com")) {
                    send(new SendMessage(chatId, "found spotify link. downloading.."));
                    query = word;
                }
            }
        }

        // download
        try {
            download(query);
        } catch (Exception e) {
            send(new SendMessage(chatId, "failed to download, " + e.getMessage()));
            Log.log(String.format("failed to download '%s', %s", query, e.getMessage()), Log.FLAVOR.Err);
            return;
        }

        // send song;
        for (File f : tempDir.listFiles()) {
            send(new SendDocument(chatId, new InputFile(f)));
        }
        Log.log("cleared temporary directory", Log.FLAVOR.Info);
        clean();
    }

    private void download(String query) throws Exception {
        clean();
        String spotdl = String.format("./lib/spotdl-amd64 download '%s' --format mp3 --output %s", query, tempDir);
        // wait until download is complete
        new ProcessBuilder("/bin/bash", "-c", spotdl).start().onExit().get();
    }

    private void clean() {
        tempDir.delete();
    }

    @Override
    public void init() {}
}
