/*import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
*/
public class Test {

 /*   public static List<String> getHashtags(String text){
        List<String> hashtags = new ArrayList<String>();
        Pattern hashtag_pattern = Pattern.compile("#(\\S+)");
        Matcher mat = hashtag_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            hashtags.add(mat.group(0));
        }

        return hashtags;
    }

    public String removeHashtags(String text){
        String regex = "#(\\S+)";
        text = text.replaceAll(regex, "");
        return text;
    }

    public static List<String> getUrls(String text){
        List<String> urlsList = new ArrayList<String>();
        String regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern url_pattern = Pattern.compile(regex);
        Matcher mat = url_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            urlsList.add(mat.group(0));
        }

        return urlsList;
    }

    public static String removeUrls(String text){
        String regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        text = text.replaceAll(regex, "");
        Pattern url_pattern = Pattern.compile(regex);
        Matcher mat = url_pattern.matcher(text);
        int i=0;
        while (mat.find()) {
            text = text.replaceAll(mat.group(i),"").trim();
            i ++;
            System.out.println(mat.group(0));

        }

        return text;
    }


    public static List<String> getMentions(String text){
        List<String> mentions = new ArrayList<String>();
        Pattern mentions_pattern = Pattern.compile("@(\\S+)");
        Matcher mat = mentions_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            mentions.add(mat.group(0));
        }

        return mentions;
    }

    public String removeMentions(String text){
        String regex = "@(\\S+)";
        text = text.replaceAll(regex, "");
        return text;
    }

    public static List<String> getEmoctions(String text){
        List<String> emoctions = new ArrayList<String>();
        Pattern mentions_pattern = Pattern.compile("(?::|;|=)(?:-)?(?:\\)|D|P)");
        Matcher mat = mentions_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            emoctions.add(mat.group(0));
        }
        return emoctions;
    }

    public String removeEmoctions(String text){

        String regex = "[\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee]";
        text = text.replaceAll(regex, "");
        return text;
    }


    public static String removePunctuations(String text){
        text = text.replaceAll("[^a-zA-Z0-9 ]", "");
        return text;
    }
*/
    public static void main(String args[]){
        String text = "@SBS_MTV 컴백 #더쇼 오오오~ #딱좋아 너의 모든게 다 좋으니까 #GOT7 i want you ｡ﾟ( ﾟ^∀^ﾟ)ﾟ｡";
      //  String kaomoji = Utils.getKaomoji(text, "k.txt");
      //  text = Utils.removeUsingFileForKaomoji(text, "k.txt");
        String text_ko = text;
        String kaomoji = "";
        kaomoji = Utils.processKaomoji(text_ko);

//       System.out.println(getEmoctions(text).toString());
 //       System.out.println(getMentions(text).toString());
  //      System.out.println(getHashtags(text).toString());
   //     System.out.println(getUrls(text).toString());
    //    text = removeUrls(text);
      //  System.out.println(text);

        //text = removePunctuations(text);
        System.out.println(kaomoji);
        System.out.println(text);

    }
}
