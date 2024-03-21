package modulartelebot;

import modulartelebot.botmodules.GitRatio;
import modulartelebot.botmodules.Test;
import io.github.cdimascio.dotenv.Dotenv;
import modulartelebot.botmodules.Pirate;
import modulartelebot.botmodules.Test;

public class Main {
    public static void main(String[] args) {
        Bot bot = new Bot("gourpbot", getToken());
        bot.addModules(new BotModule[] {
            new Test(bot),
            new Pirate(bot),
            new GitRatio(bot),
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
