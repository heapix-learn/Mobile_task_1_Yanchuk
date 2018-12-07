package com.example.first_task_k__r__o__s__h;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class ConvertToDoDocuments {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("CreateDate")
    @Expose
    private String CreateDate;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("textNote")
    @Expose
    private String textNote;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("videoPath")
    @Expose
    private String videoPath;

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public String getId() {
        return id;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getNumber() {
        return number;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getLogin() {
        return login;
    }

    public String getTextNote() {
        return textNote;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAccess() {
        return access;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoPath() {
        return videoPath;
    }
}
