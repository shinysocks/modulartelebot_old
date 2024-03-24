package modulartelebot.botmodules;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;

import modulartelebot.Bot;
import modulartelebot.BotModule;
import modulartelebot.Log;

public class GitRatio extends BotModule {
  private String[] messageArgs;
  private final String githubChartApiURL = "https://ghchart.rshah.org/";
  private File tempDir = new File("./temp/ratio-files");
  
  public GitRatio(Bot bot, String convertApiToken){
    super(bot);
    Config.setDefaultSecret(convertApiToken);
		tempDir.mkdir();
    addCommand("/ratio");
  }

  private File getGitActivity(URL url, String fileName) throws IOException {
		try {
			File convertedImage = ConvertApi.convert("svg", "png", new Param("File", url.toString())).get().saveFile(Path.of(tempDir.getCanonicalPath())).get().toFile();
			convertedImage.renameTo(new File(tempDir.getCanonicalPath()+"/"+fileName));
		} catch (InterruptedException | ExecutionException e) {
			Log.log("An exception has occurred: "+e, Log.FLAVOR.ERR);
		}
		return new File(tempDir.getCanonicalPath()+"/"+fileName);
  }

  private void deleteTempDirectory(){
		File[] tempContents = tempDir.listFiles();
    if(tempContents.length > 0){
			for(File f : tempContents){
				f.delete();
			}
		}
  }

  @Override
	public void update(String message, String chatId) throws TelegramApiException{
		messageArgs = message.strip().split(" ");
		if(messageArgs.length != 3){
			send(new SendMessage(chatId, "Err: Invalid number of arguments provided to command"));
		}else{
			try {
				SendPhoto userStatsOnePhoto = new SendPhoto();
				SendPhoto userStatsTwoPhoto = new SendPhoto();
				userStatsOnePhoto.setChatId(chatId);
				userStatsTwoPhoto.setChatId(chatId);
				URL userStatsOne = new URL(String.format("%s/%s", githubChartApiURL, messageArgs[1]));
				URL userStatsTwo = new URL(String.format("%s/%s", githubChartApiURL, messageArgs[2]));
				File fileOne = getGitActivity(userStatsOne, "firstdev.png");
				File fileTwo = getGitActivity(userStatsTwo, "seconddev.png");
				userStatsOnePhoto.setPhoto(new InputFile(fileOne));
				userStatsTwoPhoto.setPhoto(new InputFile(fileTwo));
				send(new SendMessage(chatId, messageArgs[1]+"'s Git Activity"));
				send(userStatsOnePhoto);
				send(new SendMessage(chatId, messageArgs[2]+"'s Git Activity"));
				send(userStatsTwoPhoto);
				send(new SendMessage(chatId, "HAHA Soy Dev"));
				deleteTempDirectory();
			} catch (IOException e) {
				Log.log("An error has occurred while handling file io "+e, Log.FLAVOR.ERR);
			}
		}
	}
  

  @Override
  public void init() {}
}
