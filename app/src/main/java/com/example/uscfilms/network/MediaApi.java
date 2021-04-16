package com.example.uscfilms.network;

import com.example.uscfilms.model.Media;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MediaApi {
    @GET("posts")
    Call<Media> getTrending(@Query("country") String country);

}
