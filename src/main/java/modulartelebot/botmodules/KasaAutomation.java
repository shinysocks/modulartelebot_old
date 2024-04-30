package modulartelebot.botmodules;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import modulartelebot.Bot;
import modulartelebot.BotModule;
import modulartelebot.Log;
import modulartelebot.Main;

/**
 * my custom bindings for the tp-link kasa smart bulb
 */

public class KasaAutomation extends BotModule {
    private String ip;
    public KasaAutomation(Bot bot) {
        super(bot);
        addCommand("/bulb");
    }

    @Override
    public void update(String message, String chatId) throws TelegramApiException {
        String[] words = message.split(" ");
        String subCommand = words[1];
        switch (subCommand) {
            case "off":
                kasaCommand("off");
                break;
            case "status":
                status();
                break;
            case "ocean":
                kasaCommand("hsv 230 100 100");
                break;
            case "":
                // default light state
                kasaCommand("hsv 57 100 100");
                break;
            default:
                break;
        }
    }

    private void kasaCommand(String command) {
        try {
            run(String.format("source ./temp/kasavenv/activate ; kasa --type bulb --host %s %s", ip, command));
        } catch(Exception e) {
            Log.log("could not run command", Log.FLAVOR.ERR);
        }
    }

    private void status() {
        
    }

    private void run(String command) throws Exception {
        Process p = new ProcessBuilder("/bin/bash", "-c", command).start().onExit().get();
    }

	@Override
	public void init() {
	    try {
    	    run("python -m venv ./temp/kasavenv");
    	    Log.log("initialized python virtual environment, installing python-kasa library..", Log.FLAVOR.INFO);
    	    run("source ./temp/kasavenv/bin/activate ; pip install python-kasa");
        } catch (Exception e) {
            Log.log("unable to create python virtual environment and install python-kasa from pypi, is python installed?", Log.FLAVOR.ERR);
        }
        this.ip = Main.getToken("BULB_IP");
    }
}
