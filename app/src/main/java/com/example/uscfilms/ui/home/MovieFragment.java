package com.example.uscfilms.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.adapter.RecyclerViewDataAdapter;
import com.example.uscfilms.adapter.SliderAdapter;
import com.example.uscfilms.model.CardList;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.model.SliderData;
import com.example.uscfilms.service.Medias;
import com.example.uscfilms.service.VolleyCallback;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class MovieFragment extends Fragment {
    private final static String TAG = "MovieFragment";
    TextView textView;
    JSONArray arr;
    Button button;
    Context mContext;
    String type = "movie";

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
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("poster_path");
                    sliderDataArrayList.add(new SliderData(imgURL, res.getJSONObject(i).getString("id"), "movie"));
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

        }, cxt, type, "now_playing");
    }


    private void createTopRatedMovies(Context cxt, View view) {
        Medias medias = new Medias();
        CardList topRatedMoviesList = new CardList();
        topRatedMoviesList.setSubtitle("Top-Rated");
        ArrayList<SingleCard> singleCard = new ArrayList<SingleCard>();
        medias.getMedia(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                int length = Math.min(10, res.length());
                for (int i = 0; i < length; i++) {
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("poster_path");
                    Log.d(TAG, "onSuccess1" + imgURL);

                    singleCard.add(new SingleCard(res.getJSONObject(i).getString("id"), imgURL, res.getJSONObject(i).getString("title"), "movie"));
                }
                topRatedMoviesList.setCardList(singleCard);
                allCardLists.add(topRatedMoviesList);
                Log.d(TAG, "cb, top-rated " + singleCard);
                createPopularMovies(cxt, view);
            }
        }, cxt, type, "top_rated");
    }

    private void createPopularMovies(Context cxt, View view)  {
        Medias medias = new Medias();
        CardList popularMoviesList = new CardList();
        popularMoviesList.setSubtitle("Popular");
        ArrayList<SingleCard> singleCard = new ArrayList<SingleCard>();
        medias.getMedia(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray res) throws JSONException {
                int length = Math.min(10, res.length());
                for (int i = 0; i < length; i++) {
                    String imgURL = "https://image.tmdb.org/t/p/w500" + res.getJSONObject(i).getString("poster_path");
                    singleCard.add(new SingleCard(res.getJSONObject(i).getString("id"), imgURL, res.getJSONObject(i).getString("title"), "movie"));
                }
                popularMoviesList.setCardList(singleCard);
                allCardLists.add(popularMoviesList);
                Log.d(TAG, "cb, popular " + popularMoviesList);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.movie_recycler_view);
                recyclerView.setHasFixedSize(true);
                RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allCardLists);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);

            }
        }, cxt, type, "popular");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        Context cxt = getActivity();
        createNowPlayingMovies(cxt, view);

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