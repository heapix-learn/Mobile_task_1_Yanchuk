package com.example.first_task_k__r__o__s__h;

import android.content.Context;

import com.example.first_task_k__r__o__s__h.WorkWithServer.Controller;
import com.example.first_task_k__r__o__s__h.WorkWithServer.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManagerSystem implements AuthManagerInterface<UserModel>{


    @Override
    public void onSuccess(UserModel userModel) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(String error) {

    }

    static void tryLoginWith(final Context context, String login, String password, final AuthManagerInterface listener) {

        final String mUserName;
        final String mPassword;
        final String mPhone;
        final String mEmail;
        final UserApi userApi=Controller.getApi();
        final UserModel[] myUser = {null};
        final List<UserModel> accounts= new ArrayList<>();
        final String[] error = {null};
        final int[] result = {-1};

        mUserName = login;
        mPassword=password;
        mPhone=login;
        mEmail=login;


        userApi.checkLoginUserName(mUserName).enqueue(new Callback<List<UserModel>>() {

            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                accounts.addAll(response.body());
                if (accounts.size()!=0) {
                        myUser[0] = accounts.get(0);
                        if ((myUser[0].getPassword().equals(mPassword)) && (myUser[0].getUserName().equals(mUserName) || myUser[0].getEmail().equals(mUserName)) || myUser[0].getPhone().equals(mUserName))
                            result[0] = AppContext.SUCCESS_LOGIN;
                        else if (myUser[0].getUserName().equals(mUserName))
                            result[0] = AppContext.PASSWORD_ERROR;
                        accounts.clear();
                }
                if (result[0]!=-1) {
                    if (result[0]==AppContext.SUCCESS_LOGIN) {
                        if (myUser[0] != null) {
                            listener.onSuccess(myUser[0]);
                            return;
                        }
                    } else {
                        error[0] = context.getString(R.string.error_incorrect_login_password);
                    }
                    if (error[0] ==null) {
                        listener.onCancel();
                    }else {
                        listener.onError(error[0]);
                    }
                    return;
                }

                userApi.checkLoginPhone(mPhone).enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        accounts.addAll(response.body());
                        if (accounts.size() != 0) {
                            myUser[0] = accounts.get(0);
                            if ((myUser[0].getPassword().equals(mPassword)) && (myUser[0].getPhone().equals(mPhone)))
                                result[0] = AppContext.SUCCESS_LOGIN;
                            else if (myUser[0].getPhone().equals(mPhone))
                                result[0] = AppContext.PASSWORD_ERROR;
                            accounts.clear();
                        }
                        if (result[0]!=-1) {
                            if (result[0]==AppContext.SUCCESS_LOGIN) {
                                if (myUser[0] != null) {
                                    listener.onSuccess(myUser[0]);
                                    return;
                                }
                            } else {
                                error[0] = context.getString(R.string.error_incorrect_login_password);
                            }
                            if (error[0] ==null) {
                                listener.onCancel();
                            }else {
                                listener.onError(error[0]);
                            }
                            return;
                        }

                        userApi.checkLoginEmail(mEmail).enqueue(new Callback<List<UserModel>>() {
                            @Override
                            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                                accounts.addAll(response.body());
                                if (accounts.size() != 0) {

                                    myUser[0] = accounts.get(0);
                                    if ((myUser[0].getPassword().equals(mPassword)) && (myUser[0].getEmail().equals(mEmail)))
                                        result[0] = AppContext.SUCCESS_LOGIN;
                                    else if (myUser[0].getEmail().equals(mEmail))
                                        result[0] = AppContext.PASSWORD_ERROR;

                                    accounts.clear();
                                }
                                if (result[0] != -1) {
                                    if (result[0] == AppContext.SUCCESS_LOGIN) {
                                        if (myUser[0] != null) {
                                            listener.onSuccess(myUser[0]);
                                            return;
                                        }
                                    } else {
                                        error[0] = context.getString(R.string.error_incorrect_login_password);
                                    }
                                    if (error[0] == null) {
                                        listener.onCancel();
                                    } else {
                                        listener.onError(error[0]);
                                    }
                                    return;
                                }
                                result[0] = AppContext.USER_NAME_ERROR;
                                error[0] = context.getString(R.string.error_incorrect_login_password);
                                listener.onError(error[0]);
                            }

                            @Override
                            public void onFailure(Call<List<UserModel>> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });


    }


}
