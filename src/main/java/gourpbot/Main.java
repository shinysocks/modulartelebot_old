package gourpbot;

public class Main {
    private final String TOKEN = "7080799985:AAGeGFxNDFYZ2ZB44sXjEqn0JSl9WY7rL58";

    public static void main(String[] args) {
        Bot bot = new Bot("", "", new BotModule[] { new BotModule("!go") });
        bot.run();
    }
}
