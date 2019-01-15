package com.example.first_task_k__r__o__s__h.Authorization.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first_task_k__r__o__s__h.Activities.MapsActivity;
import com.example.first_task_k__r__o__s__h.StoreInterface;
import com.example.first_task_k__r__o__s__h.Authorization.AuthManager;
import com.example.first_task_k__r__o__s__h.Authorization.Interfaces.AuthManagerInterface;
import com.example.first_task_k__r__o__s__h.Authorization.RunnableWithError;
import com.example.first_task_k__r__o__s__h.R;
import com.example.first_task_k__r__o__s__h.Store;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
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

    private static final int RESULT_SUCCESS=4;
    public int  RC_SIGN_IN = 119;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private int SIGN_UP_REQUEST = 8;
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private CallbackManager callbackManager;
    private AuthManagerInterface authManager = AuthManager.getInstance();
    private StoreInterface store  =Store.getInstance();
    private Runnable onSuccess = new Runnable() {
    @Override
    public void run() {
            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
            startActivity(intent);
        }
    };
    private RunnableWithError onFailure = new RunnableWithError() {
        @Override
        public void run() {
            Toast.makeText(LoginActivity.this, "" + this.getError().getDescription(),  Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView createAccount = (TextView) findViewById(R.id.create_acco);
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        final Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_auth_default_web_client_id))
                .requestEmail()
                .build();
        View btn = (View) findViewById(R.id.google_sign_in);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

        });

        callbackManager = CallbackManager.Factory.create();
        final LoginButton facebookSignIn = new LoginButton(this);
        Button fb = (Button) findViewById(R.id.facebook_sign_in);
        facebookSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();
                authManager.tryLoginWithFacebook(profile, onSuccess, onFailure);
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
        mUserNameView.setText(store.getLogin());

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSignIn.callOnClick();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, LoginActivitySignUp.class);
                startActivityForResult(myIntent, SIGN_UP_REQUEST);
            }
        });

        TextView forgotPassword = (TextView) findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginActivityForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        authManager.tryLoginWith(mUserNameView.getText().toString(), mPasswordView.getText().toString(), onSuccess, onFailure);
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
        if (requestCode==SIGN_UP_REQUEST){
            if (resultCode==RESULT_SUCCESS){
                finish();
            }
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            authManager.tryLoginWithGoogle(account, onSuccess, onFailure);
            signOut();
        } catch (ApiException e) {
        }
    }

}
