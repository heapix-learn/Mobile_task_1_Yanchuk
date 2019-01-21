package com.example.first_task_k__r__o__s__h.MainActivity.DB;

import com.example.first_task_k__r__o__s__h.AppContext;
import com.example.first_task_k__r__o__s__h.ConvertPostForServer;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Store implements StoreInterface {
    private PostDao postDao;
    private MapItemDao mapItemDao;

    public  Store(){
       AppDatabase database = AppContext.getInstance().getDatabase();
        postDao = database.postDao();
        mapItemDao = database.mapItemDoa();
    }

    @Override
    public void setPost(Post post) {
        postDao.insert(new ConvertPostForServer(post));
    }

    @Override
    public Post getPostWith(MapItem mapItem) {
       return ConvertToPost(postDao.getById(mapItem.getPostId()));
    }


    @Override
    public void deletePostWith(Post post) {
        postDao.delete(new ConvertPostForServer(post));
    }

    @Override
    public void editPostWith(Post post) {
        postDao.insert(new ConvertPostForServer(post));
    }

    @Override
    public List<Post> getAllPublicPosts() {
        List<Post> postList = new ArrayList<>();
        List<ConvertPostForServer> convertPostForServerList = postDao.getAllPublic();
        for (int i=0; i<convertPostForServerList.size(); i++){
            postList.add(ConvertToPost(convertPostForServerList.get(i)));
        }
        return postList;
    }

    @Override
    public List<Post> getAllPrivatePosts(String accountId) {
        List<Post> postList = new ArrayList<>();
        List<ConvertPostForServer> convertPostForServerList = postDao.getAllPrivate(accountId);
        for (int i=0; i<convertPostForServerList.size(); i++){
            postList.add(ConvertToPost(convertPostForServerList.get(i)));
        }
        return postList;
    }

    @Override
    public void setMapItem(MapItem mapItem) {
        mapItemDao.insert(mapItem);
    }

    @Override
    public void deleteMapItem(MapItem mapItem) {
        mapItemDao.delete(mapItem);
    }

    @Override
    public void editMapItem(MapItem mapItem) {
        mapItemDao.insert(mapItem);
    }

    @Override
    public List<MapItem> getAllPublicMapItems() {
        return mapItemDao.getAllPublic();
    }

    @Override
    public List<MapItem> getAllPrivateMapItems(String accountId) {
        return mapItemDao.getAllPrivate(accountId);
    }

    @Override
    public MapItem getMapItemWith(Post post) {
        return mapItemDao.getByPostId(post.getId());
    }

    private Post ConvertToPost(ConvertPostForServer convertRead){
        Post post = new Post();
        post.setTitle(convertRead.getTitle());
        post.setImagePath (FromStringToList(convertRead.getImagePath()));
        post.setNumber(Integer.parseInt(convertRead.getNumber()));
        post.setCreateDate(new Date(Long.parseLong(convertRead.getCreateDate())));
        post.setAccountId(convertRead.getAccountId());
        post.setId(convertRead.getId());
        post.setTextNote(convertRead.getTextNote());
        post.setLocation(convertRead.getLocation());
        post.setVideoPath(FromStringToList(convertRead.getVideoPath()));
        post.setVideoScreen(FromStringToList(convertRead.getVideoScreen()));
        post.setAccess(Integer.parseInt(convertRead.getAccess()));
        post.setNameLocation(convertRead.getNameLocation());
        return post;
    }
    private static List<String> FromStringToList(String str){
        List<String> ans = new ArrayList<>();
        StringBuilder help= new StringBuilder();
        for (int i=0; i<str.length()-1; i++){
            if (str.charAt(i)=='&' && str.charAt(i+1)=='&'){
                ans.add(help.toString());
                help = new StringBuilder();
                i++;
            }
            else help.append(str.charAt(i));
        }
        return ans;
    }

}
