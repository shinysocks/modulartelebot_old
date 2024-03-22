package modulartelebot;

import modulartelebot.botmodules.GitRatio;
import modulartelebot.botmodules.Test;
import io.github.cdimascio.dotenv.Dotenv;
import modulartelebot.botmodules.KasaAutomation;
import modulartelebot.botmodules.Pirate;

public class Main {
    public static void main(String[] args) {
        Bot bot = new Bot("gourpbot", getToken("TELEGRAM_TOKEN"));
        new Test(bot);
        new Pirate(bot);
        new GitRatio(bot, getToken("CONVERT_API_TOKEN"));
        // add more bots here
    }

    private static String getToken(String tokenKey) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get(tokenKey);
        if (token == null) {
            Log.log("cannot get token", Log.FLAVOR.Err);
        }
        return token;
    }
}
