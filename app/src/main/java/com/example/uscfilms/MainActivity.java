package com.example.uscfilms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uscfilms.service.Details;
import com.example.uscfilms.service.VolleyCallback2;
import com.example.uscfilms.ui.details.DetailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "lifecycle";
    private NavController navController;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.nav_view).setVisibility(View.VISIBLE);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    //    public void switchContent(int id, Fragment fragment) {
    public void switchContent(String id, String type) {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        Fragment df = new DetailsFragment();
//        Fragment old = getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
////        ft.remove(old);
//        ft.replace(R.id.main_activity, fragment);
//        // to keep the previous one
//        ft.addToBackStack(null);
//        Log.d("hcc", "I am here in main");
//        ft.commit();
//        findViewById(R.id.nav_view).setVisibility(View.GONE);
////      findViewById(R.id.nav_view).setVisibility(View.GONE);
//
//        Log.d("hcc", "I am here after commit");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
//
//    }


    public void goToTMDB() {
        Log.d("tmdb", "I am here in tmdb");
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/"));
        startActivity(i);
    }

    public void goToTMDBWithId(String id, String type) {
        Log.d("tmdb", "I am here in tmdbWithId");
        String url = "https://www.themoviedb.org/" + type + "/" + id;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }

    public void goToFB(String id, String type) {
        Log.d("tmdb", "I am here in fb");
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
        startActivity(i);
    }
    public void goToTwitter(String id, String type) {
        Log.d("tmdb", "I am here in twitter");
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"));
        startActivity(i);
    }


    public void refreshWatchlist() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Log.d("findFrag", "-" + currentFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d("findFragTrans", "-" + fragmentTransaction);

        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshWatchlist();
    }

//    private void getVideos(Context cxt, String id, String type) {
//        Details details = new Details();
//        details.getDetails(new VolleyCallback2() {
//            @Override
//            public void onSuccess(JSONObject resObj) throws JSONException {
//                Log.d("getVideosRes", "res: " + resObj);
//                JSONArray res = resObj.getJSONArray("results");
//                if (res != null) {
//                    if (res.length() != 0) {
//                        Log.d("getVideos", "res is not null");
//
//                        for (int i = 0; i < res.length(); i++) {
//                            JSONObject obj = res.getJSONObject(i);
//                            Log.d("getVideosTrailer", "obj:" + obj);
//                            String type = obj.getString("type");
//                            Log.d("getVideosTrailer", "type:" + type);
//
//                            if ((type.equals("Trailer")) && obj.getString("key") != null) {
//                                key = obj.getString("key");
//                                Log.d("getVideosTrailer", key);
//                                break;
//                            }
//                        }
//                        if (key == null) {
//                            for (int i = 0; i < res.length(); i++) {
//                                JSONObject obj = res.getJSONObject(i);
//                                Log.d("getVideosTeaser", "obj:" + obj);
//                                String type = obj.getString("type");
//                                Log.d("getVideosTeaser", "type:" + type);
//                                if ((type.equals("Teaser")) && obj.getString("key") != null) {
//                                    key = obj.getString("key");
//                                    Log.d("getVideosTeaser", key);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//                if (key != null) {
//                    YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
//                    getLifecycle().addObserver(youTubePlayerView);
//                    Log.d("getVideos", key);
//                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                        @Override
//                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                            Log.d("youtubePlayer", "loading?");
//                            youTubePlayer.loadVideo(key, 0);
//                            Log.d("youtubePlayer", "loaded?");
//
//                        }
//                    });
//                } else {
//                    YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
//                    youTubePlayerView.setVisibility(View.GONE);
//                    ImageView imgHolder = findViewById(R.id.img_holder);
//                    Picasso.get().load(backdrop_url).into(imgHolder);
//                    Log.d("novideo", "url: " + backdrop_url);
//                    imgHolder.bringToFront();
//                }
//
//            }
//        }, cxt, id, type, "videos");
//    }
}