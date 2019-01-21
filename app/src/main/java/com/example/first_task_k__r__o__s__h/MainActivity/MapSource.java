package com.example.first_task_k__r__o__s__h.MainActivity;

import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.MapItemManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.MapSourceInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.PostManagerInterface;
import com.example.first_task_k__r__o__s__h.Post;

import java.util.ArrayList;
import java.util.List;

public class MapSource implements MapSourceInterface {

    private PostManagerInterface postManager;
    private MapItemManagerInterface mapItemManager;
    private int TYPE_PUBLIC = 0;
    private int TYPE_PRIVATE = 1;
    private static MapSource instance;

    private MapSource(){
        postManager = new PostManager();
        mapItemManager = new MapItemManager();
    }

    public static MapSource getInstance(){
        if (!isExist(instance)){
            instance = new MapSource();
        }
        return instance;
    }

    @Override
    public List<MapItem> getAll(int type, RunnableWithObject<List<MapItem>> onSuccess, RunnableWithObject<PackageErrors> onFailure) {
        if (isPublic(type)){
            mapItemManager.getAllPublicMapItemsWithNetWork(onSuccess, onFailure);
            return mapItemManager.getAllPublicMapItemsWithStore();
        }

        if (isPrivate(type)){
            mapItemManager.getAllPrivateMapItemsWithNetWork(onSuccess, onFailure);
            return mapItemManager.getAllPrivateMapItemsWithStore();
        }
        return new ArrayList<>();
    }

    @Override
    public void updateMapItem() {

    }


    public void getOnePostWith(MapItem mapItem, RunnableWithObject<Post> onSuccess, RunnableWithObject<PackageErrors> onFailure) {
        if (isExist(postManager.getPostWithStore(mapItem))){
            onSuccess.init(postManager.getPostWithStore(mapItem));
        }
        postManager.getPostWithNetWork(mapItem, onSuccess, onFailure);
    }

    private boolean isExist(Post post){
        return post!=null;
    }

    private static boolean isExist(MapSource instance){
        return instance!=null;
    }

    private boolean isPublic(int type){
        return type == TYPE_PUBLIC;
    }

    private boolean isPrivate(int type){
        return type == TYPE_PRIVATE;
    }
}
