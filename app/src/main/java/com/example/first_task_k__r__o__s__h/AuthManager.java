package com.example.first_task_k__r__o__s__h;

import com.example.first_task_k__r__o__s__h.WorkWithServer.Controller;
import com.example.first_task_k__r__o__s__h.WorkWithServer.UserApi;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthManager implements AuthManagerInterface {

    private AppContext.TypeOfAuthManagerError error;
    private AuthStoreInterface authStoreInterface = new Store();

    private void addUser(final UserModel myUser, final MyRunnable onFailure) {

        final UserApi userApi = Controller.getApi();
        userApi.getLastAccountNumber("1").enqueue(new Callback<NumberOfAccounts>() {
            @Override
            public void onResponse(Call<NumberOfAccounts> call, Response<NumberOfAccounts> response) {
                final NumberOfAccounts numberOfAccounts = response.body();

                numberOfAccounts.setSize((Integer.parseInt(numberOfAccounts.getSize()) + 1) + "");
                myUser.setId(numberOfAccounts.getSize());
                userApi.pushNewUser(myUser).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
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
                    public void onFailure(Call<UserModel> call, Throwable t) {
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

    private boolean goResponse(Response<List<UserModel>> response, String password) {
        int result = -1;
        UserModel myUser;
        List<UserModel> accounts = new ArrayList<>();

        accounts.addAll(response.body());
        if (accounts.size() != 0) {
            myUser = accounts.get(0);
            if (myUser.getPassword().equals(password)) result = AppContext.SUCCESS_LOGIN;
            accounts.clear();
        }

        return result == AppContext.SUCCESS_LOGIN;
    }

    private void checkUser(Call<List<UserModel>> request, final String password, final Runnable onSuccess, final Runnable onFailure) {

        request.enqueue(new Callback<List<UserModel>>() {

            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                boolean status;
                status = goResponse(response, password);
                if (status) {
                    authStoreInterface.saveUser(response.body().get(0));
                    onSuccess.run();
                }
                else {
                    error = AppContext.TypeOfAuthManagerError.USER_CHECK_ERROR;
                    onFailure.run();

                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                error = AppContext.TypeOfAuthManagerError.SERVER_ERROR;
                onFailure.run();
            }
        });
    }

    @Override
    public void tryLoginWith(final String login, final String password, final Runnable onSuccess, final MyRunnable onFailure) {
        this.error = null;
        String token = "1234567890";
        final Runnable onSuccessUserCheck = getOnSuccessUserCheck(login,token, onSuccess);

        final Runnable onFailureUserCheck = new Runnable() {
            @Override
            public void run() {
                onFailure.init(error);
                onFailure.run();
            }
        };
        final UserApi userApi = Controller.getApi();
        checkUser(userApi.checkLoginUserName(login), password, onSuccessUserCheck, new Runnable() {
            @Override
            public void run() {
                if (error == AppContext.TypeOfAuthManagerError.SERVER_ERROR) {
                    onFailure.init(error);
                    onFailure.run();
                    return;
                }
                checkUser(userApi.checkLoginPhone(login), password, onSuccessUserCheck, new Runnable() {
                    @Override
                    public void run() {
                        checkUser(userApi.checkLoginEmail(login), password, onSuccessUserCheck, onFailureUserCheck);
                    }
                });
            }
        });
    }

    private void checkServerID(Call<List<UserModel>> request, final Runnable onSuccess, final Runnable onFailure) {
        request.enqueue(new Callback<List<UserModel>>() {

            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                List<UserModel> accounts = new ArrayList<>();
                accounts.addAll(response.body());
                if (accounts.size() != 0) {
                    onSuccess.run();
                } else {
                    onFailure.run();
                }
            }


            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                error = AppContext.TypeOfAuthManagerError.SERVER_ERROR;
                onFailure.run();
            }
        });
    }

    @Override
    public void tryLoginWithGoogle(final GoogleSignInAccount account, final Runnable onSuccess, final MyRunnable onFailure) {
        this.error = null;
        String token = "1234567890";

        final Runnable onSuccessUserCheck = getOnSuccessUserCheck(account.getEmail(),token, onSuccess);

        final UserApi userApi = Controller.getApi();
        checkServerID(userApi.checkGoogleID(account.getId()), onSuccessUserCheck, new Runnable() {
            @Override
            public void run() {
                if (error == AppContext.TypeOfAuthManagerError.SERVER_ERROR) {
                    onFailure.init(error);
                    onFailure.run();
                    return;
                }

                final UserModel myUser = new UserModel();
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
        String token = "1234567890";

        final Runnable onSuccessUserCheck = getOnSuccessUserCheck(account.getName(), token, onSuccess);

        final UserApi userApi = Controller.getApi();
        checkServerID(userApi.checkFacebookID(account.getId()), onSuccessUserCheck, new Runnable() {
            @Override
            public void run() {
                if (error == AppContext.TypeOfAuthManagerError.SERVER_ERROR) {
                    onFailure.init(error);
                    onFailure.run();
                    return;
                }

                final UserModel myUser = new UserModel();
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

        UserModel myUser = authStoreInterface.getUser();


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

  

    private Runnable getOnSuccessUserCheck(final String login, final String token, final Runnable onSuccess){
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