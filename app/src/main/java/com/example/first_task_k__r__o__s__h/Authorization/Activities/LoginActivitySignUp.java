package com.example.first_task_k__r__o__s__h.Authorization.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.first_task_k__r__o__s__h.R;

public class LoginActivitySignUp extends AppCompatActivity {
    private EditText nick;
    private EditText password;
    private EditText name;
    private int VERIFICATION_SEND_REQUEST = 9;
    private static final String NICK = "nick";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        nick = (EditText) findViewById(R.id.nikName);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);

        Button btnNext = (Button) findViewById(R.id.next_button_sign_up);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nick.getText().toString().isEmpty())  {
                    nick.setError("nickName is Empty");
                    return;
                }
                if (password.getText().toString().length()<6)  {
                    password.setError("Short password");
                    return;
                }
                if (name.getText().toString().isEmpty())  {
                    name.setError("name is Empty");
                    return;
                }
                Intent myIntent = new Intent(LoginActivitySignUp.this, LoginActivityVerification.class);
                myIntent.putExtra(NICK, nick.getText().toString());
                myIntent.putExtra(NAME, name.getText().toString());
                myIntent.putExtra(PASSWORD, password.getText().toString());
                startActivityForResult(myIntent, VERIFICATION_SEND_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==VERIFICATION_SEND_REQUEST){
            setResult(resultCode, getIntent());
            finish();
        }
    }
}
