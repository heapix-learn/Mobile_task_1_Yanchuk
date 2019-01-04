package com.example.first_task_k__r__o__s__h.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first_task_k__r__o__s__h.AuthManager;
import com.example.first_task_k__r__o__s__h.AuthManagerInterface;
import com.example.first_task_k__r__o__s__h.R;
import com.example.first_task_k__r__o__s__h.UserModel;
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

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    public static UserModel myUser;



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
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.username);
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



    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

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

            AuthManager.tryLoginWith(this, mUserNameView.getText().toString(), mPasswordView.getText().toString(), new AuthManagerInterface<UserModel>() {

                @Override
                public void onSuccess(UserModel userModel) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }


}

