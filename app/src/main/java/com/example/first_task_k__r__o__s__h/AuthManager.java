package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.first_task_k__r__o__s__h.WorkWithServer.Controller;
import com.example.first_task_k__r__o__s__h.WorkWithServer.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManager extends AppCompatActivity implements AuthManagerInterface.AuthManagerInterfaceView<UserModel> {

//    public static int  RC_SIGN_IN = 119;
//    private static GoogleSignInOptions gso;
//    private static GoogleSignInClient mGoogleSignInClient;
//    private static Activity mActivity;
//    private  AuthManagerInterface.AuthManagerInterfaceView mListener;
//    private Bundle mBundle;

    @Override
    public void onSuccess(UserModel userModel) {

    }

    @Override
    public void onError(String error) {

    }


    static void tryLoginWith(final Context context, String login, String password, final AuthManagerInterface.AuthManagerInterfaceView<UserModel> listener){

        checkUserName(context, login, password, listener);
    }

    private static void checkUserName(final Context context, final String userName, final String password, final AuthManagerInterface.AuthManagerInterfaceView<UserModel> listener){
        final UserApi userApi=Controller.getApi();

        userApi.checkLoginUserName(userName).enqueue(new Callback<List<UserModel>>() {

            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                int result = -1;
                UserModel myUser = null;
                List<UserModel> accounts = new ArrayList<>();

                accounts.addAll(response.body());
                if (accounts.size() != 0) {
                    myUser = accounts.get(0);
                    if ((myUser.getPassword().equals(password)) && (myUser.getUserName().equals(userName)))
                        result = AppContext.SUCCESS_LOGIN;
                    accounts.clear();
                }

                if (result == AppContext.SUCCESS_LOGIN) {
                    listener.onSuccess(myUser);
                    return;
                }
                checkPhone(context, userName, password, listener);
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    private static void checkPhone(final Context context, final String phone, final String password, final AuthManagerInterface.AuthManagerInterfaceView<UserModel> listener){
        final UserApi userApi=Controller.getApi();

        userApi.checkLoginPhone(phone).enqueue(new Callback<List<UserModel>>() {

            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                int result = -1;
                UserModel myUser = null;
                List<UserModel> accounts = new ArrayList<>();

                accounts.addAll(response.body());
                if (accounts.size() != 0) {
                    myUser = accounts.get(0);
                    if ((myUser.getPassword().equals(password)) && (myUser.getPhone().equals(phone)))
                        result = AppContext.SUCCESS_LOGIN;
                    accounts.clear();
                }

                if (result == AppContext.SUCCESS_LOGIN) {
                    listener.onSuccess(myUser);
                    return;
                }
                checkEmail(context, phone, password, listener);
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

    private static void checkEmail(final Context context, final String email, final String password, final AuthManagerInterface.AuthManagerInterfaceView<UserModel> listener){
        final UserApi userApi=Controller.getApi();
        userApi.checkLoginEmail(email).enqueue(new Callback<List<UserModel>>() {

            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                int result = -1;
                UserModel myUser = null;
                List<UserModel> accounts = new ArrayList<>();
                accounts.addAll(response.body());
                if (accounts.size() != 0) {
                    myUser = accounts.get(0);
                    if ((myUser.getPassword().equals(password)) && (myUser.getEmail().equals(email)))
                        result = AppContext.SUCCESS_LOGIN;
                    accounts.clear();
                }

                if (result == AppContext.SUCCESS_LOGIN) {
                    listener.onSuccess(myUser);
                    return;
                }
                listener.onError(context.getString(R.string.error_incorrect_login_password));
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });
    }

//    void tryLoginWithGoogle(Activity activity, AuthManagerInterface.AuthManagerInterfaceView listener, Bundle bundle) {
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        mListener = listener;
//        mActivity = activity;
//        mBundle = bundle;
//        mGoogleSignInClient = GoogleSignIn.getClient(mActivity, gso);
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN, mBundle);
//
//    }
//
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
////            else {
////        callbackManager.onActivityResult(requestCode, resultCode, data);
////        }
//    }
//
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            String googleId = account.getId();
//            UserModel myUser = new UserModel();
//            myUser.setId(googleId);
//            mListener.onSuccess(myUser);
//            signOut();
//        } catch (ApiException e) {
//        }
//    }
//
//    private void signOut() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(mActivity, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                    }
//                });
//    }


}