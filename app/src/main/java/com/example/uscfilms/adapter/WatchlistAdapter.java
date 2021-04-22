package com.example.uscfilms.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.model.SingleWatchlistItem;
import com.example.uscfilms.service.ItemMoveCallback;
import com.example.uscfilms.ui.details.DetailsFragment;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.SingleItemRowHolder> implements ItemMoveCallback.ItemTouchHelperContract  {

    private ArrayList<SingleWatchlistItem> watchlist;
    private Context mContext;
    private int idx;

    public WatchlistAdapter(Context context, ArrayList<SingleWatchlistItem> watchlist) {
        this.watchlist = watchlist;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.watchlist_layout, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v, i);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SingleWatchlistItem singleItem = watchlist.get(i);

        holder.watchlist_id.setText(singleItem.getId());
        holder.watchlist_type.setText(singleItem.getType().toUpperCase());
        Log.d("showType", "" + singleItem.getType());
        Picasso.get().load(singleItem.getPoster_path()).into(holder.watchlist_img);
        holder.watchlist_img_mask.setBackground(Drawable.createFromPath("@drawable/gradient"));
//        holder.watchlist_img_mask.bringToFront();
        holder.watchlist_poster_path.setText(singleItem.getPoster_path());
    }

    @Override
    public int getItemCount() {
        return (null != watchlist ? watchlist.size() : 0);
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        Log.d("position", "from " + fromPosition);
        Log.d("position", "to " + toPosition);

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(watchlist, i, i + 1);
            }

        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(watchlist, i, i - 1);
            }
        }
        // change sharedPref data
        SharedPreferences sharedPref = mContext.getSharedPreferences("watchlist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String prev = sharedPref.getString("list", "");

        try {
            JSONArray prev_arr = new JSONArray(prev);
            int prevIdx = prev_arr.length() - fromPosition - 1;
            int nxtIdx = prev_arr.length() - toPosition - 1;
            Log.d("4444", "" + fromPosition);
            Log.d("4444", "" + toPosition);

            JSONObject obj = prev_arr.getJSONObject(prevIdx);
            prev_arr.remove(prevIdx);
//            prev_arr.put(prev_arr.getJSONObject(prev_arr.length() - 1));
            for (int j = prev_arr.length(); j > nxtIdx; j--) {
                Log.d("4444obj", "-" + prev_arr.get(j - 1));
                prev_arr.put(j, prev_arr.getJSONObject(j - 1));
            }
            prev_arr.put(nxtIdx, obj);


            editor.putString("list", prev_arr.toString());
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(SingleItemRowHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(SingleItemRowHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.TRANSPARENT);

    }


    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView watchlist_id;

        protected TextView watchlist_type;

        protected ImageView watchlist_img;

        protected ImageView watchlist_img_mask;

        protected ImageView watchlist_remove_btn;

        protected TextView watchlist_poster_path;

        View rowView;


        public SingleItemRowHolder(View view, int i) {
            super(view);

            rowView = view;

            this.watchlist_id = view.findViewById(R.id.watchlist_id);
            this.watchlist_img =  view.findViewById(R.id.watchlist_img);
            this.watchlist_img_mask = view.findViewById(R.id.watchlist_img_mask);
            this.watchlist_type = view.findViewById(R.id.watchlist_type);
            this.watchlist_remove_btn = view.findViewById(R.id.watchlist_remove);
            this.watchlist_poster_path = view.findViewById(R.id.watchlist_poster_path);

//            watchlist_img.bringToFront();
            watchlist_img.setClickable(true);

//            watchlist_remove_btn.bringToFront();
            watchlist_remove_btn.setClickable(true);

            watchlist_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "On this click",  Toast.LENGTH_SHORT).show();

                    String id = (String) watchlist_id.getText();
                    String type = (String) watchlist_type.getText();
                    switchContent(id, type);
                }
            });


            // Setting onClick behavior to the button
            watchlist_remove_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPref = mContext.getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String prev = sharedPref.getString("list", "");
                    String id = (String) watchlist_id.getText();
                    try {
                        JSONArray prev_arr = new JSONArray(prev);
                        for (int i = 0; i < prev_arr.length(); i++) {
                            JSONObject obj = prev_arr.getJSONObject(i);
                            if (obj.getString("id").equals(id)) {
                                Log.d("2222", "id is " + id);
                                Log.d("2222", "??" + obj.getString("id").equals(id));
                                String title = obj.getString("title");
                                prev_arr.remove(i);
                                Toast.makeText(view.getContext(),  title + " was removed from Watchlist" , Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        editor.putString("list", prev_arr.toString());
                        editor.apply();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (mContext == null)
                        return;
                    if (mContext instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) mContext;
                        mainActivity.refreshWatchlist();
                    }


                }
            });


        }

        public void switchContent(String id, String type) {
            if (mContext == null)
                return;
            if (mContext instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.switchContent(id, type);
            }
        }


    }

}