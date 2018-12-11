package com.example.first_task_k__r__o__s__h;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class DBNotes{

    private final static UserApi userApi=Controller.getApi();

    public static void insertNote(ToDoDocuments queryValues){
        ConvertToDoDocuments values = new ConvertToDoDocuments();
        values.setTitle(queryValues.getTitle());
        values.setNumber(String.valueOf(queryValues.getNumber()));
        values.setCreateDate(String.valueOf(queryValues.getCreateDate().getTime()));
        values.setId(queryValues.getId());
        values.setLogin(queryValues.getLogin());
        values.setTextNote(queryValues.getTextNote());
        values.setImagePath(queryValues.ImagePathToString());
        values.setLocation(queryValues.getLocation());
        values.setAccess(String.valueOf(queryValues.getAccess()));
        values.setVideoPath(queryValues.VideoPathToString());
        userApi.pushNote(values).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //        Toast.makeText(.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static List<ToDoDocuments> getNotesAllMy(String login){
        List<ConvertToDoDocuments> convertRead = new ArrayList<ConvertToDoDocuments>();

        try {
            convertRead=(new DownloadMyNoteLogin().execute(login)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<ToDoDocuments> read = new ArrayList<ToDoDocuments>();
        for (int i=0; i<convertRead.size(); i++) {
            ToDoDocuments add = new ToDoDocuments();
            add.setTitle(convertRead.get(i).getTitle());
            add.setImagePath(ToDoDocuments.FromStringToList(convertRead.get(i).getImagePath()));
            add.setNumber(Integer.parseInt(convertRead.get(i).getNumber()));
            add.setCreateDate(new Date(Long.parseLong(convertRead.get(i).getCreateDate())));
            add.setLogin(convertRead.get(i).getLogin());
            add.setId(convertRead.get(i).getId());
            add.setTextNote(convertRead.get(i).getTextNote());
            add.setLocation(convertRead.get(i).getLocation());
            add.setAccess(Integer.parseInt(convertRead.get(i).getAccess()));
            add.setVideoPath(convertRead.get(i).getVideoPath());
            read.add(add);
        }
        return read;
    }
    public static List<ToDoDocuments> getNotesAllPublic(){
        List<ConvertToDoDocuments> convertRead = new ArrayList<ConvertToDoDocuments>();


        try {
            convertRead=(new DownloadMyNoteAccess().execute("0")).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<ToDoDocuments> read = new ArrayList<ToDoDocuments>();
        for (int i=0; i<convertRead.size(); i++) {
            ToDoDocuments add = new ToDoDocuments();
            add.setTitle(convertRead.get(i).getTitle());
            add.setImagePath (ToDoDocuments.FromStringToList(convertRead.get(i).getImagePath()));
            add.setNumber(Integer.parseInt(convertRead.get(i).getNumber()));
            add.setCreateDate(new Date(Long.parseLong(convertRead.get(i).getCreateDate())));
            add.setLogin(convertRead.get(i).getLogin());
            add.setId(convertRead.get(i).getId());
            add.setTextNote(convertRead.get(i).getTextNote());
            add.setLocation(convertRead.get(i).getLocation());
            add.setAccess(Integer.parseInt(convertRead.get(i).getAccess()));
            add.setVideoPath(ToDoDocuments.FromStringToList(convertRead.get(i).getVideoPath()));
            read.add(add);
        }
        return read;
    }
    public static void deleteNote(ToDoDocuments doc){
        new ServerDeleteBackground().execute(doc.getId());
    }
    public static void updateNote(ToDoDocuments queryValues){
        deleteNote(queryValues);
        ConvertToDoDocuments values = new ConvertToDoDocuments();
        values.setTitle(queryValues.getTitle());
        values.setNumber(String.valueOf(queryValues.getNumber()));
        values.setCreateDate(String.valueOf(queryValues.getCreateDate().getTime()));
        values.setId(queryValues.getId());
        values.setLogin(queryValues.getLogin());
        values.setTextNote(queryValues.getTextNote());
        values.setImagePath(queryValues.ImagePathToString());
        values.setLocation(queryValues.getLocation());
        values.setAccess(String.valueOf(queryValues.getAccess()));
        values.setVideoPath(queryValues.VideoPathToString());
        new ServerPushNoteBackground().execute(values);
    }


    static class ServerPushNoteBackground extends AsyncTask<ConvertToDoDocuments, Void, Void> {
        @Override
        protected Void doInBackground(ConvertToDoDocuments... params) {
            try {
                userApi.pushNote(params[0]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class ServerDeleteBackground extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                userApi.deleteNote(params[0]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class DownloadMyNoteLogin extends AsyncTask<String, Void, List<ConvertToDoDocuments>> {
        List<ConvertToDoDocuments> list = new ArrayList<>();

        @Override
        protected List<ConvertToDoDocuments> doInBackground(String... params) {
            try {
                list.addAll(userApi.getNotesLogin(params[0]).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }
    }
    static class DownloadMyNoteAccess extends AsyncTask<String, Void, List<ConvertToDoDocuments>> {
        List<ConvertToDoDocuments> list = new ArrayList<>();

        @Override
        protected List<ConvertToDoDocuments> doInBackground(String... params) {
            try {
                list.addAll(userApi.getNotesAccess(params[0]).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }

    }

}
