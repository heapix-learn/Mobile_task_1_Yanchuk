package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;


public class Store implements AuthStoreInterface {

    private static final String SAVED_LOGIN = "saved_login";
    private static final String SAVED_TOKEN = "saved_token";
    private static final String SAVED_USER = "saved_user";
    private String login;
    private String token;
    private User user;
    private SharedPreferences preferences;

    @Override
    public void setContext(Context context){ 
        preferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        this.login = preferences.getString(SAVED_LOGIN, "");
        this.token = preferences.getString(SAVED_TOKEN, "");
        Gson gson = new Gson();
        String json = preferences.getString(SAVED_USER, "");
        this.user = gson.fromJson(json, User.class);

    }

    @Override
    public void saveLogin(String login) {
        this.login=login;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_LOGIN, login);
        editor.apply();
    }

    @Override
    public void saveUser(User user) {
        this.user=user;
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(SAVED_USER, json);
        editor.apply();
    }

    @Override
    public void saveToken(String token) {
        this.token=token;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_TOKEN, token);
        editor.apply();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getToken() {
        return token;
    }


    @Override
    public String getLogin() {
        return login;
    }

}
