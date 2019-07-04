package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private SwipeRefreshLayout swipeContainer;
    private final int REQUEST_CODE = 40;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        client = TwitterApp.getRestClient(this);

        //find the RecyclerView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        //init the arrayList(data source)
        tweets = new ArrayList<>();
        //construct the adapter from this datasource
        tweetAdapter = new TweetAdapter(tweets);
        //RecyclerView setup (layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        //set the adapter
        rvTweets.setAdapter(tweetAdapter);

        populateTimeline();

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweets, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        //open composeActivity
       Intent composeTweet = new Intent (TimelineActivity.this, ComposeActivity.class);
       startActivityForResult(composeTweet, REQUEST_CODE); //rename this
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent newTweet) {
        // REQUEST_CODE is defined above



        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) { //switch these two, rename compose tweet request code

            Tweet resultTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("new_tweet"));
            tweets.add(0, resultTweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);

            // Toast the name to display temporarily on screen
            Toast.makeText(this, "Tweet successfully made!", Toast.LENGTH_SHORT).show();
        }
    }



    private void populateTimeline(){
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClient", response.toString());
                Toast.makeText(TimelineActivity.this, "got array", Toast.LENGTH_LONG).show();

                //iterate through the JSON array
                // for each entry, deserializer the JSON object
                for (int i = 0; i < response.length(); i++) {
                    //convert each object to a Tweet model
                    //add that Tweet model to our data source
                    //notify the adapter that we've added an item
                    Toast.makeText(TimelineActivity.this, "got in for loop", Toast.LENGTH_LONG).show();
                    try{
                        JSONObject single = response.getJSONObject(i);
                        Tweet tweet = Tweet.fromJSON(single);
                        tweets.add(tweet);
                        Toast.makeText(TimelineActivity.this, "Tweet successfully populated!", Toast.LENGTH_LONG).show();
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    }catch (JSONException e){
                        Toast.makeText(TimelineActivity.this, "got in for catch", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);

                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());


                throwable.printStackTrace();            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());


                throwable.printStackTrace();
            }
        });


    }


    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                // Remember to CLEAR OUT old items before appending in the new ones
                tweetAdapter.clear();
                // ...the data has come back, add new items to your adapter...
                populateTimeline();
                Log.d("RefreshResponse", response.toString());
               // tweetAdapter.addAll(response);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }


            public void onFailure(Throwable e) {
                Log.d("Timeline fetch error", "Fetch timeline error: " + e.toString());
            }
        });
    }
}


