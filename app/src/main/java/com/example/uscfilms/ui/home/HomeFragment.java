package com.example.uscfilms.ui.home;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.adapter.SectionPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.fragment.app.FragmentStatePagerAdapter;

public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";
    private static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
    private NavController navController;
    private View view;
    ViewPager viewPager;
    TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) requireActivity()).getSupportActionBar().hide();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
//        NavigationView navView = view.findViewById(R.id.home_nav_view);
//        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.home_nav_host_fragment);
////        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.home_nav_host_fragment);
//        navController = navHostFragment.getNavController();
//        NavigationUI.setupWithNavController(navView, navController);
//        NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController);


        return view;

    }

    // Call on Activity Created method

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        // set tab text color (default, selected)
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#256eb4") );

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                tabLayout.setSelectedTabIndicator(R.color.lake_blue);
//                int darkBlue = ContextCompat.getColor(getContext(), R.color.dark_blue);
//                tab.getIcon().setColorFilter(darkBlue, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new MovieFragment(), "Movies");
        adapter.addFragment(new TvShowFragment(), "TV Shows");

        viewPager.setAdapter(adapter);

    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        navController = Navigation.findNavController(view);
//        view.findViewById(R.id.home_nav_view).findViewById(R.id.navigation_movies).setOnClickListener((View.OnClickListener) this);
//        view.findViewById(R.id.home_nav_view).findViewById(R.id.navigation_tv_shows).setOnClickListener((View.OnClickListener) this);
//    }

    //    @Override
//    public boolean onSupportNavigateUp() {
//        return navController.navigateUp();
//    }
//    public void onClick(View view) {
//
//    }


}