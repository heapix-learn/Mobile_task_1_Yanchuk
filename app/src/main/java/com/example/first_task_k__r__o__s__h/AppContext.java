package com.example.first_task_k__r__o__s__h;

import android.app.Application;

public class AppContext extends Application {
    private static AppContext mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static AppContext getContext() {
        return mContext;
    }
}
