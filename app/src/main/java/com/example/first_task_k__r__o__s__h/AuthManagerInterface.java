package com.example.first_task_k__r__o__s__h;

import android.content.Context;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface AuthManagerInterface {
    void tryLoginWith(String login, String password, Runnable onSuccess, MyRunnable onFailure);
    void tryLoginWithGoogle(GoogleSignInAccount account, Runnable onSuccess, MyRunnable onFailure);
    void tryLoginWithFacebook(Profile account, Runnable onSuccess, MyRunnable onFailure);
    void tryLoginWithStoredInfo(Context context, Runnable onSuccess, Runnable onFailure);
    String getStoredLogin(Context context);
    void tryRegistrationWith(String login, String password, String firstName, String LastName, Runnable onSuccess, MyRunnable onFailure);
}
