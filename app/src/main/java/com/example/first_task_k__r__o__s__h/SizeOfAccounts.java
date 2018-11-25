package com.example.first_task_k__r__o__s__h;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SizeOfAccounts {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("size")
    @Expose
    public String size;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
