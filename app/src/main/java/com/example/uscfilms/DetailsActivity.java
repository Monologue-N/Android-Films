package com.example.uscfilms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uscfilms.adapter.CastAdapter;
import com.example.uscfilms.adapter.RecommendedAdapter;
import com.example.uscfilms.adapter.RecyclerViewDataAdapter;
import com.example.uscfilms.adapter.ReviewsAdapter;
import com.example.uscfilms.decoration.SpacesItemDecoration;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.model.SingleCast;
import com.example.uscfilms.model.SingleReview;
import com.example.uscfilms.service.Details;
import com.example.uscfilms.service.Medias;
import com.example.uscfilms.service.VolleyCallback;
import com.example.uscfilms.service.VolleyCallback2;
import com.google.gson.JsonParser;
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
    private String poster_path;
    private String title;
    private String overview;
    private String genres;
    private String year;

    private TextView titleText;
    private TextView overviewText;
    private TextView genresText;
    private TextView yearText;
    private TextView posterPathText;

    private Context mContext;

    private boolean added = false;


    private void getDetails(Context cxt, String id, String type, Activity activity) {
        Details details = new Details();
        details.getDetails(new VolleyCallback2() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(JSONObject res) throws JSONException {
                // fetch data
                if (res.getString("backdrop_path").equals("null") || res.getString("backdrop_path").isEmpty()) {
                    backdrop_url = "https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/movie-placeholder.jpg";
                }
                else {
                    backdrop_url = "https://image.tmdb.org/t/p/w500" + res.getString("backdrop_path");
                }
                poster_path = "https://image.tmdb.org/t/p/w500" + res.getString("poster_path");
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
                posterPathText = findViewById(R.id.details_poster_path);


                // render to view
                titleText.setText(title);
                overviewText.setText(overview);
                genresText.setText(genres);
                yearText.setText(year);
                posterPathText.setText(poster_path);

                getVideos(cxt, id, type, activity);

            }
        }, cxt, id, type, "details");
    }

    private void getVideos(Context cxt, String id, String type, Activity activity) {
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

                        getCast(cxt, id, type, activity);

            }
        }, cxt, id, type, "videos");
    }


    public void getCast(Context cxt, String id, String type, Activity activity) {
        Details details = new Details();
        ArrayList<SingleCast> castList = new ArrayList<SingleCast>();
        details.getDetails(new VolleyCallback2() {
            @Override
            public void onSuccess(JSONObject resObj) throws JSONException {
                JSONArray res = resObj.getJSONArray("cast");
                int length = Math.min(6, res.length());
                for (int i = 0; i < length; i++) {
                    if (res.getJSONObject(i) != null) {
                        JSONObject obj = res.getJSONObject(i);
                        String profile_path = "";
                        if (obj.getString("profile_path").equals("null") || obj.getString("profile_path").isEmpty() || obj.getString("profile_path").equals("")) {
                            profile_path = "https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/person-placeholder.png";
                        }
                        else {
                            profile_path = "https://image.tmdb.org/t/p/w500/" + obj.getString("profile_path");

                        }
                        String name = obj.getString("name");
                        String id = obj.getString("id");
                        castList.add(new SingleCast(profile_path, name, id));
                    }
                }
                RecyclerView recyclerView = findViewById(R.id.cast_recycler_view);
//                recyclerView.setHasFixedSize(true);
                CastAdapter adapter = new CastAdapter(cxt, castList);
                int numberOfColumns = 3;
                recyclerView.setLayoutManager(new GridLayoutManager(cxt, numberOfColumns));
                recyclerView.setAdapter(adapter);

                getReviews(cxt, id, type, activity);

            }
        }, cxt, id, type, "casts");
    }


    public void getReviews(Context cxt, String id, String type, Activity activity) {
        Log.d("reviews", ": getReviews creation" );

        Details details = new Details();
        ArrayList<SingleReview> reviewList = new ArrayList<SingleReview>();
        details.getDetails(new VolleyCallback2() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(JSONObject resObj) throws JSONException, ParseException {
                JSONArray res = resObj.getJSONArray("results");
                if (res == null) {
                    TextView reviewsText = findViewById(R.id.reviews);
                    reviewsText.setVisibility(View.GONE);
                } else if (res.length() == 0) {
                    TextView reviewsText = findViewById(R.id.reviews);
                    reviewsText.setVisibility(View.GONE);

                } else {
                    TextView reviewsText = findViewById(R.id.reviews);
                    reviewsText.setVisibility(View.VISIBLE);

                    int length = Math.min(3, res.length());
                    for (int i = 0; i < length; i++) {
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
                            Log.d("rating", ":" + rating);
                            if (rating != null && !rating.equals("null")) {
                                rating = Integer.toString(Integer.parseInt(rating) / 2) + "/5";
                            } else {
                                rating = "0/5";
                            }
                            String content = obj.getString("content");

                            String creation = "by " + author + " on " + created_at;

                            Log.d("reviews", ": " + created_at);
                            Log.d("reviews", ": " + author);
                            Log.d("reviews", ": " + rating);

                            reviewList.add(new SingleReview(creation, rating, content));
                        }
                    }
                    RecyclerView recyclerView = findViewById(R.id.reviews_recycler_view);
                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.review_margin);
                    recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
                    recyclerView.setHasFixedSize(true);
                    ReviewsAdapter adapter = new ReviewsAdapter(cxt, reviewList, activity);
                    recyclerView.setLayoutManager(new LinearLayoutManager(cxt, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);

                    getRecommended(cxt, id, type, activity);
                }
            }
        }, cxt, id, type, "reviews");
    }

    public void getRecommended(Context cxt, String id, String type, Activity activity) {
        Medias medias = new Medias();
        ArrayList<SingleCard> cardList = new ArrayList<>();
        medias.getRecommended(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException, ParseException {

                if (res == null) {
                    TextView recommendedText = findViewById(R.id.recommended);
                    recommendedText.setVisibility(View.GONE);
                } else if (res.length() == 0) {
                    TextView recommendedText = findViewById(R.id.recommended);
                    recommendedText.setVisibility(View.GONE);

                } else {
                    TextView recommendedText = findViewById(R.id.recommended);
                    recommendedText.setVisibility(View.VISIBLE);

                    int length = Math.min(10, res.length());
                    for (int i = 0; i < length; i++) {
                        if (res.getJSONObject(i) != null) {
                            JSONObject obj = res.getJSONObject(i);
                            String id = obj.getString("id");
                            String imgURL = "";

                            if (obj.getString("poster_path").equals("null") || obj.getString("poster_path").isEmpty()) {
                                imgURL = "https://cinemaone.net/images/movie_placeholder.png";
                            }
                            else {
                                imgURL = "https://image.tmdb.org/t/p/w500" + obj.getString("poster_path");

                            }
                            Log.d("Recommended", "img: " + imgURL);
                            String title;
                            title = (type.equals("movie")) ? obj.getString("title") : obj.getString("name");
                            cardList.add(new SingleCard(id, imgURL, title, type));
                        }
                    }
                    RecyclerView recyclerView = findViewById(R.id.recommended_recycler_view);
                    recyclerView.setHasFixedSize(true);
                    RecommendedAdapter adapter = new RecommendedAdapter(cxt, cardList, activity);
                    recyclerView.setLayoutManager(new LinearLayoutManager(cxt, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(adapter);

                    View layout = findViewById(R.id.layout_view3);
                    layout.setVisibility(View.GONE);
                }
            }
        }, cxt, type, "recommended", id);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        View layout = findViewById(R.id.layout_view3);
        layout.setVisibility(View.VISIBLE);


        Context cxt = getApplicationContext();
        Activity activity = this;
        String id = getIntent().getStringExtra("id");
        String type = getIntent().getStringExtra("type");
        type = type.toLowerCase();

        ImageView addToWatchListBtn = findViewById(R.id.addToWatchlist);
        addToWatchListBtn.setClickable(true);
        addToWatchListBtn.bringToFront();

        added = false;
        Log.d("checkadd", "I am here");

        // check if the key exists
        SharedPreferences sharedPref = getSharedPreferences("watchlist", Context.MODE_PRIVATE);
        String prev = sharedPref.getString("list", "");

        if (prev != null && !prev.isEmpty()) {
            try {
                JSONArray prev_arr = new JSONArray(prev);
                for (int i = 0; i < prev_arr.length(); i++) {
                    JSONObject obj = prev_arr.getJSONObject(i);
                    if (obj.getString("id").equals(id)) {
                        added = true;
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (added) {
            // already added to watchlist
            addToWatchListBtn.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
        }
        else {
            // not in watchlist
            addToWatchListBtn.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
        }
        Log.d("checkaddBool", "" +added);


        String finalType = type;
        addToWatchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove
                if (added) {
                    Toast.makeText(v.getContext(), title + " was removed from Watchlist" , Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref = getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    String prev = sharedPref.getString("list", "");
                    try {
                        JSONArray prev_arr = new JSONArray(prev);
                        for (int i = 0; i < prev_arr.length(); i++) {
                            JSONObject obj = prev_arr.getJSONObject(i);
                            if (obj.getString("id").equals(id)) {
                                prev_arr.remove(i);
                                break;
                            }
                        }
                        editor.putString("list", prev_arr.toString());
                        editor.apply();
                        addToWatchListBtn.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
                        added = false;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                // add
                else {
                    Toast.makeText(v.getContext(), title + " was added to Watchlist" , Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref = getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    String prev = sharedPref.getString("list", "");
                    try {
                        JSONArray prevArray;
                        if (prev != null && !prev.isEmpty()) {
                            Log.d("222watchlist", "-" + prev);
                            prevArray = new JSONArray(prev);
                            Log.d("222watchlist", "-" + prevArray);
                        }
                        else {
                            prevArray = new JSONArray();
                        }

                        String nothing = "{}";
                        JSONObject info = null;
                        try {
                            info = new JSONObject(nothing);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            info.put("id", id);
                            info.put("type", finalType);
                            info.put("poster_path", poster_path);
                            info.put("title", title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        prevArray.put(info);
//                                    JSONObject info_obj = new JSONObject();
//                                    info_obj.put("info", prevArray);

                        editor.putString("list", prevArray.toString());
                        editor.apply();
                        addToWatchListBtn.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                        added = true;
                        Log.d("watchlist", "-" + editor);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        ImageView fbBtn = findViewById(R.id.fb);
        ImageView twitterBtn = findViewById(R.id.twitter);

        fbBtn.setClickable(true);
        twitterBtn.setClickable(true);

        String finalType1 = type;
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFB(id, finalType1);

            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTwitter(id, finalType1);
            }
        });

        getDetails(cxt, id, type, activity);
//        getCast(cxt, id, type, activity);
//        getRecommended(cxt, id, type, activity);

    }


    public void goToFB(String id, String type) {
        Log.d("tmdb", "I am here in fb");
        mContext = getApplicationContext();

        String fbURL = "https://www.facebook.com/sharer/sharer.php?u=" + "https://www.themoviedb.org/" + type + "/" + id ;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(fbURL));
        startActivity(i);
    }
    public void goToTwitter(String id, String type) {
        Log.d("tmdb", "I am here in twitter");
        String twitterURL = "https://twitter.com/intent/tweet?text=Check%20this%20out!%0D" + "https://www.themoviedb.org/" + type + "/" + id;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"));
        startActivity(i);
    }

    public void switchContent(String id, String type) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void switchToReview(String creation, String rating) {
        Intent intent = new Intent(this, ReviewActivity.class);
        Log.d("switch777", "to review");
        intent.putExtra("creation", creation);
        intent.putExtra("rating", rating);
//        intent.putExtra("review", review);
        startActivity(intent);
    }
}