package com.example.uscfilms.adapter;

import android.content.Context;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.ui.details.DetailsFragment;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.SingleItemRowHolder> {

    private ArrayList<SingleCard> itemsList;
    private Context mContext;
    final static String TAG = "[RA] ";
    private int idx;

    public RecommendedAdapter(Context context, ArrayList<SingleCard> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommended_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v, i);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SingleCard singleItem = itemsList.get(i);
        idx = i;
        Log.d("showidx", String.valueOf(idx));

        holder.titleId.setText(singleItem.getId());
        holder.titleType.setText(singleItem.getType());
        Picasso.get().load(singleItem.getBackdrop_path()).into(holder.itemImage);
        holder.itemImageMask.setBackground(Drawable.createFromPath("@drawable/gradient"));
        holder.itemImageMask.bringToFront();
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView titleId;

        protected TextView titleType;

        protected ImageView itemImage;

        protected ImageView itemImageMask;


        public SingleItemRowHolder(View view, int i) {
            super(view);

            this.titleId =  view.findViewById(R.id.r_titleId);
            this.itemImage = view.findViewById(R.id.r_itemImage);
            this.itemImageMask = view.findViewById(R.id.r_itemImageMask);
            this.titleType = view.findViewById(R.id.r_titleType);

            itemImage.bringToFront();

            itemImage.setClickable(true);

            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click", "clicked");
//                    Toast.makeText(v.getContext(), "On this click",  Toast.LENGTH_SHORT).show();

                    String id = (String) titleId.getText();
                    String type = (String) titleType.getText();
                    Log.d("getId", id);
                    Log.d("getId", "type: " + type);
                    switchContent(id, type);
                    Log.d("itemlist", String.valueOf(i));
                }
            });


        }

        public void switchContent(String id, String type) {
            if (mContext == null)
                return;
            if (mContext instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) mContext;
                Log.d("getId2", id);
                Log.d("getId2", "type: " + type);
                mainActivity.switchContent(id, type);
            }
        }

    }

}