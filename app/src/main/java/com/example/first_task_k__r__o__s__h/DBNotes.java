package com.example.first_task_k__r__o__s__h;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        values.setVideoScreen(queryValues.VideoScreenToString());
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
            add.setVideoScreen(convertRead.get(i).getVideoScreen());
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
            add.setVideoScreen(ToDoDocuments.FromStringToList(convertRead.get(i).getVideoScreen()));
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
        values.setVideoScreen(queryValues.VideoScreenToString());
        new ServerPushNoteBackground().execute(values);
    }
    public static ToDoDocuments getOneNotesFromId(String id){
        ConvertToDoDocuments convertRead= new ConvertToDoDocuments();

        try {
            convertRead=(new DownloadNoteId().execute(id)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            ToDoDocuments add = new ToDoDocuments();
            add.setTitle(convertRead.getTitle());
            add.setImagePath (ToDoDocuments.FromStringToList(convertRead.getImagePath()));
            add.setNumber(Integer.parseInt(convertRead.getNumber()));
            add.setCreateDate(new Date(Long.parseLong(convertRead.getCreateDate())));
            add.setLogin(convertRead.getLogin());
            add.setId(convertRead.getId());
            add.setTextNote(convertRead.getTextNote());
            add.setLocation(convertRead.getLocation());
            add.setVideoPath(ToDoDocuments.FromStringToList(convertRead.getVideoPath()));
            add.setVideoScreen(ToDoDocuments.FromStringToList(convertRead.getVideoScreen()));
            add.setAccess(Integer.parseInt(convertRead.getAccess()));
        return add;
    }


    static class DownloadNoteId extends AsyncTask<String, Void, ConvertToDoDocuments> {
        List<ConvertToDoDocuments> list = new ArrayList<>();
        ConvertToDoDocuments ret;

        @Override
        protected ConvertToDoDocuments doInBackground(String... params) {
            try {
                ret = userApi.getNoteFromId(params[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ret;
        }
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
