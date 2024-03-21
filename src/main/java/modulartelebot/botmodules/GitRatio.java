package modulartelebot.botmodules;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

import modulartelebot.Bot;
import modulartelebot.BotModule;

public class GitRatio extends BotModule {
  private String[] messageArgs;
  private String baseURL = "https://ghchart.rshah.org/";
  
  public GitRatio(Bot bot){
    super(bot);
    setCommand("!ratio");
  }

  public File getFileFromURL(URL remoteFileURL, String fileName){
    File userStats = new File(fileName+".svg");
    try {
      ReadableByteChannel readableByteChannel = Channels.newChannel(remoteFileURL.openStream());
      FileOutputStream fileOutputStream = new FileOutputStream(userStats);
      FileChannel fileChannel = fileOutputStream.getChannel();
      fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
      fileOutputStream.close();
    } catch (IOException e) {
      System.err.println("An exception has occurred: "+e);
    }
    return userStats;
  }

  public File convertSVGToPNG(File svgFile){
    return svgFile;
  }


  @Override
  public void update(String message, String chatId) throws TelegramApiException{
    messageArgs = message.split(" ");
    if(messageArgs.length < 3){
      send(new SendMessage(chatId, "ERR: Too few arguments provided to command!"));
    }else if(messageArgs.length > 3){
      send(new SendMessage(chatId, "ERR: Too many arguments provided to command!"));
    }else{
      try {
        URL userStatsOne = new URL(String.format("%s/%s", baseURL, messageArgs[1]));
        URL userStatsTwo = new URL(String.format("%s/%s", baseURL, messageArgs[2]));
        SendPhoto userStatsOnePhoto = new SendPhoto();
        userStatsOnePhoto.setChatId(chatId);
        userStatsOnePhoto.setPhoto(new InputFile(getFileFromURL(userStatsOne, "firstdev")));
        SendPhoto userStatsTwoPhoto = new SendPhoto();
        userStatsTwoPhoto.setChatId(chatId);
        userStatsTwoPhoto.setPhoto(new InputFile(getFileFromURL(userStatsTwo, "seconddev")));
        send(userStatsOnePhoto);
        send(userStatsTwoPhoto);
        send(new SendMessage(chatId, "Get Ratio'd"));
      } catch (MalformedURLException e) {
        System.err.println("An exception has occurred: "+e);
      }
    }
  }
    
  

}
