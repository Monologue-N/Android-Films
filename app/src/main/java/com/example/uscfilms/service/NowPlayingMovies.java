package com.example.uscfilms.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class NowPlayingMovies {
    private final static String TAG = "[NowPlayingMovies] ";
    RequestQueue queue;
    JSONArray arr;


    public void getNowPlayingMovies(final VolleyCallback cb, Context cxt) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());

        // Instantiate the RequestQueue.
        String url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts";

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        arr = null;
                        try {
                            arr = response.getJSONArray("results");
                            Log.d(TAG, "getJSONArray: " + arr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < 6; i++) {
                            try {
                                JSONObject res = arr.getJSONObject(i);
                                String id = res.getString("id");
                                String backdrop_path = res.getString("backdrop_path");
                                String title = res.getString("title");
                                URL imgURL = new URL("https://image.tmdb.org/t/p/w500" + backdrop_path);
//                               Picasso.get().load(String.valueOf(imgURL)).into(image);
                                Log.d(TAG, "onResponse: " + backdrop_path);
                            } catch (JSONException | MalformedURLException e) {
                                Log.d(TAG, "JSONObject");
                                e.printStackTrace();
                            }

                        }
                        try {
                            cb.onSuccess(arr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        queue.add(request);
        Log.d(TAG, "JSONArr" + arr);
    }
}
