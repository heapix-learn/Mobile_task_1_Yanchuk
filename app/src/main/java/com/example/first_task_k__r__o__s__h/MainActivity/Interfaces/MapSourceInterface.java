package com.example.first_task_k__r__o__s__h.MainActivity.Interfaces;


import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;

import java.util.List;

public interface MapSourceInterface {
    int TYPE_PRIVATE = 1;
    int TYPE_PUBLIC = 0;
    List<MapItem> getAll(int type, RunnableWithObject<List<MapItem>> onSuccess, RunnableWithObject<PackageErrors> onFailure);
    void updateMapItem();
}
