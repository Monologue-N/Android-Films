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

public class Details {
    private final static String TAG = "[Details] ";
    RequestQueue queue;
    JSONObject res;

    public void getDetails(final VolleyCallback2 cb, Context cxt, String id, String type, String category) {
        queue = Volley.newRequestQueue(cxt.getApplicationContext());
        String root = "https://mono-hw9-backend.uk.r.appspot.com/apis/posts/";
        String url = root + type + "/" + category + "/" + id;

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
