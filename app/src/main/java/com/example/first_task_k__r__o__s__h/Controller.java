package com.example.first_task_k__r__o__s__h;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    static final String BASE_URL = "http://localhost:3000/";
    private static UserApi service;

    public static UserApi getApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(UserApi.class);
        return service;

    }
    public static void pushLogin(UserModel user, Callback<UserModel> callback) {
        Call<UserModel> userCall = service.pushLogin(user);
        userCall.enqueue(callback);
    }
}