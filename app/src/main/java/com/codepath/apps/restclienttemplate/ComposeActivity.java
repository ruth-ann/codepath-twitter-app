package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public static final String RESULT_TWEET_KEY = "result_tweet";
    private TwitterClient client;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApp.getRestClient(this);
        setContentView(R.layout.activity_compose);
    }

    public void onSubmit(View composeView) {
        EditText etName = (EditText) findViewById(R.id.etNewTweet);
        // Prepare data intent
        Intent intent = new Intent();
        // Pass relevant data back as a result
        String enteredTweet = etName.getText().toString();
        String newTweet = enteredTweet;
        username = getIntent().getExtras().getString("user_name");
        if (username.length() != 0){
            newTweet = "@" + username + " " + enteredTweet;
        }else{
            newTweet = enteredTweet;
        }

        intent.putExtra("etNewTweet", newTweet);
        intent.putExtra("code", 200); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, intent); // set result code and bundle data for response

        makeTweet(newTweet);


        finish(); // closes the activity, pass data to parent
    }


    //function sends tweet through the TwitterClient
    private void makeTweet(String tweetText){
        client.sendTweet(tweetText, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    //return result to calling activity
                    Intent resultData = new Intent();
                    resultData.putExtra("new_tweet", Parcels.wrap(tweet));
                    setResult(RESULT_OK, resultData);
                    finish();

                } catch (JSONException e) {
                    Log.e("ComposeActivity", "Error parsing response", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString.toString());
                throwable.printStackTrace();
            }

        });

    }
}
