package com.example.first_task_k__r__o__s__h;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToDoDocuments implements Parcelable, Comparable<ToDoDocuments>, ClusterItem {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("CreateDate")
    @Expose
    private Date CreateDate;
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
    private int access;
    @SerializedName("imagePath")
    @Expose
    private List<String> imagePath;
    @SerializedName("videoPath")
    @Expose
    private List<String> videoPath;
    @SerializedName("videoScreen")
    @Expose
    private List<String> videoScreen;
    @SerializedName("nameLocation")
    @Expose
    private String nameLocation;




    public ToDoDocuments(){
        CreateDate=new Date();
        number=-1;
        accountId="";
        location="-1000/-1000";
        access=0;
        imagePath = new ArrayList<>();
        id="-1";
        videoPath = new ArrayList<>();
        videoScreen = new ArrayList<>();
        nameLocation="";
    }

    public ToDoDocuments(Parcel in){
        String[] data = new String[12];
        in.readStringArray(data);
        title=data[0];
        number=Integer.parseInt(data[1]);
        long tmpDate=Long.parseLong(data[2]);
        CreateDate=new Date(tmpDate);
        accountId=data[3];
        id = data[4];
        textNote=data[5];
        imagePath= FromStringToList(data[6]);
        location = data[7];
        access = Integer.parseInt(data[8]);
        videoPath= FromStringToList(data[9]);
        videoScreen=FromStringToList(data[10]);
        nameLocation = data[11];
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public String ImagePathToString(){
        if (imagePath.size()==0) return "";
        StringBuilder ans= new StringBuilder();
        for (int i=0; i<imagePath.size(); i++){

            ans.append(imagePath.get(i)).append("&&");
        }
        return ans.toString();
    }

    public String VideoPathToString(){
        if (videoPath.size()==0) return "";
        StringBuilder ans= new StringBuilder();
        for (int i=0; i<videoPath.size(); i++){

            ans.append(videoPath.get(i)).append("&&");
        }
        return ans.toString();
    }

    public String VideoScreenToString(){
        if (videoScreen.size()==0) return "";
        StringBuilder ans= new StringBuilder();
        for (int i=0; i<videoScreen.size(); i++){

            ans.append(videoScreen.get(i)).append("&&");
        }
        return ans.toString();
    }



    public String getLocation() {
        return location;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public double getLocationLatitude() {
        StringBuilder help= new StringBuilder();
        for (int i=0; i<location.length(); i++){
            if (location.charAt(i) == '/') break;
            else help.append(location.charAt(i));
        }
        return Double.parseDouble(help.toString());
    }

    public double getLocationLongitude() {
        StringBuilder help= new StringBuilder();
        for (int i=location.length()-1; i>=0; i--){
            if (location.charAt(i) == '/') break;
            else help.insert(0, location.charAt(i));
        }
        return Double.parseDouble(help.toString());
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath.add(imagePath);
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath.addAll(imagePath);
    }


    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setCreateDate(Date CreateDate){
        this.CreateDate=CreateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate(){
        return CreateDate;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    public void setNumber(int number){
        this.number=number;
    }

    public int getNumber(){
        return number;
    }

    public List<String> getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath.add(videoPath);
    }

    public void setVideoPath(List<String> videoPath) {
        this.videoPath.addAll(videoPath);
    }

    public List<String> getVideoScreen() {
        return videoScreen;
    }

    public void setVideoScreen(String videoScreen) {
        this.videoScreen.add(videoScreen);
    }

    public void setVideoScreen(List<String> videoScreen) {
        this.videoScreen.addAll(videoScreen);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[] {title, ""+number, ""+CreateDate.getTime(), accountId, id, textNote, ImagePathToString(), location, ""+access, VideoPathToString(), VideoScreenToString(), nameLocation});
    }

    @NonNull
    public String toString(){
        return title;
    }

    public static final Parcelable.Creator<ToDoDocuments> CREATOR = new Parcelable.Creator<ToDoDocuments>(){

        @Override
        public ToDoDocuments createFromParcel(Parcel source){
            return new com.example.first_task_k__r__o__s__h.ToDoDocuments(source);
        }

        @Override
        public ToDoDocuments[] newArray(int size){
            return new ToDoDocuments[size];
        }

    };
    @Override
    public boolean equals(Object o){
        if (!(o instanceof ToDoDocuments)) return false;
        ToDoDocuments document = (ToDoDocuments) o;
        return id.equals(document.getId());
    }

    @Override
    public int compareTo(ToDoDocuments another){
        return another.getCreateDate().compareTo(CreateDate);
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(getLocationLatitude(), getLocationLongitude());
    }

    @Override
    public String getSnippet() {
        return getId();
    }



    public List<String>FromStringToList(String str){
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

}
