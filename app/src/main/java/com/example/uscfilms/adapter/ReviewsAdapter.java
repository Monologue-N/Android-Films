package com.example.uscfilms.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uscfilms.DetailsActivity;
import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.ReviewActivity;
import com.example.uscfilms.model.SingleCard;
import com.example.uscfilms.model.SingleCast;
import com.example.uscfilms.model.SingleReview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private ArrayList<SingleReview> reviewList;
    private Context mContext;
    private Activity mActivity;

    public ReviewsAdapter(Context context, ArrayList<SingleReview> reviewList, Activity activity) {
        this.mContext = context;
        this.reviewList = reviewList;
        this.mActivity = activity;
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

        reviewHolder.fullReview.setText(singleReview.getReview());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (null != reviewList ? reviewList.size() : 0);
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        protected TextView creation;

        protected TextView rating;

        protected TextView review;

        protected TextView fullReview;

        protected View card;


        public ReviewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.creation = view.findViewById(R.id.review_creation);
            this.rating = view.findViewById(R.id.review_rating);
            this.review = view.findViewById(R.id.review_review);
            this.fullReview = view.findViewById(R.id.full_review);
            this.card = view.findViewById(R.id.review_card);

            card.setClickable(true);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(view.getContext(), "on click review", Toast.LENGTH_SHORT).show();
                    String creationText = (String) creation.getText();
                    String ratingText = (String) rating.getText();
                    String reviewText = fullReview.getText().toString();
                    switchToReview(creationText, ratingText, reviewText);
                    Log.d("switch777", "mContext is " + mContext);
//                    Intent intent = new Intent(mContext.getApplicationContext(), ReviewActivity.class);
//                    Log.d("switch777", "to review");
//                    intent.putExtra("creation", creationText);
//                    intent.putExtra("rating", ratingText);
////        intent.putExtra("review", review);
//                    mContext.getApplicationContext().startActivity(intent);

                }
            });

            review.setClickable(true);
            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(view.getContext(), "on click review", Toast.LENGTH_SHORT).show();
                    String creationText = (String) creation.getText();
                    String ratingText = (String) rating.getText();
                    String reviewText = fullReview.getText().toString();
                    switchToReview(creationText, ratingText, reviewText);
                    Log.d("switch777", "mContext is " + mContext);
//                    Intent intent = new Intent(mContext.getApplicationContext(), ReviewActivity.class);
//                    Log.d("switch777", "to review");
//                    intent.putExtra("creation", creationText);
//                    intent.putExtra("rating", ratingText);
////        intent.putExtra("review", review);
//                    mContext.getApplicationContext().startActivity(intent);

                }
            });

        }

        public void switchToReview(String creationText, String ratingText, String reviewText) {
            Log.d("switch777", "switch to review1");
//            if (mContext == null)
//                return;
            Log.d("switch777", "mContext2 is " + mContext);
            boolean flag = mContext instanceof DetailsActivity;

            Log.d("switch777", "isInstance: " + flag);
            Log.d("switch777", "isInstance: " + mContext.getApplicationContext());

            Intent intent = new Intent(mActivity, ReviewActivity.class);
            Log.d("switch777", "to review");
            intent.putExtra("creation", creationText);
            intent.putExtra("rating", ratingText);
            intent.putExtra("review", reviewText);

//        intent.putExtra("review", review);
            mActivity.startActivity(intent);

//            if (mContext instanceof DetailsActivity) {
//                Log.d("switch777", "666switch to review");
//                DetailsActivity detailsActivity = (DetailsActivity) mContext;
//                detailsActivity.switchToReview(creationText, ratingText);
//            }

//            if (mContext == null)
//                return;
//            if (mContext instanceof MainActivity) {
//                MainActivity mainActivity = (MainActivity) mContext;
//                mainActivity.switchToReview(creationText, ratingText);
//            }
        }

    }
}
