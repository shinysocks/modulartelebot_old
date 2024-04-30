package modulartelebot;

import java.io.File;
import java.util.Arrays;

import io.github.cdimascio.dotenv.Dotenv;
import modulartelebot.botmodules.Pirate;

public class Main {
    public static void main(String[] args) {
        if (!Arrays.asList(new File("./").listFiles()).contains(new File("temp")))
            new File("./temp").mkdir();
        Bot bot = new Bot("gourpbot", getToken("TELEGRAM_TOKEN"));
        new Pirate(bot);
        // add more bots here
    }

    public static String getToken(String tokenKey) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get(tokenKey);
        if (token == null) {
            Log.log("cannot get token", Log.FLAVOR.ERR);
        }
        return token;
    }
}
