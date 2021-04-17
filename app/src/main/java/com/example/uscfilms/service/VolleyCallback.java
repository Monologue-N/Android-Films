package com.example.uscfilms.service;

import org.json.JSONArray;
import org.json.JSONException;

public interface VolleyCallback {
    void onSuccess(JSONArray res) throws JSONException;
}
