package com.example.uscfilms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.ArrayList;

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
                    year = res.getString("release_date");
                }
                else {
                    title = res.getString("name");
                    year = res.getString("first_air_date");
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
                genres = String.join(",", arr);

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
        }, cxt, id, type);
    }

    private void getVideos(Context cxt, String id, String type) {
        Details details = new Details();
        details.getVideos(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                Log.d("getVideosRes", "res: " + res);
//                if (res == null) {
//                    key = "tzkWB85ULJY";
//                    Log.d("getVideos", "res is null");
//                }
//                else if (res.length() == 0) {
//                    key = "tzkWB85ULJY";
//                    Log.d("getVideos", "res is 0");
//                }
//                else {
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
//                    if (key == null) {
//                        key = "tzkWB85ULJY";
//                    }
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
                            ImageView imgHolder = findViewById(R.id.img_holder);
                            Picasso.get().load(backdrop_url).into(imgHolder);
                        }
                    }
                }
            }
        }, cxt, id, type);
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

    }



}