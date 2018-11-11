package com.example.first_task_k__r__o__s__h;

import android.app.Application;

public class AppContext extends Application {

    public static final String FIELD_TITLE="title";
    public static final String FIELD_NUMBER="number";
    public static final String FIELD_TEXT_NOTE="textNote";
    public static final String FIELD_CREATE_DATE="createDate";
    public static final String FIELD_LOGIN="login";
    public static final String FIELD_CONTEXT="context";
    public String getPrefsDir(){
        return getApplicationInfo().dataDir+"/"+"shared_prefs";
    }
}
