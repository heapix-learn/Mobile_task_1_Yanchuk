package com.example.first_task_k__r__o__s__h;

import android.content.Context;

public class AuthManager implements AuthManagerInterface<UserModel> {

    @Override
    public void onSuccess(UserModel userModel) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(String error) {

    }

    public static void tryLoginWith(Context context, String login, String password, AuthManagerInterface listener) {
        AuthManagerSystem.tryLoginWith(context, login, password, listener);
    }


}