package com.example.uscfilms.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        holder.titleTitle.setText(singleItem.getTitle());
        holder.titlePosterPath.setText(singleItem.getBackdrop_path());
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

        protected TextView titleTitle;

        protected TextView titlePosterPath;


        public SingleItemRowHolder(View view, int i) {
            super(view);

            this.titleId = (TextView) view.findViewById(R.id.titleId);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.itemImageMask = view.findViewById(R.id.itemImageMask);
            this.titleType = view.findViewById(R.id.titleType);
            this.titleTitle = view.findViewById(R.id.titleTitle);
            this.titlePosterPath = view.findViewById(R.id.titlePosterPath);

            itemImage.bringToFront();
//
            itemImage.setClickable(true);

            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click", "clicked");
//                    Toast.makeText(v.getContext(), "On this click",  Toast.LENGTH_SHORT).show();


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

                    String id = (String) titleId.getText();
                    String type = (String) titleType.getText();
                    String title = (String) titleTitle.getText();
                    String poster_path = (String) titlePosterPath.getText();

                    // Inflating popup menu from popup_menu.xml file
                    popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                    MenuItem checkItem = popupMenu.getMenu().getItem(3);
                    boolean added = false;
                    SharedPreferences sharedPref = view.getContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE);
//                    String ifAdded = sharedPref.getString(id, "");
//                    Log.d("checkadd", ifAdded);
//                    added = (ifAdded != null && !ifAdded.isEmpty());

                    String prev = sharedPref.getString("list", "");

                    if (prev != null && !prev.isEmpty()) {
                        try {
                            JSONArray prev_arr = new JSONArray(prev);
                            for (int i = 0; i < prev_arr.length(); i++) {
                                JSONObject obj = prev_arr.getJSONObject(i);
                                if (obj.getString("id").equals(id)) {
                                    added = true;
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    if (added) {
                        checkItem.setTitle("Remove from Watchlist");
                    }
                    else {
                        checkItem.setTitle("Add to Watchlist");
                    }


                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            if (menuItem.getTitle().equals("Open in TMDB")) {
                                if (mContext == null)
                                    return false;
                                if (mContext instanceof MainActivity) {
                                    MainActivity mainActivity = (MainActivity) mContext;
                                    mainActivity.goToTMDBWithId(id, type);
                                }
                            }
                            else if (menuItem.getTitle().equals("Add to Watchlist")) {
                                Toast.makeText(view.getContext(), title + " was added to Watchlist" , Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPref = view.getContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                String prev = sharedPref.getString("list", "");
                                try {
                                    JSONArray prevArray;
                                    if (prev != null && !prev.isEmpty()) {
                                        Log.d("222watchlist", "-" + prev);
                                        prevArray = new JSONArray(prev);
                                        Log.d("222watchlist", "-" + prevArray);
                                    }
                                    else {
                                        prevArray = new JSONArray();
                                    }

                                    String nothing = "{}";
                                    JSONObject info = null;
                                    try {
                                        info = new JSONObject(nothing);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        info.put("id", id);
                                        info.put("type", type);
                                        info.put("poster_path", poster_path);
                                        info.put("title", title);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    prevArray.put(info);
//                                    JSONObject info_obj = new JSONObject();
//                                    info_obj.put("info", prevArray);

                                    editor.putString("list", prevArray.toString());
                                    editor.apply();
                                    menuItem.setTitle("Remove from Watchlist");
                                    Log.d("watchlist", "-" + editor);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            else if (menuItem.getTitle().equals("Remove from Watchlist")) {
                                Toast.makeText(view.getContext(), title + " was removed from Watchlist" , Toast.LENGTH_SHORT).show();

                                SharedPreferences sharedPref = mContext.getSharedPreferences("watchlist", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                String prev = sharedPref.getString("list", "");
                                try {
                                    JSONArray prev_arr = new JSONArray(prev);
                                    for (int i = 0; i < prev_arr.length(); i++) {
                                        JSONObject obj = prev_arr.getJSONObject(i);
                                        if (obj.getString("id").equals(id)) {
                                            prev_arr.remove(i);
                                            break;
                                        }
                                    }
                                    editor.putString("list", prev_arr.toString());
                                    editor.apply();
                                    menuItem.setTitle("Add to Watchlist");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            else if (menuItem.getTitle().equals("Share on Facebook")) {
                                if (mContext == null)
                                    return false;
                                if (mContext instanceof MainActivity) {
                                    MainActivity mainActivity = (MainActivity) mContext;
                                    mainActivity.goToFB(id, type);
                                }
                            }
                            else if (menuItem.getTitle().equals("Share on Twitter")) {
                                if (mContext == null)
                                    return false;
                                if (mContext instanceof MainActivity) {
                                    MainActivity mainActivity = (MainActivity) mContext;
                                    mainActivity.goToTwitter(id, type);
                                }
                            }


                            // Toast message on menu item clicked
//                            Toast.makeText(view.getContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
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