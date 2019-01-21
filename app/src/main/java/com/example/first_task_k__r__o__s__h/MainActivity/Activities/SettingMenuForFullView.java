package com.example.first_task_k__r__o__s__h.MainActivity.Activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.R;


public class SettingMenuForFullView extends AppCompatActivity {

    public static final int DELETE_POST_REQUEST=2;
    public static final int EDIT_POST_REQUEST=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu_for_full_view);
        TextView deletePost = (TextView) findViewById(R.id.deletePostFromTextMenu);
        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(DELETE_POST_REQUEST, getIntent());
                finish();
            }
        });

        TextView editPost = (TextView) findViewById(R.id.editPostFromTextMenu);
        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(EDIT_POST_REQUEST, getIntent());
                finish();
            }
        });
        ConstraintLayout notContainerMenuSettingsText = (ConstraintLayout) findViewById(R.id.notContainerMenuSettingsText);
        notContainerMenuSettingsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
