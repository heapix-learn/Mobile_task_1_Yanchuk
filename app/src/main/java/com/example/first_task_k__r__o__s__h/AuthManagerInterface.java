package com.example.first_task_k__r__o__s__h;


public interface AuthManagerInterface<Result> {

    void onSuccess(Result result);

    void onCancel();

    void onError(String error);

}
