package com.example.first_task_k__r__o__s__h;

public interface AuthStoreInterface {
    String getLogin();
    void saveLogin(String login);
    void saveUser(User user);
    User getUser();
    String getToken();
    void saveToken(String token);

}
