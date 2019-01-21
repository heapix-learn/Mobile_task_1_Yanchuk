package com.example.first_task_k__r__o__s__h;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.first_task_k__r__o__s__h.Authorization.Activities.LoginActivity;
import com.example.first_task_k__r__o__s__h.Authorization.AuthManager;
import com.example.first_task_k__r__o__s__h.Authorization.Interfaces.AuthManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.Activities.MainActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final AuthManagerInterface authManagerInterface = AuthManager.getInstance();

        Runnable onSuccess = new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        };

        Runnable onFailure = new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        };
        authManagerInterface.tryLoginWithStoredInfo(onSuccess, onFailure);
    }
}
