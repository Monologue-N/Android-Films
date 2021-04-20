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

public class Details {
    private final static String TAG = "[Details] ";
    RequestQueue queue;
    JSONObject res;
    JSONArray arr;
    String type;


    public void getDetails(final VolleyCallback2 cb, Context cxt, String id, String type) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());
        this.type = type;

        String url;

        if (type.equals("movie")) {
            url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts/movieDetails/" + id;
        }
        else {
            url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts/tvDetails/" + id;
        }

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        res = null;
                        res = response;
                        Log.d(TAG, "getDetails: " + res);

                        try {
                            cb.onSuccess(res);
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
        Log.d(TAG, "JSONObject" + res);
    }


    public void getVideos(final VolleyCallback cb, Context cxt, String id, String type) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());

        this.type = type;
        Log.d("getId3", id);
        Log.d("getType3", type);

        String url;

        if (type.equals("movie")) {
            url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts/movieVideos/" + id;
        }
        else {
            url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts/tvVideos/" + id;
        }
        Log.d("searchid", id);

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        arr = null;
                        try {
                            arr = response.getJSONArray("results");
                            Log.d(TAG, "getVideos: " + arr);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
    }


    public void getCast(final VolleyCallback cb, Context cxt, String id, String type) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());

        this.type = type;
        Log.d("getId4", id);
        Log.d("getType4", type);

        String url;

        if (type.equals("movie")) {
            url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts/movieCast/" + id;
        }
        else {
            url = "https://sixth-starlight-308222.wn.r.appspot.com/apis/posts/tvshowCast/" + id;
        }
        Log.d("searchid", id);

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        arr = null;
                        try {
                            arr = response.getJSONArray("cast");
                            Log.d(TAG, "getCasts: " + arr);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
    }



}
