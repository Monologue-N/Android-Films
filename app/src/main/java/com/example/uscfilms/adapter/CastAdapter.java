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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    private ArrayList<SingleCast> castList;
    private Context mContext;

    public CastAdapter(Context context, ArrayList<SingleCast> castList) {
        this.mContext = context;
        this.castList = castList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CastHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_layout, null);

        return new CastHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CastHolder castHolder, final int i) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        SingleCast singleCast = castList.get(i);

        Picasso.get().load(singleCast.getProfile_path()).into(castHolder.castImage);
        castHolder.castName.setText(singleCast.getName());
    }




    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (null != castList ? castList.size() : 0);
    }

    public static class CastHolder extends RecyclerView.ViewHolder {

        protected ImageView castImage;

        protected TextView castName;

        public CastHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.castImage = view.findViewById(R.id.cast_img);
            this.castName = view.findViewById(R.id.cast_name);
        }
    }
}
