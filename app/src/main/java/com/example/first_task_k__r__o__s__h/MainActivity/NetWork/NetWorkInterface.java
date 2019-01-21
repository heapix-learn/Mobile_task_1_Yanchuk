package com.example.first_task_k__r__o__s__h.MainActivity.NetWork;

import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;
import com.example.first_task_k__r__o__s__h.Post;

import java.util.List;

public interface NetWorkInterface {
    void setPost(Post post, Runnable onSuccess, RunnableWithObject<PackageErrors> onFailure);
    void getPostWith(MapItem mapItem, RunnableWithObject<Post> onSuccess, RunnableWithObject<PackageErrors> onFailure);
    void deletePostWith(Post post, Runnable onSuccess, Runnable onFailure);
    void editPostWith(Post post, Runnable onSuccess, Runnable onFailure);
    void getAllPrivateMapItems(final RunnableWithObject<List<MapItem>> onSuccess, final RunnableWithObject<PackageErrors> onFailure);
    void getAllPublicMapItems(final RunnableWithObject<List<MapItem>> onSuccess, final RunnableWithObject<PackageErrors> onFailure);
    void setMapItem(MapItem mapItem, Runnable onSuccess, RunnableWithObject<PackageErrors> onFailure);
    void getMapItemWith(Post post, RunnableWithObject<MapItem> onSuccess, RunnableWithObject<PackageErrors> onFailure);
    void deleteMapItem(MapItem mapItem, Runnable onSuccess, Runnable onFailure);
    void editMapItemWith(MapItem mapItem, Runnable onSuccess, Runnable onFailure);
}
