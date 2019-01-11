package com.example.first_task_k__r__o__s__h;

import android.app.Application;

import java.util.ArrayList;
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
    public static final String OWN_MARKER="OwnMarker";

    public enum TypeOfAuthManagerError{
        SERVER_ERROR("Server error"),
        USER_CHECK_ERROR("Not authorized."),
        WRONG_CREDENTIALS("Wrong credentials"),
        SUCH_EMAIL_ALREADY_REGISTERED("Such email already registered."),
        ACCESS_DENIED("Access denied");

        private String description;

        TypeOfAuthManagerError(String description) {
            this.description = description;
        }

        public String getDescription() {return description;}
    }

    public static String ListPathToString(List<String> list){
        if (list.size()==0) return "";
        StringBuilder ans= new StringBuilder();
        for (int i=0; i<list.size(); i++){
            ans.append(list.get(i)).append("&&");
        }
        return ans.toString();
    }

    public static List<String>FromStringToList(String str){
        List<String> ans = new ArrayList<>();
        StringBuilder help= new StringBuilder();
        for (int i=0; i<str.length()-1; i++){
            if (str.charAt(i)=='&' && str.charAt(i+1)=='&'){
                ans.add(help.toString());
                help = new StringBuilder();
                i++;
            }
            else help.append(str.charAt(i));
        }
        return ans;
    }

    public static TypeOfAuthManagerError convertError(String str){
        if (TypeOfAuthManagerError.ACCESS_DENIED.getDescription().equals(str))  return TypeOfAuthManagerError.ACCESS_DENIED;
        if (TypeOfAuthManagerError.SERVER_ERROR.getDescription().equals(str))  return TypeOfAuthManagerError.SERVER_ERROR;
        if (TypeOfAuthManagerError.SUCH_EMAIL_ALREADY_REGISTERED.getDescription().equals(str))  return TypeOfAuthManagerError.SUCH_EMAIL_ALREADY_REGISTERED;
        if (TypeOfAuthManagerError.USER_CHECK_ERROR.getDescription().equals(str))  return TypeOfAuthManagerError.USER_CHECK_ERROR;
        if (TypeOfAuthManagerError.WRONG_CREDENTIALS.getDescription().equals(str))  return TypeOfAuthManagerError.WRONG_CREDENTIALS;
        return null;
    }

}
