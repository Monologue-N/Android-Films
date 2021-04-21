package com.example.uscfilms.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uscfilms.R;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.model.SingleCast;
import com.example.uscfilms.model.SingleReview;
import com.example.uscfilms.model.SingleSearchResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultHolder> {

    private ArrayList<SingleSearchResult> resultList;
    private Context mContext;

    public SearchResultAdapter(Context context, ArrayList<SingleSearchResult> resultList) {
        this.mContext = context;
        this.resultList = resultList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ResultHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_layout, null);

        return new ResultHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ResultHolder reviewHolder, final int i) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        SingleSearchResult singleSearchResult = resultList.get(i);
        Picasso.get().load(singleSearchResult.getBackdrop_path()).into(reviewHolder.search_img);
        reviewHolder.search_img_mask.setBackground(Drawable.createFromPath("@drawable/gradient"));
        reviewHolder.search_img_mask.bringToFront();

        reviewHolder.search_type_year.setText(singleSearchResult.getType_year());
        reviewHolder.search_title.setText(singleSearchResult.getTitle());
        reviewHolder.search_rating.setText(singleSearchResult.getRating());

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (null != resultList ? resultList.size() : 0);
    }

    public static class ResultHolder extends RecyclerView.ViewHolder {

        protected ImageView search_img;

        protected ImageView search_img_mask;

        protected TextView search_type_year;

        protected TextView search_title;

        protected TextView search_rating;

        public ResultHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.search_img = view.findViewById(R.id.searchImg);
            this.search_img_mask = view.findViewById(R.id.searchImgMask);
            this.search_type_year = view.findViewById(R.id.search_type_year);
            this.search_title = view.findViewById(R.id.search_title);
            this.search_rating = view.findViewById(R.id.search_rating);

        }
    }
}
