package modulartelebot;

import io.github.cdimascio.dotenv.Dotenv;
import modulartelebot.botmodules.KasaAutomation;
import modulartelebot.botmodules.Pirate;

public class Main {
    public static void main(String[] args) {
        Bot bot = new Bot("gourpbot", getToken());

        // add modules here:
        new Pirate(bot);
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
