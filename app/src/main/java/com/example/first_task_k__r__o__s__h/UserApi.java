package com.example.first_task_k__r__o__s__h;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/accounts")
    Call<List<UserModel>> checkLogin(@Query("username") String resourceName);

    @GET("/accounts")
    Call<List<UserModel>> getAll();


    @POST("/accounts")
    Call<UserModel> pushLogin(@Body UserModel user);
}
