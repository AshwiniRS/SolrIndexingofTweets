import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CrawlerRestAPI {
    private final static String CONSUMER_KEY = "29P9vRwxl0kYikeWJgAEiNVcC";
    private final static String CONSUMER_KEY_SECRET = "9C9YhGF4XZbD6cwiBszvqw9Q7FIBUrt27QndM6Eq6slrRgBSSK";
    private final static String accessTokenStr = "609964556-uCOo7mP5Olhd7VCZpsJw20Pq1Vw9ob7mLtQyfN2l";
    private final static String accessTokenSecretStr = "1sISLobbkeU74lSZxDdW6asHKTW6RKbiMl5KOXBisK5M6";

    static AccessToken accessToken = null;
    static Configuration configuration = null;
    static int count = 0;

    public CrawlerRestAPI() {
        configuration();
    }

    public void configuration() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(CONSUMER_KEY);
        builder.setOAuthConsumerSecret(CONSUMER_KEY_SECRET);
        configuration = builder.build();
        accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);
    }

    public String removeEverything(String text, CrawlerRestAPI crawler, String lang){

        text = Utils.removeHashtags(text);
        text = Utils.removeEmoticons(text,lang);
        text = Utils.removeMentions(text);
        text = Utils.removeUrls(text);
        if(lang.equalsIgnoreCase("en")) {
            text = Utils.removeUsingFile(text, "stopwords_en.txt");
            text = Utils.removePunctuations(text);
        }
        else if(lang.equalsIgnoreCase("ko")) {
            text = Utils.removeUsingFile(text, "stopwords_ko.txt");
            text = Utils.removePunctuationsforOtherLanguages(text);
          //  text = Utils.removeUsingFileForKaomoji(text,"kaomoji.txt");
        }
        else if(lang.equalsIgnoreCase("tr")) {
            text = Utils.removeUsingFile(text, "stopwords_tr.txt");
            text = Utils.removePunctuationsforOtherLanguages(text);
        }
        else if(lang.equalsIgnoreCase("es")) {
            text = Utils.removeUsingFile(text, "stopwords_es.txt");
            text = Utils.removePunctuationsforOtherLanguages(text);
        }
        return text;
    }

    public static void main(String[] args) throws TwitterException, IOException {

        CrawlerRestAPI crawler = new CrawlerRestAPI();
        TwitterFactory factory = new TwitterFactory(configuration);
        Twitter twitter = factory.getInstance();
        twitter.setOAuthAccessToken(accessToken);

        Calendar calendar = new GregorianCalendar(2016,8,15);
        System.out.println(calendar.getTime().toString());
        String sinceDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        System.out.println(sinceDate);

        calendar = new GregorianCalendar(2016,8,19);
        System.out.println(calendar.getTime().toString());
        String untilDate = new SimpleDateFormat("yyyay-MM-dd").format(calendar.getTime());
        System.out.println(untilDate);

        Query query = new Query("#iPhone7 OR #iphone7 OR #appleevent OR #IPHONE7 OR #iphone 7 +exclude:retweets");
       // Query query = new Query("\"(#gameofthrones7)\" OR \"(#gameofthrones)\" OR \"(#JonSnow)\" OR \"(#winteriscoming)\" OR \"(GOTSeason7)\" OR \"(#GOT7)\" +exclude:retweets");
     //   Query query = new Query("\"(#USPresidentialElections)\" OR \"(#US Presidential Elections)\" OR \"(#DonaldTrump)\" OR \"(#HillaryClinton)\" OR \"(USpresidentialpolls2016)\" +exclude:retweets");
     //   Query query = new Query("\"(#usopen)\" OR \"(#stanwawrinka)\" OR \"(#wawrinka)\"");
        //Query query = new Query("\"(#syria)\" OR \"(#SyriaCivilWar)\" OR \"(#CivilWar)\" +exclude:retweets");  //OR
      //  Query query = new Query("\"(syria-n civil war)\" OR \"(phase in Syria's civil war)\" OR \"(#civil)\" OR \"(#war)\" +exclude:retweets\"");
     //  Query query = new Query("\"(#USPresidenti alElections)\" OR \"(#US Presidential Elections)\" OR \"(#DonaldTrump)\" OR \"(#HillaryClinton)\" OR \"(USpresidentialpolls2016)\" +exclude:retweets");        query.setSince(sinceDate);
     //   Query query = new Query("\"(#peace for syria)\" OR \"(#ISIS)\" OR \"(syrian soldiers)\"");
        query.setUntil(untilDate);
        query.setLang("ko");
        query.setCount(100);
        System.out.println("Query : " +query.toString());

        QueryResult result;
        FileWriter file = new FileWriter("sports_koupdatedjson",true);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        do{
                System.out.println("  ***** COUNT  **** " + count);
                result = twitter.search(query);
                if(result.getRateLimitStatus().getRemaining() == 0){
                   int sleeptime = result.getRateLimitStatus().getSecondsUntilReset();
                    System.out.println("Rate limit exceeded so making thread to sleep for "+sleeptime+" seconds");
                    try {
                        Thread.sleep(sleeptime * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Tweets Count : " + result.getCount());
                List<Status> tweets = result.getTweets();
                for (Status statusOftweet : tweets) {
                    Tweet tweet = new Tweet();
                    tweet.tweet_text = statusOftweet.getText();
                    tweet.topic = "Politics";
                    tweet.tweet_lang = statusOftweet.getLang();
                    Date tweet_date = DateUtils.round(statusOftweet.getCreatedAt() , Calendar.HOUR);
                    tweet.tweet_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(tweet_date).toString();

                    List<String> hashtags = Utils.getHashtags(tweet.tweet_text);
                    tweet.hashtags = hashtags.toString();

                    List<String> mentions = Utils.getMentions(tweet.tweet_text);
                    tweet.mentions = mentions.toString();

                    List<String> urlList = Utils.getUrls(tweet.tweet_text);
                    tweet.tweet_urls = urlList.toString();

                    List<String> emoticons = Utils.getEmoticons(tweet.tweet_text);
                    tweet.tweet_emoctions = emoticons.toString();

           /*         if(tweet.tweet_lang.equalsIgnoreCase("ko")){
                        String kaomoji = Utils.getKaomoji(tweet.tweet_text,"kaomoji.txt");
                        tweet.tweet_emoctions += kaomoji;
                    } */

                    if(tweet.tweet_lang.equalsIgnoreCase("en")){
                        tweet.ttext_en = tweet.tweet_text;
                        tweet.ttext_en = crawler.removeEverything(tweet.ttext_en, crawler, "en").trim().replaceAll(" +"," ");
                    }else if(tweet.tweet_lang.equalsIgnoreCase("ko")){
                        tweet.ttext_ko = tweet.tweet_text;
                        tweet.ttext_ko = crawler.removeEverything(tweet.ttext_ko, crawler, "ko").trim();
                        String kaomoji = Utils.processKaomoji(tweet.ttext_ko);
                        tweet.tweet_emoctions +=kaomoji;
                        tweet.ttext_ko = tweet.ttext_ko.replace(kaomoji, "");
                    }else if(tweet.tweet_lang.equalsIgnoreCase("es")){
                        tweet.ttext_es = tweet.tweet_text;
                        tweet.ttext_es = crawler.removeEverything(tweet.ttext_es, crawler, "es").trim();
                    }else if(tweet.tweet_lang.equalsIgnoreCase("tr")){
                        tweet.ttext_tr = tweet.tweet_text;
                        tweet.ttext_tr = crawler.removeEverything(tweet.ttext_tr, crawler, "tr").trim();
                    }
                    tweet.tweet_loc = new Double[2];
                    if(null != statusOftweet.getGeoLocation()) {
                        tweet.tweet_loc[0] = statusOftweet.getGeoLocation().getLatitude();
                    }
                    if(null != statusOftweet.getGeoLocation()) {
                        tweet.tweet_loc[1] = statusOftweet.getGeoLocation().getLongitude();
                    }

                    try {
                        System.out.println(JSONObject.valueToString(tweet));
                        String json = ow.writeValueAsString(tweet);
                        file.append(json);
                    } catch (JSONException e) {
                            e.printStackTrace();
                    }
                    count++;
                    System.out.println(statusOftweet);

                }

            } while ((query = result.nextQuery()) != null);

        System.out.println("#################COUNT : " + count + "######################");
        file.close();
    }
}