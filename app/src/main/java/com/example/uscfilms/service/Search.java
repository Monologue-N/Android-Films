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
import java.text.ParseException;

public class Search {
    private final static String TAG = "[Details] ";
    RequestQueue queue;
    JSONArray res;

    public void getSearchResults(final VolleyCallback cb, Context cxt, String query) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());
        String root = "https://mono-hw9-backend.uk.r.appspot.com/apis/posts/";
        String url = root + "search" + "/" + query;

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        res = null;
                            try {
                                res = response.getJSONArray("results");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        Log.d(TAG, "getSearchResults: " + res);

                        try {
                            cb.onSuccess(res);
                        } catch (JSONException | ParseException e) {
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
    }

}

