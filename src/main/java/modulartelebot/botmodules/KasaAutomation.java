package modulartelebot.botmodules;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import modulartelebot.Bot;
import modulartelebot.BotModule;
import modulartelebot.Log;

/**
 * my custom automation bindings for the tp-link kasa smart bulb
 */

public class KasaAutomation extends BotModule {
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
            case "bedtime":

                break;
            case "ocean":

                break;
            case "ethereal":

                break;
            default:
                // default light state
                break;
        }
    }

    private void kasaCommand(String command) {
        try {
            run("kasa --type bulb --host $ip " + command);
        } catch(Exception e) {
            Log.log("could not run command", Log.FLAVOR.Err);
        }
    }

    private void status() {
        
    }

    private void run(String command) throws Exception {
        new ProcessBuilder("/bin/bash", "-c", command).start().onExit().get();        
    }

	@Override
	public void init() {
	    try {
    	    run("python -m venv ./temp/kasavenv");
    	    Log.log("initialized python virtual environment, installing python-kasa library..", Log.FLAVOR.Info);
    	    run("source ./temp/kasavenv/bin/activate ; pip install python-kasa");
        } catch (Exception e) {
            Log.log("unable to create python virtual environment and install python-kasa from pypi, is python installed?", Log.FLAVOR.Err);
        }
	}
}
