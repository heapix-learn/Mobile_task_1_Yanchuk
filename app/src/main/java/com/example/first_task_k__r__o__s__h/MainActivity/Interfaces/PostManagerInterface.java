package com.example.first_task_k__r__o__s__h.MainActivity.Interfaces;

import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;
import com.example.first_task_k__r__o__s__h.Post;

public interface PostManagerInterface {
    void addPost(Post post);
    void deletePost(Post post);
    void editPost(Post post);
    void getPostWithNetWork(MapItem mapItem, RunnableWithObject<Post> onSuccess, RunnableWithObject<PackageErrors> onFailure);
    Post getPostWithStore(MapItem mapItem);
}
