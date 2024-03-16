package gourpbot;

import gourpbot.botModules.Pirate;

public class Main {
    private static final String TOKEN = "";

    public static void main(String[] args) {
        Bot bot = new Bot("gourpbot", TOKEN, new BotModule[] {new Pirate() });
    }
}
