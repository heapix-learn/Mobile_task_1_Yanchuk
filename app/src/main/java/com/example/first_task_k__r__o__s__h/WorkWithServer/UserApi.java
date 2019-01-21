package com.example.first_task_k__r__o__s__h.WorkWithServer;

import com.example.first_task_k__r__o__s__h.Authorization.AuthUserInfo;
import com.example.first_task_k__r__o__s__h.Authorization.User;
import com.example.first_task_k__r__o__s__h.Authorization.VerificationData;
import com.example.first_task_k__r__o__s__h.ConvertPostForServer;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.PostId;
import com.example.first_task_k__r__o__s__h.ServerAnswer;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {


    @POST("/signin")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkLogin(@Body AuthUserInfo info);

    @POST("/signup")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> SignUp(@Body User myUser);

    @POST("/sign-in-with-google")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> signInWithGoogle(@Body GoogleSignInAccount account);

    @POST("/sign-in-with-facebook")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> signInWithFacebook(@Body Profile profile);

    @POST("/send-verification-email")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkEmail(@Body User user);

    @POST("/send-verification-code")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkPhone(@Body User user);

    @POST("/check-verification")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkVerification(@Body VerificationData verificationData);

    @POST("/forgot-password")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> forgotPassword(@Body AuthUserInfo user);

    @POST("/get-user")
    @Headers({"Content-Type: application/json"})
    Call<User> getUser(@Query("access_token") String token);



    @POST("/add-post")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> addPost(@Body ConvertPostForServer post, @Query("access_token") String token);

    @POST("/edit-post")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> editPost(@Body ConvertPostForServer post, @Query("access_token") String token);

    @POST("/delete-post")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> deletePost(@Body ConvertPostForServer post, @Query("access_token") String token);

    @POST("/get-one-post-with-item-post-id")
    @Headers({"Content-Type: application/json"})
    Call<ConvertPostForServer> getOnePostWithItemPostId(@Body PostId postId, @Query("access_token") String token);


    @POST("/get-all-public-map-items")
    @Headers({"Content-Type: application/json"})
    Call<List<MapItem>> getAllPublicMapItems();

    @POST("/get-all-private-map-items")
    @Headers({"Content-Type: application/json"})
    Call<List<MapItem>> getAllPrivateMapItems(@Query("access_token") String token);

    @POST("/add-map-item")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> addMapItem(@Body MapItem mapItem, @Query("access_token") String token);

    @POST("/edit-map-item")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> editMapItem(@Body MapItem mapItem, @Query("access_token") String token);

    @POST("/delete-map-item")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> deleteMapItem(@Body MapItem mapItem, @Query("access_token") String token);

    @POST("/get-map-item-with-post")
    @Headers({"Content-Type: application/json"})
    Call<MapItem> getMapItemWith(@Body ConvertPostForServer post, @Query("access_token") String token);

}
