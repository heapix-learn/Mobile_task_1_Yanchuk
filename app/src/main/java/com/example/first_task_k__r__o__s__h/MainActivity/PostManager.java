package com.example.first_task_k__r__o__s__h.MainActivity;

import com.example.first_task_k__r__o__s__h.MainActivity.DB.Store;
import com.example.first_task_k__r__o__s__h.MainActivity.NetWork.NetWorkInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.PostManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.DB.StoreInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.NetWork.NetWorkManager;
import com.example.first_task_k__r__o__s__h.Post;

public class PostManager implements PostManagerInterface {

    private NetWorkInterface netWork = new NetWorkManager();
    private StoreInterface store = new Store();


    @Override
    public void addPost(Post post) {
        store.setPost(post);
        netWork.setPost(post, new Runnable() {
            @Override
            public void run() {

            }
        }, new RunnableWithObject());
    }

    @Override
    public void deletePost(Post post) {
        store.deletePostWith(post);
        netWork.deletePostWith(post, new Runnable() {
            @Override
            public void run() {

            }
        }, new RunnableWithObject<>());
    }

    @Override
    public void editPost(Post post) {
        store.editPostWith(post);
        netWork.editPostWith(post, new Runnable() {
            @Override
            public void run() {

            }
        }, new RunnableWithObject<>());
    }

    @Override
    public void getPostWithNetWork(MapItem mapItem, final RunnableWithObject<Post> onSuccess, final RunnableWithObject<PackageErrors> onFailure) {
        if (isExistInStore(mapItem)) onSuccess.init(store.getPostWith(mapItem));
        RunnableWithObject<Post> onSuccessCheckNetWork = new RunnableWithObject<Post>(){
            @Override
            public void run(){
                setToStore(this.getDescription());
                onSuccess.init(this.getDescription());
                setToStore(this.getDescription());
                onSuccess.run();
            }
        };
        netWork.getPostWith(mapItem, onSuccessCheckNetWork, onFailure);
    }

    @Override
    public Post getPostWithStore(MapItem mapItem) {
        return store.getPostWith(mapItem);
    }


    private boolean isExistInStore(MapItem mapItem){
        return store.getPostWith(mapItem) != null;
    }

    private void setToStore(Post post){
        store.setPost(post);
    }
}
