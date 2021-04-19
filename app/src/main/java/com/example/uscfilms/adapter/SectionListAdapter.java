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

class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleCard> itemsList;
    private Context mContext;
    final static String TAG = "[SLDA] ";
    private int idx;

    public SectionListDataAdapter(Context context, ArrayList<SingleCard> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
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

//        Glide.with(mContext).load(singleItem.getBackdrop_path()).into(new GlideDrawableViewBackgroundTarget(holder.itemImage));

       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
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

        protected Button button;


        public SingleItemRowHolder(View view, int i) {
            super(view);

            this.titleId = (TextView) view.findViewById(R.id.titleId);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.itemImageMask = view.findViewById(R.id.itemImageMask);
            this.titleType = view.findViewById(R.id.titleType);

            itemImage.bringToFront();
//
            itemImage.setClickable(true);

            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click", "clicked");
                    Toast.makeText(v.getContext(), "On this click",  Toast.LENGTH_SHORT).show();


//                    Fragment fragment = new DetailsFragment();
//                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.movie_fragment, fragment)
//                            .commit();

//                    Fragment mFragment = new DetailsFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", itemsList.get(i).getId());
//                    mFragment.setArguments(bundle);
//                    Log.d("hcc", "I am here after arg");

//                    switchContent(R.id.movie_fragment, mFragment);

//                    String id = itemsList.get(i).getId();
                    String id = (String) titleId.getText();
                    String type = (String) titleType.getText();

//                    Log.d("singleItem", String.valueOf(itemsList.get(i)));
                    Log.d("getId", id);
                    Log.d("getId", "type: " + type);
                    switchContent(id, type);
                    Log.d("itemlist", String.valueOf(i));
                }
            });


            // Referencing and Initializing the button
            button = (Button) view.findViewById(R.id.clickBtn);

            // Setting onClick behavior to the button
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Initializing the popup menu and giving the reference as current context
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), button);

                    // Inflating popup menu from popup_menu.xml file
                    popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getTitle().equals("Open in TMDB")) {

                            }


                            // Toast message on menu item clicked
                            Toast.makeText(view.getContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    // Showing the popup menu
                    popupMenu.show();
                }
            });


        }

        public void switchContent(String id, String type) {
            if (mContext == null)
                return;
            if (mContext instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) mContext;
//                Fragment frag = fragment;
//                Log.d("hcc", "I am here");
                Log.d("getId2", id);
                Log.d("getId2", "type: " + type);
                mainActivity.switchContent(id, type);
            }
        }




//        public void switchContent(int id, Fragment fragment) {
//            if (mContext == null)
//                return;
//            if (mContext instanceof MainActivity) {
//                MainActivity mainActivity = (MainActivity) mContext;
//                Fragment frag = fragment;
//                Log.d("hcc", "I am here");
//                mainActivity.switchContent(id, frag);
//            }
//        }

    }

}