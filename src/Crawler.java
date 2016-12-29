/* The main method
 * we get authentication and we use the other classes as needed
 * to extract the information on the tweets and the user
 */

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;

import java.io.IOException;

public class Crawler {
    private final static String CONSUMER_KEY = "29P9vRwxl0kYikeWJgAEiNVcC";
    private final static String CONSUMER_KEY_SECRET = "9C9YhGF4XZbD6cwiBszvqw9Q7FIBUrt27QndM6Eq6slrRgBSSK";
    private final static String accessTokenStr = "609964556-uCOo7mP5Olhd7VCZpsJw20Pq1Vw9ob7mLtQyfN2l";
    private final static String accessTokenSecretStr = "1sISLobbkeU74lSZxDdW6asHKTW6RKbiMl5KOXBisK5M6";
    static AccessToken accessToken = null;
    static Configuration configuration = null;

    public Crawler(){
    //    configuration();
    }

    /*public void configuration(){

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(CONSUMER_KEY);
        builder.setOAuthConsumerSecret(CONSUMER_KEY_SECRET);
        configuration = builder.build();
        accessToken = new AccessToken(accessTokenStr,accessTokenSecretStr);
        System.out.println("Access Token: " + accessToken.getToken());
        System.out.println("Access Token Secret: " + accessToken.getTokenSecret());
    }*/

    public static void main(String[] args) throws TwitterException, IOException {

        Crawler crawler = new Crawler();
        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
            }
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

            @Override
            public void onScrubGeo(long l, long l1) {
            }
            @Override
            public void onStallWarning(StallWarning stallWarning) {
            }
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
      //  TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();
       // twitterStream.setOAuthAccessToken(accessToken);
        //twitterStream.addListener(listener);

        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track("#got");
        filterQuery.language("english");
//        twitterStream.sample();
 //       twitterStream.filter(filterQuery);
    }
}