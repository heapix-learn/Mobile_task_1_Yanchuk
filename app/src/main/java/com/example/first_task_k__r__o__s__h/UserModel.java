package com.example.first_task_k__r__o__s__h;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class UserModel {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userName")
        @Expose
        public String userName;
        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("fullName")
        @Expose
        private String fullName;

        @SerializedName("phone")
        @Expose
        private String phone;


        public UserModel(){}

        public String getId() {
            return id;
        }

        public void setId(String userId) {
            this.id = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String username) {
            this.userName = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

