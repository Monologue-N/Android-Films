package com.example.uscfilms.ui.watchlist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.adapter.RecommendedAdapter;
import com.example.uscfilms.adapter.WatchlistAdapter;
import com.example.uscfilms.model.SingleWatchlistItem;
import com.example.uscfilms.service.ItemMoveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;


public class WatchlistFragment extends Fragment {
    private Context cxt;
    boolean allowRefresh = true;
    boolean isOnCreation = false;

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
        cxt = mContext;
        isOnCreation = true;

        ArrayList<SingleWatchlistItem> watchlistItemArrayList = new ArrayList<>();

        // traverse shared preferences
        SharedPreferences sharedPref = mContext.getSharedPreferences("watchlist", Context.MODE_PRIVATE);
//        sharedPref.edit().clear().apply();
        String currList = sharedPref.getString("list", "");
        Log.d("3333sharedPref", "-" + currList);
        if (currList == null || currList.isEmpty() || currList.equals("[]")) {
            TextView textView = view.findViewById(R.id.watchlist_hint);
            String hint = "Nothing saved to Watchlist";
            textView.setText(hint);
        } else {

            try {
                JSONArray currArray = new JSONArray(currList);
                for (int i = currArray.length() - 1; i >= 0; i--) {
                    JSONObject obj = currArray.getJSONObject(i);
                    String id = obj.getString("id");
                    String type = obj.getString("type");
                    String poster_path = obj.getString("poster_path");
                    String title = obj.getString("title");
                    watchlistItemArrayList.add(new SingleWatchlistItem(id, type, poster_path, title));
                }



                RecyclerView recyclerView = view.findViewById(R.id.watchlist_recycler_view);
                recyclerView.setHasFixedSize(true);

                WatchlistAdapter adapter = new WatchlistAdapter(mContext, watchlistItemArrayList);
                ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                touchHelper.attachToRecyclerView(recyclerView);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            Map<String, ?> keys = sharedPref.getAll();
//
//            for (Map.Entry<String, ?> entry : keys.entrySet()) {
//                Log.d("map values", entry.getKey() + ": " +
//                        entry.getValue().toString());
//                String id = entry.getKey();
//                String type;
//                String poster_path;
//                String info_str = entry.getValue().toString();
//                try {
//                    JSONObject obj = new JSONObject(info_str);
//                    type = obj.getString("type");
//                    poster_path = obj.getString("poster_path");
//                    watchlistItemArrayList.add(new SingleWatchlistItem(id, type, poster_path));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
//        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("111onResume", "-" + isOnCreation);
        if (!isOnCreation) {
            Log.d("111resume", "watchlist resumed");
            if (cxt == null)
                return;
            if (cxt instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) cxt;
                mainActivity.onlyRefreshWatchlist();
            }
        }
        isOnCreation = false;
    }
}
