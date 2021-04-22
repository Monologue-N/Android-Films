package com.example.uscfilms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.uscfilms.ui.details.DetailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "lifecycle";
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.nav_view).setVisibility(View.VISIBLE);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    //    public void switchContent(int id, Fragment fragment) {
    public void switchContent(String id, String type) {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        Fragment df = new DetailsFragment();
//        Fragment old = getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
////        ft.remove(old);
//        ft.replace(R.id.main_activity, fragment);
//        // to keep the previous one
//        ft.addToBackStack(null);
//        Log.d("hcc", "I am here in main");
//        ft.commit();
//        findViewById(R.id.nav_view).setVisibility(View.GONE);
////      findViewById(R.id.nav_view).setVisibility(View.GONE);
//
//        Log.d("hcc", "I am here after commit");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
//
//    }


    public void goToTMDB() {
        Log.d("tmdb", "I am here in tmdb");
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/"));
        startActivity(i);
    }

    public void goToTMDBWithId(String id, String type) {
        Log.d("tmdb", "I am here in tmdbWithId");
        String url = "https://www.themoviedb.org/" + type + "/" + id;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
    }

    public void goToFB(String id, String type) {
        Log.d("tmdb", "I am here in fb");
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
        startActivity(i);
    }
    public void goToTwitter(String id, String type) {
        Log.d("tmdb", "I am here in twitter");
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"));
        startActivity(i);
    }


    public void refreshWatchlist() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Log.d("findFrag", "-" + currentFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d("findFragTrans", "-" + fragmentTransaction);

        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshWatchlist();
    }
}