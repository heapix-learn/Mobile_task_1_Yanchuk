package com.example.first_task_k__r__o__s__h;

import android.content.Context;

public class AuthManagerInterface {

    public interface AuthManagerInterfaceView<Result> {

        void onSuccess(Result result);

        void onError(String error);

    }

    public static void tryLoginWith(Context context, String login, String password, AuthManagerInterfaceView<UserModel> listener) {
        AuthManager.tryLoginWith(context, login, password, listener);

    }

//    public static void tryLoginWithGoogle(Activity activity, AuthManagerInterfaceView<UserModel> listener, Bundle bundle){
//        AuthManager authManager= new AuthManager();
//        authManager.tryLoginWithGoogle(activity, listener, bundle);
//    }
}
