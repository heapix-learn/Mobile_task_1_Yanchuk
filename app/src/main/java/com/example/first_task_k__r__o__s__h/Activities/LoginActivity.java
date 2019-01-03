package com.example.first_task_k__r__o__s__h.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first_task_k__r__o__s__h.AppContext;
import com.example.first_task_k__r__o__s__h.NumberOfAccounts;
import com.example.first_task_k__r__o__s__h.R;
import com.example.first_task_k__r__o__s__h.UserModel;
import com.example.first_task_k__r__o__s__h.WorkWithServer.Controller;
import com.example.first_task_k__r__o__s__h.WorkWithServer.UserApi;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    public static UserModel myUser;

    private final static String G_PLUS_SCOPE ="oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE ="https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE ="https://www.googleapis.com/auth/userinfo.email";
    private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;
    private final static UserApi userApi=Controller.getApi();
    private int index;
    private List<UserModel> accounts;
    private UserLoginTask mAuthTask = null;
    public int  RC_SIGN_IN = 119;
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;


    private Locale myLocale;
    private Button btn_en, btn_ru;
    private TextView createAccount;
    private Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createAccount = (TextView) findViewById(R.id.create_acco);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // Set up the login form.
        accounts = new ArrayList<>();
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.username);
        populateAutoComplete();
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mPasswordView = (EditText) findViewById(R.id.password);
        View btn = (View) findViewById(R.id.google_sign_in);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginButton facebookSignIn = (LoginButton) findViewById(R.id.facebook_sign_in);
        facebookSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
                String userId = loginResult.getAccessToken().getUserId();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        btn_en = (Button) findViewById(R.id.lang_en);
        btn_ru = (Button) findViewById(R.id.lang_ru);


        btn_en.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang = "en";
                changeLang(lang);
            }
        });
        btn_ru.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang = "ru";
                changeLang(lang);
            }
        });

     //   loadLocale();

    }


    private void changeLang(String lang)
    {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    public void saveLocale(String lang)
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }


    public void loadLocale()
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    private void updateTexts()
    {

//        mUserNameView.setHint(R.string.username);
//        mPasswordView.setHint(R.string.prompt_password);
//        btn_ru.setText(R.string.russian);
//        btn_en.setText(R.string.english);
//        createAccount.setText(R.string.create_acco);
//        mEmailSignInButton.setText(R.string.login);

        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mUserNameView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Intent myIntent;
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            // updateUI(account);
            myUser = new UserModel();
            myUser.setEmail(account.getEmail());
//            myUser.setUserName(account.getDisplayName());
//            myUser.setFullName(account.ge);
            myIntent = new Intent(LoginActivity.this,MapsActivity.class);
            LoginActivity.this.startActivity(myIntent);
            signOut();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }



    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }
//        } else if (!isEmailValid(userName)) {
//            mUserNameView.setError(getString(R.string.error_invalid_user_name));
//            focusView = mUserNameView;
//            cancel = true;
//        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuthTask = new UserLoginTask(userName, password, this);
            mAuthTask.execute((Void) null);
        }


    }

    private boolean isEmailValid(String userName) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mUserNameView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mUserName;
        private final String mPassword;
        private final String mPhone;
        private final String mEmail;

        private final Context mContext;

        UserLoginTask(String info, String password, Context context) {
            mUserName = info;
            mPassword = password;
            mPhone = info;
            mEmail = info;
            mContext= context;
        }



        @Override
        protected Integer doInBackground(Void... params) {
            int ans;
            try {
                accounts.addAll(userApi.checkLoginUserName(mUserName).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (accounts.size()!=0) {
                try {
                    myUser = accounts.get(0);
                    if ((myUser.getPassword().equals(mPassword)) && (myUser.getUserName().equals(mUserName) || myUser.getEmail().equals(mUserName)) || myUser.getPhone().equals(mUserName))
                        return AppContext.SUCCESS_LOGIN;
                    else if (myUser.getUserName().equals(mUserName))
                        return AppContext.PASSWORD_ERROR;
                } finally {
                    accounts.clear();
                }
            }



            try {
                accounts.addAll(userApi.checkLoginPhone(mPhone).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (accounts.size()!=0) {
                try {
                    myUser = accounts.get(0);
                    if ((myUser.getPassword().equals(mPassword)) && (myUser.getPhone().equals(mPhone)))
                        return AppContext.SUCCESS_LOGIN;
                    else if (myUser.getPhone().equals(mPhone)) return AppContext.PASSWORD_ERROR;
                } finally {
                    accounts.clear();
                }
            }



            try {
                accounts.addAll(userApi.checkLoginEmail(mEmail).execute().body());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (accounts.size()!=0) {
                try {
                    myUser = accounts.get(0);
                    if ((myUser.getPassword().equals(mPassword)) && (myUser.getEmail().equals(mEmail)))
                        return AppContext.SUCCESS_LOGIN;
                    else if (myUser.getEmail().equals(mEmail)) return AppContext.PASSWORD_ERROR;
                } finally {
                    accounts.clear();
                }
            }


            return AppContext.USER_NAME_ERROR;
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
     //       showProgress(false);

            if (success==AppContext.SUCCESS_LOGIN) {
                if (!myUser.getId().equals("-1")){
                    Intent myIntent = new Intent(LoginActivity.this,MapsActivity.class);
                    LoginActivity.this.startActivity(myIntent);
                    ///finish();
                } else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    userApi.getLastAccountNumber("1").enqueue(new Callback<NumberOfAccounts>() {
                                        @Override
                                        public void onResponse(Call<NumberOfAccounts> call, Response<NumberOfAccounts> response) {
                                            index=Integer.parseInt(response.body().getSize());
                                            myUser.setId(String.valueOf(index + 1));

                                            Controller.pushNewUser(myUser, new Callback<UserModel>() {
                                                @Override
                                                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                                    Toast myToast = Toast.makeText(mContext, R.string.updatingReport, Toast.LENGTH_SHORT);
                                                    myToast.show();

                                                    userApi.deleteLastAccountNumber("1").enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            NumberOfAccounts numberOfAccounts=new NumberOfAccounts();
                                                            numberOfAccounts.setId("1");
                                                            numberOfAccounts.setSize(String.valueOf(index+1));
                                                            userApi.pushLastAccountNumber(numberOfAccounts).enqueue(new Callback<NumberOfAccounts>() {
                                                                @Override
                                                                public void onResponse(Call<NumberOfAccounts> call, Response<NumberOfAccounts> response) {
                                                                    Intent myIntent = new Intent(LoginActivity.this, MapsActivity.class);
                                                                    LoginActivity.this.startActivity(myIntent);
                                                                }
                                                                @Override
                                                                public void onFailure(Call<NumberOfAccounts> call, Throwable t) {
                                                                    Toast.makeText(LoginActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        }
                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                            Toast.makeText(LoginActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onFailure(Call<UserModel> call, Throwable t) {
                                                    Toast.makeText(LoginActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        @Override
                                        public void onFailure(Call<NumberOfAccounts> call, Throwable t) {
                                            Toast.makeText(LoginActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                                    mPasswordView.requestFocus();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                    builder.setMessage(R.string.confirm_registry).setPositiveButton(R.string.yes, dialogClickListener)
                            .setNegativeButton(R.string.no, dialogClickListener).show();
                }
            } else {
                if (success==AppContext.PASSWORD_ERROR) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
                else {
                    mUserNameView.setError(getString(R.string.error_incorrect_user_name));
                    mUserNameView.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
     //       showProgress(false);
        }




    }
}

