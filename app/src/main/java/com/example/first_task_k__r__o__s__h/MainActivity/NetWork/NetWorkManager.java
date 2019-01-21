package com.example.first_task_k__r__o__s__h.MainActivity.NetWork;

import com.example.first_task_k__r__o__s__h.Authorization.Store;
import com.example.first_task_k__r__o__s__h.Authorization.StoreInterface;
import com.example.first_task_k__r__o__s__h.ConvertPostForServer;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.PostId;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;
import com.example.first_task_k__r__o__s__h.Post;
import com.example.first_task_k__r__o__s__h.ServerAnswer;
import com.example.first_task_k__r__o__s__h.WorkWithServer.Controller;
import com.example.first_task_k__r__o__s__h.WorkWithServer.UserApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetWorkManager implements NetWorkInterface {

    private UserApi userApi;
    private StoreInterface store;

    private ConvertPostForServer getCovertPostForServer(Post post){
        ConvertPostForServer postForServer = new ConvertPostForServer();
        postForServer.setTitle(post.getTitle());
        postForServer.setNumber(String.valueOf(post.getNumber()));
        postForServer.setCreateDate(String.valueOf(post.getCreateDate().getTime()));
        postForServer.setId(post.getId());
        postForServer.setAccountId(post.getAccountId());
        postForServer.setTextNote(post.getTextNote());
        postForServer.setImagePath(post.ImagePathToString());
        postForServer.setLocation(post.getLocation());
        postForServer.setAccess(String.valueOf(post.getAccess()));
        postForServer.setVideoPath(post.VideoPathToString());
        postForServer.setVideoScreen(post.VideoScreenToString());
        postForServer.setNameLocation(post.getNameLocation());
        return postForServer;
    }

    public NetWorkManager(){
        userApi = Controller.getApi();
        store = Store.getInstance();
    }

    @Override
    public void setPost(Post post, final Runnable onSuccess, final RunnableWithObject onFailure) {
        userApi.addPost(getCovertPostForServer(post), store.getToken()).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    } else {
                        onFailure.run();
                    }
                } else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void getPostWith(MapItem mapItem, final RunnableWithObject<Post> onSuccess, final RunnableWithObject<PackageErrors> onFailure) {
        PostId mPostId = new PostId();
        mPostId.setPostId(mapItem.getPostId());
        userApi.getOnePostWithItemPostId(mPostId,store.getToken()).enqueue(new Callback<ConvertPostForServer>() {
            @Override
            public void onResponse(Call<ConvertPostForServer> call, Response<ConvertPostForServer> response) {
                if (response.body()!=null){
                    onSuccess.init(ConvertFromJStoPost(response.body()));
                    onSuccess.run();
                }else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ConvertPostForServer> call, Throwable t) {
                onFailure.run();
            }
        });

    }

    @Override
    public void deletePostWith(Post post, final Runnable onSuccess, final Runnable onFailure) {
        userApi.deletePost(getCovertPostForServer(post), store.getToken()).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    } else {
                        onFailure.run();
                    }
                } else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void editPostWith(Post post, final Runnable onSuccess, final Runnable onFailure) {
        userApi.editPost(getCovertPostForServer(post), store.getToken()).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    } else {
                        onFailure.run();
                    }
                } else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    private Post ConvertFromJStoPost(ConvertPostForServer convertRead){
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

    @Override
    public void getAllPrivateMapItems(final RunnableWithObject<List<MapItem>> onSuccess, final RunnableWithObject<PackageErrors> onFailure){
        userApi.getAllPrivateMapItems(store.getToken()).enqueue(new Callback<List<MapItem>>() {
            @Override
            public void onResponse(Call<List<MapItem>> call, Response<List<MapItem>> response) {
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
                else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<List<MapItem>> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void getAllPublicMapItems(final RunnableWithObject<List<MapItem>> onSuccess, final RunnableWithObject<PackageErrors> onFailure) {
        userApi.getAllPublicMapItems().enqueue(new Callback<List<MapItem>>() {
            @Override
            public void onResponse(Call<List<MapItem>> call, Response<List<MapItem>> response) {
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }
                else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<List<MapItem>> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void setMapItem(MapItem mapItem, final Runnable onSuccess, final RunnableWithObject<PackageErrors> onFailure) {
        userApi.addMapItem(mapItem, store.getToken()).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    } else {
                        onFailure.run();
                    }
                } else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void getMapItemWith(Post post, final RunnableWithObject<MapItem> onSuccess, final RunnableWithObject<PackageErrors> onFailure) {

        userApi.getMapItemWith(getCovertPostForServer(post),store.getToken()).enqueue(new Callback<MapItem>() {
            @Override
            public void onResponse(Call<MapItem> call, Response<MapItem> response) {
                if (response.body()!=null){
                    onSuccess.init(response.body());
                    onSuccess.run();
                }else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<MapItem> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void deleteMapItem(MapItem mapItem, final Runnable onSuccess, final Runnable onFailure) {
        userApi.deleteMapItem(mapItem, store.getToken()).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    onSuccess.run();
                }
                else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void editMapItemWith(MapItem mapItem, final Runnable onSuccess, final Runnable onFailure) {
        userApi.editMapItem(mapItem, store.getToken()).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    onSuccess.run();
                }
                else {
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

}
