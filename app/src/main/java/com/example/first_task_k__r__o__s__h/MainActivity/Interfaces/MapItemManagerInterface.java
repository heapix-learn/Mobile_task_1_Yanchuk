package com.example.first_task_k__r__o__s__h.MainActivity.Interfaces;

import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;

import java.util.List;

public interface MapItemManagerInterface {
    void getAllPrivateMapItemsWithNetWork(RunnableWithObject<List<MapItem>> onSuccess, RunnableWithObject<PackageErrors> onFailure);
    void getAllPublicMapItemsWithNetWork(RunnableWithObject<List<MapItem>> onSuccess, RunnableWithObject<PackageErrors> onFailure);
    List<MapItem> getAllPrivateMapItemsWithStore();
    List<MapItem> getAllPublicMapItemsWithStore();
    void addMapItem(MapItem mapItem);
    void deleteMapItem(MapItem mapItem);
    void editMapItem(MapItem mapItem);
}
