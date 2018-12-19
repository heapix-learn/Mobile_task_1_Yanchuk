package com.example.first_task_k__r__o__s__h;

import android.app.Application;

import java.util.List;

public class AppContext extends Application {

    public static final String TODO_DOCUMENT="ToDoDocument";
    public static final int TODO_NOTE_REQUEST=1;
    public static final String PHOTOS_URL="photoSURL";
    public static final String VIDEO_URL="videoURL";
    public static final String POSITION="position";
    public static final int DELETE_POST_REQUEST=2;
    public static final String GET_POST_ID="getPostId";
    public static final int TODO_NOTE_SETTING_REQUEST=3;
    public static final int EDIT_POST_REQUEST=4;


    public static String ListPathToString(List<String> list){
        if (list.size()==0) return "";
        StringBuilder ans= new StringBuilder();
        for (int i=0; i<list.size(); i++){

            ans.append(list.get(i)).append("&&");
        }
        return ans.toString();
    }

}
