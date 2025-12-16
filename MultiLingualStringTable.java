package base;
/**
 * @author Kevan Buckley, maintained by __student
 * @version 2.0, 2014
 */

public class MultiLingualStringTable {
  private enum LanguageSetting {ENGLISH, KLINGON}
  private static LanguageSetting currentLanguage = LanguageSetting.ENGLISH;
  private static String [] englishMessages = {"Enter your name:", "Welcome", "Have a good time playing Abominodo"};
  private static String [] klingonMessages = {"'el lIj pong:", "nuqneH", "QaQ poH Abominodo"};
  
  public static String getMessage(int messageIndex){
    if(currentLanguage == LanguageSetting.ENGLISH){
      return englishMessages[messageIndex];
    } else {
      return klingonMessages[messageIndex];
    }
    
  }
}