package com.example.first_task_k__r__o__s__h.Authorization.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.R;


public class LoginActivitySignUpConfirmCodePhone extends AppCompatActivity {

    private static final String TODO_DOCUMENT="ToDoDocument";
    public static final String PHOTOS_URL="photoSURL";
    public static final String VIDEO_URL="videoURL";
    public static final String POSITION="position";
    public static final int DELETE_POST_REQUEST=2;
    public static final String GET_POST_ID="getPostId";
    public static final int TODO_NOTE_SETTING_REQUEST=3;
    public static final int EDIT_POST_REQUEST=4;
    public static final String OWN_MARKER="OwnMarker";
    public static final int RESULT_SUCCESS=4;
    public static final String NICK = "nick";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String CONFIRMATION_CODE="confCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up_confirm_code_phone);
        TextView massage = (TextView) findViewById(R.id.text_confirm_code);

        String s1 = getString(R.string.text_type_confirm_code_phone)+" ";
        String phone = getIntent().getExtras().getString(PHONE, "");
        String s2 =phone+". ";
        String s3 = getString(R.string.text_type_confirm_code_phone2)+" ";
        String s4 = getString(R.string.text_type_confirm_code_phone3)+" ";
        String s5 = getString(R.string.text_type_confirm_code_phone4);
        massage.setText(Html.fromHtml(s1+ s2 +"<b>"+s3+"</b>" + s4 + "<b>" + s5 + "</b>"));
        Button btnSend = (Button) findViewById(R.id.send_button_phone);
        final EditText editCode = (EditText) findViewById(R.id.confirmation_code) ;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().putExtra(CONFIRMATION_CODE, editCode.getText().toString());
                setResult(RESULT_SUCCESS, getIntent());
                finish();
            }
        });
    }
}
