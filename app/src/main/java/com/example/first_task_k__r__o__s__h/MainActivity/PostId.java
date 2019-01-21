package com.example.first_task_k__r__o__s__h.MainActivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostId {
    @SerializedName("postId")
    @Expose
    private String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId=postId;
    }


}
