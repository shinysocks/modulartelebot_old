package modulartelebot;

import modulartelebot.botmodules.GitRatio;
import io.github.cdimascio.dotenv.Dotenv;
import modulartelebot.botmodules.Pirate;
import modulartelebot.botmodules.YTDL;
import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
				if(!Arrays.asList(new File("./").listFiles()).contains(new File("temp")))
					new File("./temp").mkdir();
        Bot bot = new Bot("gourpbot", getToken("TELEGRAM_TOKEN"));
        new Pirate(bot);
        new GitRatio(bot, getToken("CONVERT_API_TOKEN"));
				new YTDL(bot);
        // add more bots here
    }

    private static String getToken(String tokenKey) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get(tokenKey);
        if (token == null) {
            Log.log("cannot get token", Log.FLAVOR.ERR);
        }
        return token;
    }
}
