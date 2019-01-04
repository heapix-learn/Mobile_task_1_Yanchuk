package com.example.first_task_k__r__o__s__h.WorkWithServer;

import android.os.AsyncTask;

import com.example.first_task_k__r__o__s__h.AppContext;
import com.example.first_task_k__r__o__s__h.ConvertToDoDocuments;
import com.example.first_task_k__r__o__s__h.OwnMarker;
import com.example.first_task_k__r__o__s__h.ToDoDocuments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class DBPosts {

    private final static UserApi userApi=Controller.getApi();

    public static boolean addPost(ToDoDocuments queryValues){
        final boolean[] flag = {true};
        ConvertToDoDocuments values = new ConvertToDoDocuments();
        values.setTitle(queryValues.getTitle());
        values.setNumber(String.valueOf(queryValues.getNumber()));
        values.setCreateDate(String.valueOf(queryValues.getCreateDate().getTime()));
        values.setId(queryValues.getId());
        values.setAccountId(queryValues.getAccountId());
        values.setTextNote(queryValues.getTextNote());
        values.setImagePath(queryValues.ImagePathToString());
        values.setLocation(queryValues.getLocation());
        values.setAccess(String.valueOf(queryValues.getAccess()));
        values.setVideoPath(queryValues.VideoPathToString());
        values.setVideoScreen(queryValues.VideoScreenToString());
        values.setNameLocation(queryValues.getNameLocation());
        userApi.pushPost(values).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                flag[0] =false;
            }
        });
        return flag[0];
    }

    public static boolean addMarker(OwnMarker ownMarker){
        final boolean[] flag = {true};

        userApi.pushMarker(ownMarker).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                flag[0] =false;
            }
        });
        return flag[0];
    }

    public static List<OwnMarker> getMarkersAllMy(String accountId){
        List<OwnMarker> convertRead;

        try {
            convertRead=(new DownloadMyMarkersFromAccountId().execute(accountId)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            convertRead=null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            convertRead=null;
        }

        return convertRead;
    }
    public static List<OwnMarker> getMarkersAllPublic(){
        List<OwnMarker> convertRead;

        try {
            convertRead=(new DownloadMyMarkersFromAccess().execute("0")).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            convertRead=null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            convertRead=null;
        }

        return convertRead;
    }




    public static boolean deletePost(String id){
        try {
            return (new ServerDeletePostBackground().execute(id)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteMarker(String postId){
        try {
            return (new ServerDeleteMarkerBackground().execute(postId)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean updatePost(ToDoDocuments queryValues){
        deletePost(queryValues.getId());
        ConvertToDoDocuments values = new ConvertToDoDocuments();
        values.setTitle(queryValues.getTitle());
        values.setNumber(String.valueOf(queryValues.getNumber()));
        values.setCreateDate(String.valueOf(queryValues.getCreateDate().getTime()));
        values.setId(queryValues.getId());
        values.setAccountId(queryValues.getAccountId());
        values.setTextNote(queryValues.getTextNote());
        values.setImagePath(queryValues.ImagePathToString());
        values.setLocation(queryValues.getLocation());
        values.setAccess(String.valueOf(queryValues.getAccess()));
        values.setVideoPath(queryValues.VideoPathToString());
        values.setVideoScreen(queryValues.VideoScreenToString());
        values.setNameLocation(queryValues.getNameLocation());
        try {
            return new ServerPushPostBackground().execute(values).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean updateMarker(OwnMarker ownMarker){
        deleteMarker(ownMarker.getPostId());
        try {
            return new ServerPushMarkerBackground().execute(ownMarker).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ToDoDocuments getOnePostFromId(String id){
        ConvertToDoDocuments convertRead;

        try {
            convertRead=(new DownloadPostFromId().execute(id)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            convertRead=null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            convertRead=null;
        }

        if (convertRead==null) return null;

            ToDoDocuments add = new ToDoDocuments();
            add.setTitle(convertRead.getTitle());
            add.setImagePath (AppContext.FromStringToList(convertRead.getImagePath()));
            add.setNumber(Integer.parseInt(convertRead.getNumber()));
            add.setCreateDate(new Date(Long.parseLong(convertRead.getCreateDate())));
            add.setAccountId(convertRead.getAccountId());
            add.setId(convertRead.getId());
            add.setTextNote(convertRead.getTextNote());
            add.setLocation(convertRead.getLocation());
            add.setVideoPath(AppContext.FromStringToList(convertRead.getVideoPath()));
            add.setVideoScreen(AppContext.FromStringToList(convertRead.getVideoScreen()));
            add.setAccess(Integer.parseInt(convertRead.getAccess()));
            add.setNameLocation(convertRead.getNameLocation());
        return add;
    }


    public static OwnMarker getOneMarkerFromPostId(String id){
        OwnMarker convertRead;

        try {
            convertRead=(new DownloadMarkerFromPostId().execute(id)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            convertRead=null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            convertRead=null;
        }

        return convertRead;
    }

    static class DownloadPostFromId extends AsyncTask<String, Void, ConvertToDoDocuments> {
        ConvertToDoDocuments ret;

        @Override
        protected ConvertToDoDocuments doInBackground(String... params) {
            try {
                ret = userApi.getPostFromId(params[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ret;
        }
    }

    static class DownloadMarkerFromPostId extends AsyncTask<String, Void, OwnMarker> {
        OwnMarker ret;

        @Override
        protected OwnMarker doInBackground(String... params) {
            try {
                ret = userApi.getMarkerFromPostId(params[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ret;
        }
    }


    static class ServerPushPostBackground extends AsyncTask<ConvertToDoDocuments, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ConvertToDoDocuments... params) {
            try {
                userApi.pushPost(params[0]).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    static class ServerPushMarkerBackground extends AsyncTask<OwnMarker, Void, Boolean> {
        @Override
        protected Boolean doInBackground(OwnMarker... params) {
            try {
                userApi.pushMarker(params[0]).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    static class ServerDeletePostBackground extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                userApi.deletePost(params[0]).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    static class ServerDeleteMarkerBackground extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                userApi.deleteMarker(params[0]).execute();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    static class DownloadMyMarkersFromAccountId extends AsyncTask<String, Void, List<OwnMarker>> {
        List<OwnMarker> list = new ArrayList<>();

        @Override
        protected List<OwnMarker> doInBackground(String... params) {
            try {
                list.addAll(userApi.getMarkersFromAccountId(params[0]).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }
    }
    static class DownloadMyMarkersFromAccess extends AsyncTask<String, Void, List<OwnMarker>> {
        List<OwnMarker> list = new ArrayList<>();

        @Override
        protected List<OwnMarker> doInBackground(String... params) {
            try {
                list.addAll(userApi.getMarkersFromAccess(params[0]).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }

    }

}
