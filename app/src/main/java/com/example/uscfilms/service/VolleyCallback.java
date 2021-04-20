package com.example.uscfilms.service;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;

public interface VolleyCallback {
    void onSuccess(JSONArray res) throws JSONException, ParseException;
}
