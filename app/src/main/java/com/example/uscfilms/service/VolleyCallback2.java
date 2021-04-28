package com.example.uscfilms.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public interface VolleyCallback2 {
    void onSuccess(JSONObject res) throws JSONException, ParseException;

}
