package com.example.first_task_k__r__o__s__h;


import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


import java.util.Date;

public class ToDoDocuments implements Parcelable, Comparable<ToDoDocuments> {
    private String title;
    private int number;
    private Date CreateDate;
    private String login;
    private String context;
    private String textNote;
    private Uri imagePath;
    private double locationLatitude;
    private double locationLongitude;


    public ToDoDocuments(){
        CreateDate=new Date();
        number=-1;
        login=LoginActivity.myUser.username;
        locationLongitude=-1000;
        locationLatitude=-1000;
    }

    public ToDoDocuments(Parcel in){
        String[] data = new String[9];
        in.readStringArray(data);
        title=data[0];
        number=Integer.parseInt(data[1]);
        long tmpDate=Long.parseLong(data[2]);
        CreateDate=new Date(tmpDate);
        login=data[3];
        context=data[4];
        textNote=data[5];
        imagePath= Uri.parse(data[6]);
        locationLatitude = Double.parseDouble(data[7]);
        locationLongitude = Double.parseDouble(data[8]);
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setCreateDate(Date CreateDate){
        this.CreateDate=CreateDate;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[] {title, ""+number, ""+CreateDate.getTime(), login, context, textNote, ""+imagePath, ""+locationLatitude, ""+locationLongitude});
    }

    @NonNull
    public String toString(){
        return context;
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
        return number == document.getNumber();
    }

    @Override
    public int compareTo(ToDoDocuments another){
        return another.getCreateDate().compareTo(CreateDate);
    }

}
