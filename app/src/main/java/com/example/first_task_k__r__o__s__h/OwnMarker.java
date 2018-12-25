package com.example.first_task_k__r__o__s__h;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

public class OwnMarker implements ClusterItem, Parcelable, Comparable<OwnMarker> {
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("accountId")
    @Expose
    private String accountId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("access")
    @Expose
    private String access;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("id")
    @Expose
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OwnMarker(){}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }



    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private double getLocationLatitude() {
        StringBuilder help= new StringBuilder();
        for (int i=0; i<location.length(); i++){
            if (location.charAt(i) == '/') break;
            else help.append(location.charAt(i));
        }
        return Double.parseDouble(help.toString());
    }


    private double getLocationLongitude() {
        StringBuilder help= new StringBuilder();
        for (int i=location.length()-1; i>=0; i--){
            if (location.charAt(i) == '/') break;
            else help.insert(0, location.charAt(i));
        }
        return Double.parseDouble(help.toString());
    }


    private OwnMarker(Parcel in){
        String[] data = new String[6];
        in.readStringArray(data);
        postId = data[0];
        accountId = data[1];
        location = data[2];
        access = data[3];
        number = data[4];
        id = data[5];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[] {postId, accountId, location, access, number, id});
    }

    public static final Parcelable.Creator<OwnMarker> CREATOR = new Parcelable.Creator<OwnMarker>(){

        @Override
        public OwnMarker createFromParcel(Parcel source){
            return new com.example.first_task_k__r__o__s__h.OwnMarker(source);
        }

        @Override
        public OwnMarker[] newArray(int size){
            return new OwnMarker[size];
        }

    };

    @Override
    public LatLng getPosition() {
        return new LatLng(getLocationLatitude(),getLocationLongitude());
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }


    @Override
    public int compareTo(OwnMarker o) {
        return o.getPostId().compareTo(postId);
    }
    @Override
    public boolean equals(Object o){
        if (!(o instanceof OwnMarker)) return false;
        OwnMarker document = (OwnMarker) o;
        return postId.equals(document.getPostId());
    }
}