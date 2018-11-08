package com.example.first_task_k__r__o__s__h;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ToDoDocuments implements Parcelable {
    private String title;
    private int number;


    public ToDoDocuments(String title){
        this.title=title;
    }
    public ToDoDocuments(){};

    public ToDoDocuments(Parcel in){
        String[] data = new String[2];
        in.readStringArray(data);
        title=data[0];
        number=Integer.parseInt(data[1]);
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
        dest.writeStringArray(new String[] {title, ""+number});
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
        return number == document.getNumber();
    }

}
