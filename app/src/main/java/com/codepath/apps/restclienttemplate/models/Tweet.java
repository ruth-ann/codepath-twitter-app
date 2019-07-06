package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Parcel
public class Tweet {

    //attributes
    public String body;
    public long uid; //database ID for the tweet
    public User user;
    public String createdAt;
    public String relativeDate;
    public Integer retweets;
    public Integer likes;
    public Integer comments;
    public String imageUrl;
    public Boolean hasMedia;
    public Boolean isFavorited;
    public Boolean isRetweeted;

    public Tweet(){

    }

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        JSONObject entities;

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        Log.i("TwitterApp", jsonObject.toString());
        tweet.relativeDate = getRelativeTimeAgo(tweet.createdAt);
        tweet.likes = jsonObject.getInt("favorite_count");
        tweet.retweets = jsonObject.getInt("retweet_count");
        tweet.hasMedia = false;
        tweet.isFavorited = jsonObject.getBoolean("favorited");
        tweet.isRetweeted = jsonObject.getBoolean("retweeted");
        entities = jsonObject.getJSONObject("entities");
        if (entities.has("media")){
            tweet.hasMedia = true;
            JSONArray media;
            media = entities.getJSONArray("media");
            tweet.imageUrl = media.getJSONObject(0).getString("media_url");

        }
       // tweet.imageUrl = jsonObject.getS
       //ra tweet.comments = jsonObject.getString("reply_count");
        return tweet;



    }

   public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
