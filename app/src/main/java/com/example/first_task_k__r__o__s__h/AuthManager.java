package com.example.first_task_k__r__o__s__h;

import com.example.first_task_k__r__o__s__h.WorkWithServer.Controller;
import com.example.first_task_k__r__o__s__h.WorkWithServer.UserApi;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthManager implements AuthManagerInterface {

    private AppContext.TypeOfAuthManagerError error;
    private AuthStoreInterface authStoreInterface = new Store();
    String login;
    private String token;

    private void addUser(final User myUser, final MyRunnable onFailure) {

        final UserApi userApi = Controller.getApi();
        userApi.getLastAccountNumber("1").enqueue(new Callback<NumberOfAccounts>() {
            @Override
            public void onResponse(Call<NumberOfAccounts> call, Response<NumberOfAccounts> response) {
                final NumberOfAccounts numberOfAccounts = response.body();

                numberOfAccounts.setSize((Integer.parseInt(numberOfAccounts.getSize()) + 1) + "");
                myUser.setId(numberOfAccounts.getSize());
                userApi.pushNewUser(myUser).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        userApi.updateLastAccountNumber("1", numberOfAccounts).enqueue(new Callback<NumberOfAccounts>() {
                            @Override
                            public void onResponse(Call<NumberOfAccounts> call, Response<NumberOfAccounts> response) {

                            }

                            @Override
                            public void onFailure(Call<NumberOfAccounts> call, Throwable t) {
                                onFailure.init(error);
                                onFailure.run();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        onFailure.init(error);
                        onFailure.run();
                    }
                });

            }

            @Override
            public void onFailure(Call<NumberOfAccounts> call, Throwable t) {
                onFailure.init(error);
                onFailure.run();
            }
        });

    }

    @Override
    public void tryLoginWith(final String login, final String password, final Runnable onSuccess, final MyRunnable onFailure) {
        final Runnable onSuccessUserCheck = getOnSuccessUserCheck(onSuccess);
        final Runnable onFailureUserCheck = new Runnable() {
            @Override
            public void run() {
                onFailure.init(error);
                onFailure.run();
            }
        };
        final UserApi userApi = Controller.getApi();
        this.login=login;
        User loginPasswordUser = new User();
        loginPasswordUser.setEmail(login);
        loginPasswordUser.setPassword(password);

        userApi.checkLogin(loginPasswordUser).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {

                if (response.body()==null) {
                    Gson gson = new Gson();
                    ServerAnswer serverAnswer=gson.fromJson(response.errorBody().charStream(),ServerAnswer.class);
                    error = AppContext.convertError(serverAnswer.getError());
                    onFailureUserCheck.run();
                    return;
                }
                if (response.body().getSuccess() && response.body().getToken()!=null){
                    token = response.body().getToken();
                    onSuccessUserCheck.run();
                }
                else {
                    error = AppContext.TypeOfAuthManagerError.USER_CHECK_ERROR;
                    onFailureUserCheck.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                error = AppContext.TypeOfAuthManagerError.SERVER_ERROR;
                onFailureUserCheck.run();
            }
        });
    }


    private void checkServerID(Call<List<User>> request, final Runnable onSuccess, final Runnable onFailure) {
        request.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                List<User> accounts = new ArrayList<>();
                accounts.addAll(response.body());
                if (accounts.size() != 0) {
                    onSuccess.run();
                } else {
                    onFailure.run();
                }
            }


            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                error = AppContext.TypeOfAuthManagerError.SERVER_ERROR;
                onFailure.run();
            }
        });
    }

    @Override
    public void tryLoginWithGoogle(final GoogleSignInAccount account, final Runnable onSuccess, final MyRunnable onFailure) {
        this.error = null;
        login=account.getEmail();

        final Runnable onSuccessUserCheck = getOnSuccessUserCheck(onSuccess);

        final UserApi userApi = Controller.getApi();
        checkServerID(userApi.checkGoogleID(account.getId()), onSuccessUserCheck, new Runnable() {
            @Override
            public void run() {
                if (error == AppContext.TypeOfAuthManagerError.SERVER_ERROR) {
                    onFailure.init(error);
                    onFailure.run();
                    return;
                }

                final User myUser = new User();
                myUser.setGoogleID(account.getId());
                myUser.setUserName(account.getEmail().substring(0, account.getEmail().indexOf("@")));
                myUser.setEmail(account.getEmail());
                myUser.setFullName(account.getDisplayName());
                addUser(myUser, onFailure);
                authStoreInterface.saveUser(myUser);
            }
        });

    }

    @Override
    public void tryLoginWithFacebook(final Profile account, final Runnable onSuccess, final MyRunnable onFailure) {
        this.error = null;
        login=account.getName();
        final Runnable onSuccessUserCheck = getOnSuccessUserCheck(onSuccess);

        final UserApi userApi = Controller.getApi();
        checkServerID(userApi.checkFacebookID(account.getId()), onSuccessUserCheck, new Runnable() {
            @Override
            public void run() {
                if (error == AppContext.TypeOfAuthManagerError.SERVER_ERROR) {
                    onFailure.init(error);
                    onFailure.run();
                    return;
                }

                final User myUser = new User();
                myUser.setFacebookID(account.getId());
                myUser.setFullName(account.getName());
                myUser.setUserName(account.getName());

                addUser(myUser, onFailure);
                authStoreInterface.saveUser(myUser);
            }



        });
    }

    @Override
    public void tryLoginWithStoredInfo(final Runnable onSuccess, final Runnable onFailure) {

        User myUser = authStoreInterface.getUser();


        Runnable onSuccessCheck = new Runnable() {
            @Override
            public void run() {
                onSuccess.run();
            }
        };
        Runnable onFailureCheck = new Runnable() {
            @Override
            public void run() {
                onFailure.run();

            }
        };

        if (myUser==null){
            onFailure.run();
            return;
        }

        final UserApi userApi = Controller.getApi();
        checkServerID(userApi.checkID(myUser.getId()), onSuccessCheck, onFailureCheck);

        onFailureCheck.run();
    }

    @Override
    public String getStoredLogin() {
        return authStoreInterface.getLogin();
    }

    @Override
    public void tryRegistrationWith(String login, String password, String firstName, String LastName, Runnable onSuccess, final MyRunnable onFailure) {
        final Runnable onSuccessUserCheck = getOnSuccessUserCheck(onSuccess);
        final Runnable onFailureUserCheck = new Runnable() {
            @Override
            public void run() {
                onFailure.init(error);
                onFailure.run();
            }
        };
        final UserApi userApi = Controller.getApi();
        User myUser = new User();
        myUser.setEmail(login);
        myUser.setPassword(password);


        userApi.SignUp(myUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.errorBody()!=null) {
                    Gson gson = new Gson();
                    ServerAnswer serverAnswer=gson.fromJson(response.errorBody().charStream(),ServerAnswer.class);
                    error = AppContext.convertError(serverAnswer.getError());
                    onFailureUserCheck.run();
                }
                else{
                    onSuccessUserCheck.run();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                error = AppContext.TypeOfAuthManagerError.SERVER_ERROR;
                onFailureUserCheck.run();
            }
        });
    }

    private Runnable getOnSuccessUserCheck(final Runnable onSuccess){
            return new Runnable() {
            @Override
            public void run() {
                authStoreInterface.saveLogin(login);
                authStoreInterface.saveToken(token);
                onSuccess.run();
            }
        };

    }


}