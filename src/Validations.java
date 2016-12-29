import org.apache.commons.lang3.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ashwini on 9/17/16
 */
public class Validations {

    public static String roundoff(String date){
        Date tweet_date = null;
        try {
            tweet_date = DateUtils.round((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(date)), Calendar.HOUR);
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(tweet_date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static void readJSON(String file){
        JSONParser parser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(file);
            Object obj = parser.parse(fileReader);
            JSONArray array = (JSONArray)obj;
            for(int i=0 ; i<= array.size() ; i++){
                JSONObject jo = (JSONObject) array.get(i);
                String date = jo.get("tweet_date").toString();
                String newDate = roundoff(date);
                jo.replace("tweet_date",date,newDate);
           }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

public static void main(String args[]){
    readJSON("tech_en.json");
}
}
