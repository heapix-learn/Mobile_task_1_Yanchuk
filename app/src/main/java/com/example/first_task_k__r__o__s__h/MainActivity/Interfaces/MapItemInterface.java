package com.example.first_task_k__r__o__s__h.MainActivity.Interfaces;

import com.google.maps.android.clustering.ClusterItem;

public interface MapItemInterface extends ClusterItem {
    String getPostId();
    void setPostId(String postId);
    double getLocationLatitude();
    void setLocationLatitude(double latitude);
    double getLocationLongitude();
    void setLocationLongitude(double longitude);
    String getAccess();
    void setAccess(String access);
    String getAccountId();
    void setAccountId(String accountId);
}
