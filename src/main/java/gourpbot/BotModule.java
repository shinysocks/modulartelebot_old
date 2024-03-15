package gourpbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class BotModule {
    private String command;

    public BotModule(String command) {
        this.command = command;
        Log.log("Bot Module initialized with command '" + command + "'", Log.FLAVOR.Success);
    }

    public String getCommand() {
        return command;
    }

    private void send(SendMessage message, String id) {
        // String text = update.getMessage().getText();
        // message.setText("DARK BRANDON: " + reverse(text));
        // System.out.println(message.getText());
        // System.out.println(reverse("yuh"));

        // try {
        //     execute(message); // Call method to send the message
        // } catch (TelegramApiException e) {
        //     e.printStackTrace();
        // }

    }

    // public SendMessage update(String chatId) {

    // }
}
