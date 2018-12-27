package com.example.first_task_k__r__o__s__h.WorkWithServer;

import com.example.first_task_k__r__o__s__h.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(UserApi.class);
        return service;

    }
    public static void pushNewUser(UserModel user, Callback<UserModel> callback) {
        Call<UserModel> userCall = service.pushNewUser(user);
        userCall.enqueue(callback);
    }
}