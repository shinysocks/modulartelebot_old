package gourpbot;

import gourpbot.botmodules.Pirate;
import gourpbot.botmodules.Test;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Bot bot = new Bot("gourpbot", getToken());
        bot.addModules(new BotModule[] {
            new Test(bot),
            new Pirate(bot),
            // add more bots here
        });
    }

    private static String getToken() {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TELEGRAM_TOKEN");
        if (token == null) {
            Log.log("cannot get telegram token", Log.FLAVOR.Err);
        }
        return token;
    }
}
