package com.example.first_task_k__r__o__s__h;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/accounts")
    Call<List<UserModel>> checkLogin(@Query("username") String resourceName);

    @GET("/size_of_accounts/{id}")
    Call<SizeOfAccounts> getSize(@Path("id") String id);

    @DELETE("/size_of_accounts/{id}")
    Call<ResponseBody> deleteSize(@Path("id") String id);

    @POST("/size_of_accounts")
    Call<SizeOfAccounts> pushSize(@Body SizeOfAccounts size);

    @POST("/accounts")
    Call<UserModel> pushLogin(@Body UserModel user);

}
