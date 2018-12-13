package com.example.first_task_k__r__o__s__h;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/accounts")
    Call<List<UserModel>> checkLogin(@Query("username") String resourceName);

    @POST("/accounts")
    Call<UserModel> pushLogin(@Body UserModel user);

    @GET("/size_of_accounts/{id}")
    Call<SizeOfAccounts> getSize(@Path("id") String id);

    @DELETE("/size_of_accounts/{id}")
    Call<ResponseBody> deleteSize(@Path("id") String id);

    @POST("/size_of_accounts")
    Call<SizeOfAccounts> pushSize(@Body SizeOfAccounts size);

    @GET("/notes/{id}")
    Call<ConvertToDoDocuments> getNoteFromId(@Path("id") String id);

    @GET("/size_of_notes/{id}")
    Call<SizeOfAccounts> getSizeOfNotes(@Path("id") String id);

    @DELETE("/size_of_notes/{id}")
    Call<ResponseBody> deleteSizeOfNotes(@Path("id") String id);

    @POST("/size_of_notes")
    Call<ResponseBody> pushSizeOfNotes(@Body SizeOfAccounts size);

    @GET("/notes")
    Call<List<ConvertToDoDocuments>> getNotesLogin(@Query("login") String resourceName);

    @GET("/notes")
    Call<List<ConvertToDoDocuments>> getNotesAccess(@Query("access") String resourceName);

    @DELETE("/notes/{id}")
    Call<ResponseBody> deleteNote(@Path("id") String id);

    @POST("/notes")
    Call<ResponseBody> pushNote(@Body ConvertToDoDocuments user);
}
