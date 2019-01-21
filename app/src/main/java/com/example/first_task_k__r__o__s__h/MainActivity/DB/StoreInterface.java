package com.example.first_task_k__r__o__s__h.MainActivity.DB;

import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.Post;

import java.util.List;

public interface StoreInterface {
    void setPost(Post post);
    Post getPostWith(MapItem mapItem);
    void deletePostWith(Post post);
    void editPostWith(Post post);
    List<Post> getAllPublicPosts();
    List<Post> getAllPrivatePosts(String accountId);
    void setMapItem(MapItem mapItem);
    void deleteMapItem(MapItem mapItem);
    void editMapItem(MapItem mapItem);
    List<MapItem> getAllPublicMapItems();
    List<MapItem> getAllPrivateMapItems(String accountId);
    MapItem getMapItemWith(Post post);
}
