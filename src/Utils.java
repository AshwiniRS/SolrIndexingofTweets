import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static List<String> getHashtags(String text) {
        List<String> hashtags = new ArrayList<String>();
        Pattern hashtag_pattern = Pattern.compile("#(\\S+)");
        Matcher mat = hashtag_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            hashtags.add(mat.group(0));
        }

        return hashtags;
    }

    public static String removeHashtags(String text) {
        String regex = "#(\\S+)";
        text = text.replaceAll(regex, "");
        return text;
    }

    public static List<String> getUrls(String text) {
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

    public static String removeUrls(String text) {
        String regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        text = text.replaceAll(regex, "");
        return text;
    }

    public static List<String> getMentions(String text) {
        List<String> mentions = new ArrayList<String>();
        Pattern mentions_pattern = Pattern.compile("@(\\S+)");
        Matcher mat = mentions_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            mentions.add(mat.group(0));
        }
        return mentions;
    }

    public static String removeMentions(String text) {
        String regex = "@(\\S+)";
        text = text.replaceAll(regex, "");
        return text;
    }

    public static List<String> getEmoticons(String text) {
        List<String> emoticons = new ArrayList<String>();
        Pattern mentions_pattern = Pattern.compile("[\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee]");
        Matcher mat = mentions_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            emoticons.add(mat.group(0));
        }

        mentions_pattern = Pattern.compile("(?::|;|=)(?:-)?(?:\\)|D|P)");
        mat = mentions_pattern.matcher(text);
        while (mat.find()) {
            System.out.println(mat.group(0));
            emoticons.add(mat.group(0));
        }
        return emoticons;
    }

    public static String removeEmoticons(String text, String lang) {

        String regex = "[\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee]";
        text = text.replaceAll(regex, "");
        regex = "(?::|;|=)(?:-)?(?:\\)|D|P)";
        text = text.replaceAll(regex, "");
        if (lang.equalsIgnoreCase("ko") || lang.equalsIgnoreCase("es") || lang.equalsIgnoreCase("tr"))
            text = removeUsingFile(text, "kaomoji.txt");
        return text;
    }

    public static String processKaomoji(String text_ko) {
        String kaomoji = "";
        try {
            //Processing Kamojis
            BufferedReader inFile1 = new BufferedReader(new FileReader("kaomoji.txt"));
            String inputLine1 = inFile1.readLine();
            List<String> kamojilist = new ArrayList<String>();
            while (inputLine1 != null)

            {
                inputLine1 = inFile1.readLine();
                kamojilist.add(inputLine1);
            }

            String kamojis = "";
            for (String eachword : kamojilist) {
                if (eachword != null && text_ko.contains(eachword) == true) {
                    kaomoji = kaomoji + eachword;
                    text_ko = text_ko.replace(eachword, "");
                }
            }
            inFile1.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return kaomoji;
}

    public static String getKaomoji(String text, String file){
        BufferedReader br = null;
        //String line = "";
        String kaomoji = "";
        boolean b = false;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            do{
                line = br.readLine();
                if(text != null)
                b = text.toLowerCase().contains(line.toLowerCase());
                else
                    b = false;
                if (b)
                    kaomoji += line;
            }while(br.readLine() != null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kaomoji;
    }
    public static String removeUsingFileForKaomoji(String text, String file) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (br.readLine() != null) {
                line = br.readLine();
                boolean b = text.toLowerCase().contains(line.toLowerCase());
                if (b)
                    text = text.replace(line,"");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
    public static String removeUsingFile(String text, String file) {
        List<String> stopwordsList = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                line = br.readLine();
                stopwordsList.add(line);
            }
            String[] text_splited = text.split(" ");
            for(String word : text_splited){
                if(stopwordsList.contains(word.toLowerCase()))
                    text = text.replaceAll(word, "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedIOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String removePunctuations(String text){
        text = text.replaceAll("[^a-zA-Z0-9 ]", "");
        return text;
    }

    public static String removePunctuationsforOtherLanguages(String text){
        text = text.replaceAll("[!?.\"\",;':@#$%^&*~`]", "");
        return text;
    }
}
