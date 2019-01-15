package com.example.first_task_k__r__o__s__h;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;


public class Store implements StoreInterface {

    private static final String SAVED_LOGIN = "saved_login";
    private static final String SAVED_TOKEN = "saved_token";
    private static final String SAVED_USER = "saved_user";
    private SharedPreferences preferences;
    private static Store instance;

    private Store(){
        preferences = AppContext.getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }
    public static Store getInstance(){
        if (instance ==null){
            instance = new Store();
        }
        return instance;
    }

    @Override
    public void saveLogin(String login) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_LOGIN, login);
        editor.apply();
    }

    @Override
    public void saveUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(SAVED_USER, json);
        editor.apply();
    }

    @Override
    public void saveToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_TOKEN, token);
        editor.apply();
    }

    @Override
    public User getUser() {
        Gson gson = new Gson();
        String json = preferences.getString(SAVED_USER, "");
        return gson.fromJson(json, User.class);
    }

    @Override
    public String getToken() {
        return preferences.getString(SAVED_TOKEN, "");
    }


    @Override
    public String getLogin() {
        return preferences.getString(SAVED_LOGIN, "");
    }

}
