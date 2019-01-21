package com.example.first_task_k__r__o__s__h.MainActivity;

import com.example.first_task_k__r__o__s__h.Authorization.User;
import com.example.first_task_k__r__o__s__h.MainActivity.DB.Store;
import com.example.first_task_k__r__o__s__h.MainActivity.DB.StoreInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.MapItemManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.NetWork.NetWorkInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.NetWork.NetWorkManager;

import java.util.ArrayList;
import java.util.List;

public class MapItemManager implements MapItemManagerInterface {

    private NetWorkInterface netWork;
    private StoreInterface store;
    private com.example.first_task_k__r__o__s__h.Authorization.StoreInterface authStore;

    public MapItemManager(){
        store = new Store();
        netWork = new NetWorkManager();
        authStore = com.example.first_task_k__r__o__s__h.Authorization.Store.getInstance();
    }


    @Override
    public void getAllPrivateMapItemsWithNetWork(RunnableWithObject<List<MapItem>> onSuccess, RunnableWithObject<PackageErrors> onFailure) {
        netWork.getAllPrivateMapItems(onSuccess, onFailure);
    }

    @Override
    public List<MapItem> getAllPrivateMapItemsWithStore() {
        if (isExistUserInStore(authStore.getUser())) {
            if (isExistInStore(store.getAllPrivateMapItems(authStore.getUser().getId()))) {
                return store.getAllPrivateMapItems(authStore.getUser().getId());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void getAllPublicMapItemsWithNetWork(RunnableWithObject<List<MapItem>> onSuccess, RunnableWithObject<PackageErrors> onFailure) {
        netWork.getAllPublicMapItems(onSuccess, onFailure);
    }

    @Override
    public List<MapItem> getAllPublicMapItemsWithStore() {
        if (isExistInStore(store.getAllPublicMapItems())){
            return store.getAllPublicMapItems();
        }
        return new ArrayList<>();
    }


    @Override
    public void addMapItem(MapItem mapItem) {
        store.setMapItem(mapItem);
        netWork.setMapItem(mapItem, new Runnable() {
            @Override
            public void run() {

            }
        }, new RunnableWithObject<PackageErrors>());
    }

    @Override
    public void deleteMapItem(MapItem mapItem) {
        store.deleteMapItem(mapItem);
        netWork.deleteMapItem(mapItem, new Runnable() {
            @Override
            public void run() {

            }
        }, new RunnableWithObject<PackageErrors>());
    }

    @Override
    public void editMapItem(MapItem mapItem) {
        store.editMapItem(mapItem);
        netWork.editMapItemWith(mapItem, new Runnable() {
            @Override
            public void run() {

            }
        }, new RunnableWithObject<PackageErrors>());
    }

    private boolean isExistInStore(List<MapItem> mapItemsList){
        if (mapItemsList!=null){
            return mapItemsList.size() != 0;
        }
        return false;
    }

    private boolean isExistUserInStore(User user){
        return user!=null;
    }
}
