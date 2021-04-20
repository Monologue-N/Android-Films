package com.example.uscfilms.adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private ArrayList<SingleReview> reviewList;
    private Context mContext;

    public ReviewsAdapter(Context context, ArrayList<SingleReview> reviewList) {
        this.mContext = context;
        this.reviewList = reviewList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, null);

        return new ReviewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ReviewHolder reviewHolder, final int i) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        SingleReview singleReview = reviewList.get(i);

        reviewHolder.creation.setText(singleReview.getCreation());
        reviewHolder.rating.setText(singleReview.getRating());
        reviewHolder.review.setText(singleReview.getReview());

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (null != reviewList ? reviewList.size() : 0);
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder {

        protected TextView creation;

        protected TextView rating;

        protected TextView review;

        public ReviewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.creation = view.findViewById(R.id.review_creation);
            this.rating = view.findViewById(R.id.review_rating);
            this.review = view.findViewById(R.id.review_review);

        }
    }
}
