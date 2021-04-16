package com.example.uscfilms.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.uscfilms.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";
    ImageView image;
    TextView textView;
    RequestQueue queue;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = (TextView) view.findViewById(R.id.trending_test);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        image = view.findViewById(R.id.trending_img);
        image.setImageBitmap(null);

        // Instantiate the RequestQueue.
        String url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts";

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array = null;
                        try {
                            array = response.getJSONArray("results");
                            Log.d(TAG, "getJSONArray: " + array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < 1; i++) {
                            try {
                                JSONObject result = array.getJSONObject(i);
                                String id = result.getString("id");
                                String backdrop_path = result.getString("backdrop_path");
                                String title = result.getString("title");
                                URL imgURL = new URL("https://image.tmdb.org/t/p/w500" + backdrop_path);
                                Bitmap bmp = BitmapFactory.decodeStream(imgURL.openConnection().getInputStream());
//                                textView.append(id + ", " + title);
                                image.setImageBitmap(bmp);
                                Log.d(TAG, "onResponse: " + backdrop_path);
                            } catch (JSONException | MalformedURLException e) {
                                Log.d(TAG, "JSONObject");
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                textView.setText("That didn't work!");

            }
        });
        queue.add(request);


//        textView = (TextView) view.findViewById(R.id.trending_test);
//
//// Instantiate the RequestQueue.
//        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        String url ="https://www.google.com";
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    // Display the first 500 characters of the response string.
//                    textView.setText("Response is: "+ response.substring(0,500));
//                }, error -> textView.setText("That didn't work!"));
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
        return view;

    }
}