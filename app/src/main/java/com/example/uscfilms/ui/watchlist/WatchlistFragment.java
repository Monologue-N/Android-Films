package com.example.uscfilms.ui.watchlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uscfilms.R;
import com.example.uscfilms.adapter.RecommendedAdapter;
import com.example.uscfilms.adapter.WatchlistAdapter;
import com.example.uscfilms.model.SingleWatchlistItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;


public class WatchlistFragment extends Fragment {

    public WatchlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        Context mContext = view.getContext();

        ArrayList<SingleWatchlistItem> watchlistItemArrayList = new ArrayList<>();

        // traverse shared preferences
        SharedPreferences sharedPref = mContext.getSharedPreferences("watchlist", Context.MODE_PRIVATE);
//        sharedPref.edit().clear().apply();
        Log.d("sharedPref", "-" + sharedPref.getAll());
        if (sharedPref.getAll() == null || sharedPref.getAll().isEmpty()) {
            TextView textView = view.findViewById(R.id.watchlist_hint);
            String hint = "Nothing saved to Watchlist";
            textView.setText(hint);
        }
        else {
            Map<String,?> keys = sharedPref.getAll();

            for(Map.Entry<String,?> entry : keys.entrySet()){
                Log.d("map values",entry.getKey() + ": " +
                        entry.getValue().toString());
                String id = entry.getKey();
                String type;
                String poster_path;
                String info_str = entry.getValue().toString();
                try {
                    JSONObject obj = new JSONObject(info_str);
                    type = obj.getString("type");
                    poster_path = obj.getString("poster_path");
                    watchlistItemArrayList.add(new SingleWatchlistItem(id, type, poster_path));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            RecyclerView recyclerView = view.findViewById(R.id.watchlist_recycler_view);
            recyclerView.setHasFixedSize(true);

            WatchlistAdapter adapter = new WatchlistAdapter(mContext, watchlistItemArrayList);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            recyclerView.setAdapter(adapter);
        }




        return view;
    }
}
