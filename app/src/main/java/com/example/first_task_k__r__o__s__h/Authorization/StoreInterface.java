package com.example.first_task_k__r__o__s__h.Authorization;

public interface StoreInterface {
    String getLogin();
    void saveLogin(String login);
    void saveUser(User user);
    User getUser();
    String getToken();
    void saveToken(String token);
}
