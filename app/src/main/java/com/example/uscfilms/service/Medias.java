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

public class Medias {
    private final static String TAG = "[Medias] ";
    RequestQueue queue;
    JSONArray arr;


    public void getMedia(final VolleyCallback cb, Context cxt, String type, String category) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());
        String root = "https://mono-hw9-backend.uk.r.appspot.com/apis/posts/";
        String url = root + type + "/" + category;


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
                        try {
                            cb.onSuccess(arr);
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

    public void getRecommended(final VolleyCallback cb, Context cxt, String type, String category, String id) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());
        String root = "https://mono-hw9-backend.uk.r.appspot.com/apis/posts/";
        String url = root + type + "/" + category + "/" + id;


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
                        try {
                            cb.onSuccess(arr);
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
