package com.example.first_task_k__r__o__s__h.Authorization.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.R;

public class LoginActivitySignUpConfirmCodeEmail extends AppCompatActivity {
    private static final int RESULT_SUCCESS=4;
    private static final String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up_confirm_code_email);
        TextView massage = (TextView) findViewById(R.id.text_confirm_code);

        String s1 = getString(R.string.text_type_confirm_code_email)+" ";
        String email = getIntent().getExtras().getString(EMAIL, "");
        String s2 =email+". ";
        String s3 = getString(R.string.text_type_confirm_code_email2)+" ";
        String s4 = getString(R.string.text_type_confirm_code_email3)+" ";
        String s5 = getString(R.string.text_type_confirm_code_email4);
        massage.setText(Html.fromHtml(s1+ s2 +"<b>"+s3+"</b>" + s4 + "<b>" + s5 + "</b>"));

        Button btnSend = (Button) findViewById(R.id.send_button_email);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_SUCCESS);
                finish();
            }
        });
    }
}
