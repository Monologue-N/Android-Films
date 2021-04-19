package com.example.uscfilms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.uscfilms.service.Details;
import com.example.uscfilms.service.VolleyCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    String key;

//    private void getDetails(Context cxt, String id) {
//        Details details = new Details();
//        details.getDetails(new VolleyCallback2() {
//            @Override
//            public void onSuccess(JSONObject res) throws JSONException {
//
//            }
//        }, cxt, id);
//    }

    private void getVideos(Context cxt, String id, String type) {
        Details details = new Details();
        details.getVideos(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                Log.d("getVideosRes", "res: " + res);
                if (res == null) {
                    key = "tzkWB85ULJY";
                    Log.d("getVideos", "res is null");
                }
                else if (res.length() == 0) {
                    key = "tzkWB85ULJY";
                    Log.d("getVideos", "res is 0");
                }
                else {
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
                    if (key == null) {
                        key = "tzkWB85ULJY";
                    }
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
//        getDetails(cxt, id, type);
        getVideos(cxt, id, type);
    }



}