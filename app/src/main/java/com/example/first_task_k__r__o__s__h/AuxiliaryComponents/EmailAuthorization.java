package com.example.first_task_k__r__o__s__h.AuxiliaryComponents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.first_task_k__r__o__s__h.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailAuthorization extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String TAG = "FirebaseEmail";
    private FirebaseUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_authorization);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        Button getVerificationLink = (Button) findViewById(R.id.getVerificationLinkEmail);
        Button SignIn = (Button) findViewById(R.id.signInEmail);

        mAuth = FirebaseAuth.getInstance();



        getVerificationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfo();

            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser!=null){
                    myUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EmailAuthorization.this, myUser.isEmailVerified()+"",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void setInfo(){
        String email=editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()){
            editTextEmail.setError("Email is empty!");
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is empty!");
            return;
        }



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            editTextEmail.setError("Email is exist!");
                            updateUI(null);
                        }

                    }
                });
    }
    private void updateUI(FirebaseUser user){
        myUser=user;
        setVerificationLink();
    }
    private void setVerificationLink(){
        if (myUser!=null){
            myUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(EmailAuthorization.this,"Verification email sent to " + myUser.getEmail(),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EmailAuthorization.this,"Failed to sent verification email!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}
