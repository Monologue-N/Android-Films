package com.example.uscfilms.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import com.example.uscfilms.R;
import com.bumptech.glide.Glide;
import com.example.uscfilms.model.SliderData;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final List<SliderData> mSliderItems;

    // Constructor
    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList) {
        this.mSliderItems = sliderDataArrayList;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final SliderData sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);


        // .transform(new BlurTransformation(25, 5))
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 5)))
                .into(viewHolder.imageViewBackgroundBlur);
    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;
        ImageView imageViewBackgroundBlur;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            imageViewBackgroundBlur = itemView.findViewById(R.id.myimageBlur);

            this.itemView = itemView;
        }
    }
}