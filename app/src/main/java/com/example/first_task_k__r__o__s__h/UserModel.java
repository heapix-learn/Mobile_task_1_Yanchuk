package com.example.first_task_k__r__o__s__h;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class UserModel {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("password")
        @Expose
        private String password;

        public UserModel(){}

        public String getId() {
            return id;
        }

        public void setId(String userId) {
            this.id = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

