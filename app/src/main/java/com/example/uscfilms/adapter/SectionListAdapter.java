package com.example.uscfilms.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

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

//        holder.tvTitle.setText(singleItem.getTitle());
        Picasso.get().load(singleItem.getBackdrop_path()).into(holder.itemImage);


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

        protected TextView tvTitle;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view, int i) {
            super(view);

//            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);

//            itemImage.bringToFront();
//
//            itemImage.setClickable(true);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click", "clicked");
                    Toast.makeText(v.getContext(), "On this click",  Toast.LENGTH_SHORT).show();


//                    Fragment fragment = new DetailsFragment();
//                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.movie_fragment, fragment)
//                            .commit();

                    Fragment mFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", itemsList.get(i).getId());
                    mFragment.setArguments(bundle);
                    Log.d("hcc", "I am here after arg");

                    switchContent(R.id.movie_fragment, mFragment);


                }
            });


        }

        public void switchContent(int id, Fragment fragment) {
            if (mContext == null)
                return;
            if (mContext instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) mContext;
                Fragment frag = fragment;
                Log.d("hcc", "I am here");
                mainActivity.switchContent(id, frag);
            }
        }

    }

}