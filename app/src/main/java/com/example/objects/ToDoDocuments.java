package com.example.objects;
import android.os.Parcel;
import android.os.Parcelable;

public class ToDoDocuments implements Parcelable {
    private String title;

    public ToDoDocuments(String title){
        this.title=title;
    }
    public ToDoDocuments(){}

    public ToDoDocuments(Parcel in){
        String[] data = new String[1];
        in.readStringArray(data);
        title=data[0];
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[] {title});
    }

    public static final Parcelable.Creator<ToDoDocuments> CREATOR = new Parcelable.Creator<ToDoDocuments>(){

        @Override
        public ToDoDocuments createFromParcel(Parcel source){
            return new ToDoDocuments(source);
        }

        @Override
        public ToDoDocuments[] newArray(int size){
            return new ToDoDocuments[size];
        }
    };
}
