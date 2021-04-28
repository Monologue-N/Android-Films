package com.example.uscfilms.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.adapter.RecyclerViewDataAdapter;
import com.example.uscfilms.adapter.SliderAdapter;
import com.example.uscfilms.model.CardList;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.model.SliderData;
import com.example.uscfilms.service.Medias;
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


public class TvShowFragment extends Fragment {
    private final static String TAG = "TVShowFragment";
    TextView textView;
    JSONArray arr;
    Button button;
    Context mContext;
    String type = "tv";

    ArrayList<CardList> allCardLists;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allCardLists = new ArrayList<CardList>();

    }

    private void createDayTVShows(Context cxt, View view) {
        View layout = view.findViewById(R.id.layout_view1);
        layout.setVisibility(View.VISIBLE);

        Medias medias = new Medias();

        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = view.findViewById(R.id.slider);

        medias.getMedia(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                int length = Math.min(6, res.length());
                for (int i = 0; i < length; i++) {
                    Log.d("DayTV", "-" + res);
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("poster_path");
                    sliderDataArrayList.add(new SliderData(imgURL, res.getJSONObject(i).getString("id"), type));
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
                createTopRatedTVShows(cxt, view);

            }

        }, cxt, type, "day");
    }


    private void createTopRatedTVShows(Context cxt, View view) {
        Medias medias = new Medias();
        CardList topRatedTVShowsList = new CardList();
        topRatedTVShowsList.setSubtitle("Top-Rated");
        ArrayList<SingleCard> singleCard = new ArrayList<SingleCard>();
        medias.getMedia(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                int length = Math.min(10, res.length());
                for (int i = 0; i < length; i++) {
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("poster_path");
                    Log.d(TAG, "onSuccess1" + imgURL);

                    singleCard.add(new SingleCard(res.getJSONObject(i).getString("id"), imgURL, res.getJSONObject(i).getString("name"), type));
                }
                topRatedTVShowsList.setCardList(singleCard);
                allCardLists.add(topRatedTVShowsList);
                Log.d(TAG, "cb, top-rated " + singleCard);
                CreatePopularTVShows(cxt, view);
            }
        }, cxt, type, "top_rated");
    }

    private void CreatePopularTVShows(Context cxt, View view)  {
        Medias medias = new Medias();
        CardList popularTVShowsList = new CardList();
        popularTVShowsList.setSubtitle("Popular");
        ArrayList<SingleCard> singleCard = new ArrayList<SingleCard>();
        medias.getMedia(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                int length = Math.min(10, res.length());
                for (int i = 0; i < length; i++) {
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("poster_path");
                    singleCard.add(new SingleCard(res.getJSONObject(i).getString("id"), imgURL, res.getJSONObject(i).getString("name"), type));
                }
                popularTVShowsList.setCardList(singleCard);
                allCardLists.add(popularTVShowsList);
                Log.d(TAG, "cb, popular " + popularTVShowsList);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
                recyclerView.setHasFixedSize(true);
                RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allCardLists);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);

                View layout = view.findViewById(R.id.layout_view1);
                layout.setVisibility(View.GONE);

            }
        }, cxt, type, "popular");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        Context cxt = getActivity();
        createDayTVShows(cxt, view);

        // make footer clickable
        TextView footer;
        footer = view.findViewById(R.id.footer);
        footer.setClickable(true);
        // customize footer onClick
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tmdb", "I clicked");
                MainActivity mainActivity = (MainActivity) cxt;
                mainActivity.goToTMDB();
            }
        });

        return view;

    }
}