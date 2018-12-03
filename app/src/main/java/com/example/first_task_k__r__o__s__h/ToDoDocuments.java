package com.example.first_task_k__r__o__s__h;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private int access;
    @SerializedName("imagePath")
    @Expose
    private List<String> imagePath;
    @SerializedName("typeOfResource")
    @Expose
    private String typeOfResource;



    public ToDoDocuments(){
        CreateDate=new Date();
        number=-1;
//        login=LoginActivity.myUser.getUsername();
        login="";
        location="-1000/-1000";
        access=0;
        imagePath = new ArrayList<>();
        id="-1";
        typeOfResource="";
    }

    public ToDoDocuments(Parcel in){
        String[] data = new String[10];
        in.readStringArray(data);
        title=data[0];
        number=Integer.parseInt(data[1]);
        long tmpDate=Long.parseLong(data[2]);
        CreateDate=new Date(tmpDate);
        login=data[3];
        id = data[4];
        textNote=data[5];
        imagePath= FromStringToList(data[6]);
        location = data[7];
        access = Integer.parseInt(data[8]);
        typeOfResource = data[9];
    }

    public String ImagePathToString(){
        if (imagePath.size()==0) return "";
        StringBuilder ans= new StringBuilder();
        for (int i=0; i<imagePath.size(); i++){

            ans.append(imagePath.get(i)).append("&&");
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getTypeOfResource() {
        return typeOfResource;
    }

    public void setTypeOfResource(String typeOfResource) {
        this.typeOfResource = typeOfResource;
    }
    public void setTypeOfResource(char typeOfResource) {
        this.typeOfResource += typeOfResource;
    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[] {title, ""+number, ""+CreateDate.getTime(), login, id, textNote, ImagePathToString(), location, ""+access, typeOfResource});
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
        return getTypeOfResource()+"%#"+getTextNote()+"%##"+ImagePathToString();
    }

    public static Bitmap ConvertBase64ToBitmap(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static File ConvertBase64ToFile(Context Cont, String encodedVideo) {
        byte[] byteVideo= Base64.decode(encodedVideo, Base64.DEFAULT);
        FileOutputStream out = null;
        File directory = Cont.getFilesDir(); //or getExternalFilesDir(null); for external storage
        File file = new File(directory, "file.mp4");
        if (file.exists()){
            file.delete();
        }
        if (!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            out = new FileOutputStream(file);

            out.write(byteVideo);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return file;
    }


}
