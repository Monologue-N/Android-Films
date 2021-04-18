package com.example.uscfilms.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uscfilms.R;
import com.example.uscfilms.adapter.RecyclerViewDataAdapter;
import com.example.uscfilms.adapter.SliderAdapter;
import com.example.uscfilms.model.CardList;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.model.SliderData;
import com.example.uscfilms.service.NowPlayingMovies;
import com.example.uscfilms.service.PopularMovies;
import com.example.uscfilms.service.TopRatedMovies;
import com.example.uscfilms.service.VolleyCallback;
import com.google.gson.JsonArray;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MovieFragment extends Fragment {
    private final static String TAG = "MovieFragment";
    TextView textView;
    JSONArray arr;
    String dummyURL = "https://image.tmdb.org/t/p/w500/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg";

    ArrayList<CardList> allCardLists;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCardLists = new ArrayList<CardList>();

    }

    private void createNowPlayingMovies(Context cxt, View view) {
        NowPlayingMovies npm = new NowPlayingMovies();

        // Urls of our images.
        String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
        String url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp";
        String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";
        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.slider);

        npm.getNowPlayingMovies(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                for (int i = 0; i < 6; i++) {
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("poster_path");
                    sliderDataArrayList.add(new SliderData(imgURL));
                }
                // passing this array list inside our adapter class.
                SliderAdapter adapter = new SliderAdapter(cxt, sliderDataArrayList);

                // below method is used to set auto cycle direction in left to
                // right direction you can change according to requirement.
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

                // below method is used to
                // setadapter to sliderview.
                sliderView.setSliderAdapter(adapter);

                // below method is use to set
                // scroll time in seconds.
                sliderView.setScrollTimeInSec(3);

                // to set it scrollable automatically
                // we use below method.
                sliderView.setAutoCycle(true);

                // to start autocycle below method is used.
                sliderView.startAutoCycle();
                createTopRatedMovies(cxt, view);

            }

        }, cxt);


//        // adding the urls inside array list
//        sliderDataArrayList.add(new SliderData(url1));
//        sliderDataArrayList.add(new SliderData(url2));
//        sliderDataArrayList.add(new SliderData(url3));
//
//        // passing this array list inside our adapter class.
//        SliderAdapter adapter = new SliderAdapter(cxt, sliderDataArrayList);
//
//        // below method is used to set auto cycle direction in left to
//        // right direction you can change according to requirement.
//        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//
//        // below method is used to
//        // setadapter to sliderview.
//        sliderView.setSliderAdapter(adapter);
//
//        // below method is use to set
//        // scroll time in seconds.
//        sliderView.setScrollTimeInSec(3);
//
//        // to set it scrollable automatically
//        // we use below method.
//        sliderView.setAutoCycle(true);
//
//        // to start autocycle below method is used.
//        sliderView.startAutoCycle();
    }


    private void createTopRatedMovies(Context cxt, View view) {
        CardList topRatedMoviesList = new CardList();
        topRatedMoviesList.setSubtitle("Top-Rated");
        ArrayList<SingleCard> singleCard = new ArrayList<SingleCard>();
        TopRatedMovies trm = new TopRatedMovies();
        trm.getTopRatedMovies(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                for (int i = 0; i < 20; i++) {
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("backdrop_path");
//                    ImageView image = view.findViewById(R.id.imageView);
//                    Picasso.get().load(imgURL).into(image);
                    Log.d(TAG, "onSuccess1" + imgURL);

                    singleCard.add(new SingleCard("Id " + i, imgURL, res.getJSONObject(i).getString("title")));
                }
                topRatedMoviesList.setCardList(singleCard);
                allCardLists.add(topRatedMoviesList);
                Log.d(TAG, "cb, top-rated " + singleCard);
                createPopularMovies(cxt, view);
            }
        }, cxt);

        // try dummy data
//        for (int j = 0; j < 20; j++) {
//            singleCard.add(new SingleCard(String.valueOf(j), dummyURL, String.valueOf(j)));
//        }
//        topRatedMoviesList.setCardList(singleCard);
//        allCardLists.add(topRatedMoviesList);
    }

    private void createPopularMovies(Context cxt, View view)  {
        CardList popularMoviesList = new CardList();
        popularMoviesList.setSubtitle("Popular");
        ArrayList<SingleCard> singleCard = new ArrayList<SingleCard>();
        PopularMovies trm = new PopularMovies();
        trm.getPopularMovies(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                for (int i = 0; i < 20; i++) {
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("backdrop_path");
//                    ImageView image = view.findViewById(R.id.imageView);
//                    Picasso.get().load(imgURL).into(image);

                    singleCard.add(new SingleCard("Id " + i, imgURL, res.getJSONObject(i).getString("title")));
                }
                popularMoviesList.setCardList(singleCard);
                allCardLists.add(popularMoviesList);
                Log.d(TAG, "cb, popular " + popularMoviesList);
                // dummy data
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
                recyclerView.setHasFixedSize(true);
                RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allCardLists);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
        }, cxt);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        Context cxt = getActivity();
        createNowPlayingMovies(cxt, view);



        return view;

    }
}