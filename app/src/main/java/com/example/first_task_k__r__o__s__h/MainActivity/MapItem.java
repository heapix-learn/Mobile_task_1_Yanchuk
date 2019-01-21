package com.example.first_task_k__r__o__s__h.MainActivity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.MapItemInterface;
import com.example.first_task_k__r__o__s__h.Post;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

@Entity
public class MapItem implements MapItemInterface, ClusterItem,Comparable<MapItem>  {
    @PrimaryKey
    @NonNull
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("accountId")
    @Expose
    private String accountId;

    public MapItem(){}
    public MapItem(Post post){
        postId=post.getId();
        latitude = post.getLocationLatitude()+"";
        longitude = post.getLocationLongitude() + "";
        access = post.getAccess()+"";
        accountId = post.getAccountId();
    }


    @NonNull
    @Override
    public String getPostId() {
        return postId;
    }

    @Override
    public void setPostId(String postId) {
        this.postId=postId;
    }

    @Override
    public double getLocationLatitude() {
        return Double.parseDouble(latitude);
    }

    @Override
    public void setLocationLatitude(double latitude) {
        this.latitude = latitude+"";
    }

    @Override
    public double getLocationLongitude() {
        return Double.parseDouble(longitude);
    }

    @Override
    public void setLocationLongitude(double longitude) {
        this.longitude = longitude+"";
    }

    @Override
    public String getAccess() {
        return access;
    }

    @Override
    public void setAccess(String access) {
        this.access=access;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId=accountId;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(getLocationLatitude(), getLocationLongitude());
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
    public int compareTo(MapItem o) {
        return o.getPostId().compareTo(postId);
    }
    @Override
    public boolean equals(Object o){
        if (!(o instanceof MapItem)) return false;
        MapItem document = (MapItem) o;
        return postId.equals(document.getPostId());
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
