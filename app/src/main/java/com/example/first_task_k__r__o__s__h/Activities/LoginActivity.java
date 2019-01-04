package com.example.first_task_k__r__o__s__h.Activities;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
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
import com.example.first_task_k__r__o__s__h.R;
import com.example.first_task_k__r__o__s__h.UserModel;
import com.example.first_task_k__r__o__s__h.WorkWithServer.Controller;
import com.example.first_task_k__r__o__s__h.WorkWithServer.UserApi;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int REQUEST_READ_CONTACTS = 0;
    public static UserModel myUser;

    private final static UserApi userApi=Controller.getApi();
    private List<UserModel> accounts;
    private UserLoginTask mAuthTask = null;
    public int  RC_SIGN_IN = 119;
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView createAccount = (TextView) findViewById(R.id.create_acco);

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

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
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

    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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
            myUser = new UserModel();
            myUser.setEmail(account.getEmail());
            myIntent = new Intent(LoginActivity.this,MapsActivity.class);
            LoginActivity.this.startActivity(myIntent);
            signOut();
        } catch (ApiException e) {
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mUserNameView.setError(null);
        mPasswordView.setError(null);

        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuthTask = new UserLoginTask(userName, password, this);
            mAuthTask.execute((Void) null);
        }


    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
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
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mUserName;
        private final String mPassword;
        private final String mPhone;
        private final String mEmail;

        UserLoginTask(String info, String password, Context context) {
            mUserName = info;
            mPassword = password;
            mPhone = info;
            mEmail = info;
        }



        @Override
        protected Integer doInBackground(Void... params) {
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

            if (success==AppContext.SUCCESS_LOGIN) {
                if (!myUser.getId().equals("-1")){
                    Intent myIntent = new Intent(LoginActivity.this,MapsActivity.class);
                    LoginActivity.this.startActivity(myIntent);
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
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
        }

    }
}

