package com.example.uscfilms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {
    private TextView creationText;
    private TextView ratingText;
    private TextView reviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        this.getSupportActionBar().hide();

        Context cxt = getApplicationContext();
        String creation = getIntent().getStringExtra("creation");
        String rating = getIntent().getStringExtra("rating");
        String review = getIntent().getStringExtra("review");

        creationText = findViewById(R.id.review_creation);
        ratingText = findViewById(R.id.review_rating);
        reviewText = findViewById(R.id.review_review);

        creationText.setText(creation);
        ratingText.setText(rating);
        reviewText.setText(review);

    }
}