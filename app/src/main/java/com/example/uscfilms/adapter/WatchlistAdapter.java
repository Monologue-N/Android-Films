package com.example.uscfilms.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.example.uscfilms.ui.details.DetailsFragment;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.SingleItemRowHolder>  {

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
        holder.watchlist_type.setText(singleItem.getType());
        Picasso.get().load(singleItem.getPoster_path()).into(holder.watchlist_img);
        holder.watchlist_img_mask.setBackground(Drawable.createFromPath("@drawable/gradient"));
        holder.watchlist_img_mask.bringToFront();
        holder.watchlist_poster_path.setText(singleItem.getPoster_path());
    }

    @Override
    public int getItemCount() {
        return (null != watchlist ? watchlist.size() : 0);
    }


    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView watchlist_id;

        protected TextView watchlist_type;

        protected ImageView watchlist_img;

        protected ImageView watchlist_img_mask;

        protected ImageView watchlist_remove_btn;

        protected TextView watchlist_poster_path;


        public SingleItemRowHolder(View view, int i) {
            super(view);

            this.watchlist_id = view.findViewById(R.id.watchlist_id);
            this.watchlist_img =  view.findViewById(R.id.watchlist_img);
            this.watchlist_img_mask = view.findViewById(R.id.watchlist_img_mask);
            this.watchlist_type = view.findViewById(R.id.watchlist_type);
            this.watchlist_remove_btn = view.findViewById(R.id.watchlist_remove);
            this.watchlist_poster_path = view.findViewById(R.id.watchlist_poster_path);

            watchlist_img.bringToFront();
            watchlist_img.setClickable(true);

            watchlist_remove_btn.bringToFront();
            watchlist_remove_btn.setClickable(true);

            watchlist_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "On this click",  Toast.LENGTH_SHORT).show();

                    String id = (String) watchlist_id.getText();
                    String type = (String) watchlist_type.getText();
                    switchContent(id, type);
                }
            });


            // Setting onClick behavior to the button
            watchlist_remove_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),  " was removed from Watchlist" , Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPref = mContext.getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String id = (String) watchlist_id.getText();
                    editor.remove(id);
                    editor.apply();


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