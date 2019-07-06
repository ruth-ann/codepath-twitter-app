package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    public final static String TAG = "TweetAdapter";
    //not static
    private  List<Tweet> mTweets;
    //not static
     Context context;
     TwitterClient client;
    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;

    }
    //for each row, inflate the layout and cache references into ViewHolder

    //this method is invoked whenever a new view needs to be created, inflates the layout and connects the objects
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        client = TwitterApp.getRestClient(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }


    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //get the data according to position
        final Tweet tweet =  mTweets.get(position);

        //populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeStamp.setText(tweet.relativeDate);
        holder.tvLikes.setText(tweet.likes.toString());
        holder.tvRetweets.setText(tweet.retweets.toString());
        if (tweet.isRetweeted){
            holder.ibRetweet.setImageResource(R.drawable.ic_vector_retweet);
        }else{
            holder.ibRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
        }

        if (tweet.isFavorited){
            holder.ibLike.setImageResource(R.drawable.ic_vector_heart);
        }else{
            holder.ibLike.setImageResource(R.drawable.ic_vector_heart_stroke);
        }

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .transform(new CircleTransform(context))
                .into(holder.ivProfileImage);

        if (tweet.hasMedia){
            Glide.with(context)
                    .load(tweet.imageUrl)
                    .into(holder.ivTweetImage);

            Toast.makeText(context, tweet.imageUrl, Toast.LENGTH_LONG).show();

        }else{
            holder.ivTweetImage.setVisibility(View.GONE);
        }

        holder.ibLike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (tweet.isFavorited){
                    client.unFavoriteTweet(tweet.uid, new JsonHttpResponseHandler());
                    holder.ibLike.setImageResource(R.drawable.ic_vector_heart_stroke);
                    tweet.isFavorited = false;
                    tweet.likes--;
                    holder.tvLikes.setText(Integer.toString(tweet.likes));

                }else{
                    client.favoriteTweet(tweet.uid, new JsonHttpResponseHandler());
                    holder.ibLike.setImageResource(R.drawable.ic_vector_heart);
                    tweet.isFavorited = true;
                    tweet.likes++;
                    holder.tvLikes.setText(Integer.toString(tweet.likes));
                }

            }
        });

        holder.ibRetweet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (tweet.isRetweeted){

                    client.unRetweet(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            super.onSuccess(statusCode, headers, responseString);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });




                    holder.ibRetweet.setImageResource(R.drawable.ic_vector_retweet_stroke);
                    tweet.isRetweeted = false;
                    tweet.retweets--;
                    holder.tvRetweets.setText(Integer.toString(tweet.retweets));

                }else{

                    client.retweet(tweet.uid,  new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });
                    holder.ibRetweet.setImageResource(R.drawable.ic_vector_retweet);
                    tweet.isRetweeted = true;
                    tweet.retweets++;
                    holder.tvRetweets.setText(Integer.toString(tweet.retweets));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //recently added
    // Clean all elements of the recycler

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }



    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimeStamp;
        public TextView tvLikes;
        public TextView tvRetweets;
        public ImageView ivTweetImage;

        public ImageButton ibLike;
        public ImageButton ibRetweet;
        public ImageButton ibComment;

        public ViewHolder(View itemView){
            super(itemView);

            //perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
            tvRetweets = (TextView) itemView.findViewById(R.id.tvRetweets);
            ivTweetImage = (ImageView) itemView.findViewById(R.id.ivTweetImage);
            ibLike = (ImageButton) itemView.findViewById(R.id.ibLike);
            ibRetweet = (ImageButton) itemView.findViewById(R.id.ibRetweet);
            ibComment = (ImageButton) itemView.findViewById(R.id.ibComment);
            ibComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    //make sure the position is valid (that it exists in the view)
                    if (position != RecyclerView.NO_POSITION){
                        Tweet tweet = mTweets.get(position);
                        long tweetUid = tweet.uid;
                        Intent replyTweet = new Intent (context, ComposeActivity.class);
                        replyTweet.putExtra("user_name", tweet.user.screenName);
                        context.startActivity(replyTweet);


                    }

                }
            });

        }

    }


    //stack overflow code
    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;


            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }


    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser){
        //always log the error
        Log.e(TAG, message, error);
        //alert the user to avoid silent errors
        if (alertUser){
            //show a long toast with the error message
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

}
