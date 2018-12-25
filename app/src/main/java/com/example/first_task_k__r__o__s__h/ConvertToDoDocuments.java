package com.example.first_task_k__r__o__s__h;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("accountId")
    @Expose
    private String accountId;
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
    @SerializedName("videoScreen")
    @Expose
    private String videoScreen;
    @SerializedName("nameLocation")
    @Expose
    private String nameLocation;

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getVideoScreen() {
        return videoScreen;
    }

    public void setVideoScreen(String videoScreen) {
        this.videoScreen = videoScreen;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}
