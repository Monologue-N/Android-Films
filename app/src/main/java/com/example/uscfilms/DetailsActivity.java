package com.example.uscfilms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uscfilms.adapter.CastAdapter;
import com.example.uscfilms.adapter.RecyclerViewDataAdapter;
import com.example.uscfilms.adapter.ReviewsAdapter;
import com.example.uscfilms.model.SingleCast;
import com.example.uscfilms.model.SingleReview;
import com.example.uscfilms.service.Details;
import com.example.uscfilms.service.VolleyCallback;
import com.example.uscfilms.service.VolleyCallback2;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DetailsActivity extends AppCompatActivity {
    private String key;
    private String backdrop_url;
    private String title;
    private String overview;
    private String genres;
    private String year;

    private TextView titleText;
    private TextView overviewText;
    private TextView genresText;
    private TextView yearText;


    private void getDetails(Context cxt, String id, String type) {
        Details details = new Details();
        details.getDetails(new VolleyCallback2() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(JSONObject res) throws JSONException {
                // fetch data
                backdrop_url = "https://image.tmdb.org/t/p/w500" + res.getString("backdrop_path");
                if (type.equals("movie")) {
                    title = res.getString("title");
                    year = String.valueOf(Integer.parseInt(res.getString("release_date").substring(0, 4)));
                }
                else {
                    title = res.getString("name");
                    year = String.valueOf(Integer.parseInt(res.getString("first_air_date").substring(0, 4)));
                }
                overview = res.getString("overview");

                // split genre names with comma
                JSONArray genresArr = res.getJSONArray("genres");
                ArrayList<String> stringList = new ArrayList<String>();
                for (int i = 0; i < genresArr.length(); i++) {
                    stringList.add(genresArr.getJSONObject(i).getString("name"));
                    Log.d("stringList", "is: " + stringList);
                }
                String[] arr = new String[stringList.size()];
                arr = stringList.toArray(arr);
                genres = String.join(", ", arr);

                // find TextViews
                titleText = findViewById(R.id.details_title);
                overviewText = findViewById(R.id.overviewText);
                genresText = findViewById(R.id.genresText);
                yearText = findViewById(R.id.yearText);

                // render to view
                titleText.setText(title);
                overviewText.setText(overview);
                genresText.setText(genres);
                yearText.setText(year);

                getVideos(cxt, id, type);

            }
        }, cxt, id, type, "details");
    }

    private void getVideos(Context cxt, String id, String type) {
        Details details = new Details();
        details.getDetails(new VolleyCallback2() {
            @Override
            public void onSuccess(JSONObject resObj) throws JSONException {
                Log.d("getVideosRes", "res: " + resObj);
                JSONArray res = resObj.getJSONArray("results");
                if (res != null) {
                    if (res.length() != 0) {
                        Log.d("getVideos", "res is not null");

                        for (int i = 0; i < res.length(); i++) {
                            JSONObject obj = res.getJSONObject(i);
                            Log.d("getVideosTrailer", "obj:" + obj);
                            String type = obj.getString("type");
                            Log.d("getVideosTrailer", "type:" + type);

                            if ((type.equals("Trailer")) && obj.getString("key") != null) {
                                key = obj.getString("key");
                                Log.d("getVideosTrailer", key);
                                break;
                            }
                        }
                        if (key == null) {
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject obj = res.getJSONObject(i);
                                Log.d("getVideosTeaser", "obj:" + obj);
                                String type = obj.getString("type");
                                Log.d("getVideosTeaser", "type:" + type);
                                if ((type.equals("Teaser")) && obj.getString("key") != null) {
                                    key = obj.getString("key");
                                    Log.d("getVideosTeaser", key);
                                    break;
                                }
                            }
                        }
                    }
                }
                        if (key != null) {
                            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                            getLifecycle().addObserver(youTubePlayerView);
                            Log.d("getVideos", key);
                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    Log.d("youtubePlayer", "loading?");
                                    youTubePlayer.loadVideo(key, 0);
                                    Log.d("youtubePlayer", "loaded?");

                                }
                            });
                        } else {
                            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                            youTubePlayerView.setVisibility(View.GONE);
                            ImageView imgHolder = findViewById(R.id.img_holder);
                            Picasso.get().load(backdrop_url).into(imgHolder);
                            Log.d("novideo", "url: " + backdrop_url);
                            imgHolder.bringToFront();
                        }

            }
        }, cxt, id, type, "videos");
    }


    public void getCast(Context cxt, String id, String type) {
        Details details = new Details();
        ArrayList<SingleCast> castList = new ArrayList<SingleCast>();
        details.getDetails(new VolleyCallback2() {
            @Override
            public void onSuccess(JSONObject resObj) throws JSONException {
                JSONArray res = resObj.getJSONArray("cast");
                for (int i = 0; i < 6; i++) {
                    if (res.getJSONObject(i) != null) {
                        JSONObject obj = res.getJSONObject(i);
                        String profile_path = "https://image.tmdb.org/t/p/w500/" + obj.getString("profile_path");
                        String name = obj.getString("name");
                        String id = obj.getString("id");
                        castList.add(new SingleCast(profile_path, name, id));
                    }
                }
                RecyclerView recyclerView = findViewById(R.id.cast_recycler_view);
                recyclerView.setHasFixedSize(true);
                CastAdapter adapter = new CastAdapter(cxt, castList);
//                recyclerView.setLayoutManager(new LinearLayoutManager(cxt, LinearLayoutManager.VERTICAL, false));
                int numberOfColumns = 3;
                recyclerView.setLayoutManager(new GridLayoutManager(cxt, numberOfColumns));
                recyclerView.setAdapter(adapter);

                getReviews(cxt, id, type);

            }
        }, cxt, id, type, "casts");
    }


    public void getReviews(Context cxt, String id, String type) {
        Log.d("reviews", ": getReviews creation" );

        Details details = new Details();
        ArrayList<SingleReview> reviewList = new ArrayList<SingleReview>();
        details.getDetails(new VolleyCallback2() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(JSONObject resObj) throws JSONException, ParseException {
                JSONArray res = resObj.getJSONArray("results");
                for (int i = 0; i < 3; i++) {
                    Log.d("reviews", "res: " + res);

                    if (res.getJSONObject(i) != null) {
                        JSONObject obj = res.getJSONObject(i);
                        String author = obj.getString("author");
                        String created_at = obj.getString("created_at");
                        // parse String to date
                        String ISO_86_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
                        SimpleDateFormat formatter = new SimpleDateFormat(ISO_86_24H_FULL_FORMAT);
                        Date date = formatter.parse(created_at);
                        // format date
                        DateFormat df = new SimpleDateFormat("E, MMM dd yyyy");
                        created_at = df.format(date);


                        String rating = obj.getJSONObject("author_details").getString("rating");
                        rating = Integer.toString(Integer.parseInt(rating) / 2) + "/5";

                        String content = obj.getString("content");

                        String creation = "by " + author + " on " + created_at;

                        Log.d("reviews", ": " + created_at);
                        Log.d("reviews", ": " + author);
                        Log.d("reviews", ": " + rating);

                        reviewList.add(new SingleReview(creation, rating, content));
                    }
                }
                RecyclerView recyclerView = findViewById(R.id.reviews_recycler_view);
                recyclerView.setHasFixedSize(true);
                ReviewsAdapter adapter = new ReviewsAdapter(cxt, reviewList);
                recyclerView.setLayoutManager(new LinearLayoutManager(cxt, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
        }, cxt, id, type, "reviews");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Context cxt = getApplicationContext();
        String id = getIntent().getStringExtra("id");
        String type = getIntent().getStringExtra("type");
        getDetails(cxt, id, type);
        getCast(cxt, id, type);

    }



}