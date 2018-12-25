package com.example.first_task_k__r__o__s__h;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/Accounts")
    Call<List<UserModel>> checkLogin(@Query("username") String resourceName);

    @POST("/Accounts")
    Call<UserModel> pushNewUser(@Body UserModel user);

    @GET("/Number_of_accounts/{id}")
    Call<NumberOfAccounts> getLastAccountNumber(@Path("id") String id);

    @DELETE("/Number_of_accounts/{id}")
    Call<ResponseBody> deleteLastAccountNumber(@Path("id") String id);

    @POST("/Number_of_accounts")
    Call<NumberOfAccounts> pushLastAccountNumber(@Body NumberOfAccounts numberOfAccounts);

    @GET("/Posts/{id}")
    Call<ConvertToDoDocuments> getPostFromId(@Path("id") String id);

    @GET("/Number_of_posts/{id}")
    Call<NumberOfPosts> getNumberOfPosts(@Path("id") String id);



    @POST("/Number_of_posts")
    Call<ResponseBody> pushNumberOfPosts(@Body NumberOfPosts size);



    @GET("/Markers")
    Call<List<OwnMarker>> getMarkersFromAccountId(@Query("accountId") String resourceName);

    @GET("/Markers")
    Call<List<OwnMarker>> getMarkersFromAccess(@Query("access") String resourceName);



    @DELETE("/Posts/{id}")
    Call<ResponseBody> deletePost(@Path("id") String id);

    @POST("/Posts/")
    Call<ResponseBody> pushPost(@Body ConvertToDoDocuments convertToDoDocuments);

    @DELETE("/Markers/{postId}")
    Call<ResponseBody> deleteMarker(@Path("postId") String id);

    @POST("/Markers/")
    Call<ResponseBody> pushMarker(@Body OwnMarker ownMarker);

    @GET("/Markers/{postId}")
    Call<OwnMarker> getMarkerFromPostId(@Path("postId") String id);


    @DELETE("/Number_of_posts/{id}/")
    Call<ResponseBody> deleteNumberOfPosts(@Path("id") String id);
}
