package com.example.uscfilms.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static final String BASE_URL = "http://localhost:8080/apis/posts";

    public static Retrofit newInstance(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
//    public static Retrofit newInstance(Context context) {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new MediaInterceptor())
//                .build();
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }

//    private static class HeaderInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request original = chain.request();
//            Request request = original
//                    .newBuilder()
//                    .header("X-Api-Key", API_KEY)
//                    .build();
//            return chain.proceed(request);
//        }
//    }
//
//    private static class MediaInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            Response response = chain.proceed(request);
//            return response;
//        }
//    }

}
