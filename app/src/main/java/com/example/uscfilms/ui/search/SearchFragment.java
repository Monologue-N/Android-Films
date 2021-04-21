package com.example.uscfilms.ui.search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.uscfilms.R;
import com.example.uscfilms.adapter.RecommendedAdapter;
import com.example.uscfilms.adapter.SearchResultAdapter;
import com.example.uscfilms.model.SingleSearchResult;
import com.example.uscfilms.service.Search;
import com.example.uscfilms.service.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;


public class SearchFragment extends Fragment {
    String query;

    public SearchFragment() {
        // Required empty public constructor
    }

    public void getSearchResult(Context cxt, String query) {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Remove the underline of search plate
//        View v = view.findViewById(R.id.search_plate);
//        v.setBackgroundColor(Color.TRANSPARENT);

        Context mContext = getActivity();

        SearchView searchView = view.findViewById(R.id.search_tool_bar);
        searchView.setBackgroundColor(Color.TRANSPARENT);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Search search = new Search();
                ArrayList<SingleSearchResult> searchList = new ArrayList<>();
                search.getSearchResults(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONArray res) throws JSONException, ParseException {
                        Log.d("SearchResult", "" + res);
                        int length = Math.min(20, res.length());
                        for (int i = 0; i < length; i++) {
                            if (res.getJSONObject(i) != null) {
                                JSONObject obj = res.getJSONObject(i);
                                String id = obj.getString("id");
                                String backdrop_path = "https://image.tmdb.org/t/p/w500" + obj.getString("backdrop_path");
                                String type = obj.getString("type");
                                String year = obj.getString("year");
                                String title = obj.getString("title");
                                String rating = obj.getString("rating");
                                searchList.add(new SingleSearchResult(id, backdrop_path, type, year, title, rating));
                            }
                        }
                        RecyclerView recyclerView = view.findViewById(R.id.search_recycler_view);
                        SearchResultAdapter adapter = new SearchResultAdapter(mContext, searchList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);

                    }
                }, mContext, newText);


                return false;
            }
        });


        return view;
    }
}