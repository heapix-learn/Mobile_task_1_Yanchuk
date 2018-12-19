package com.example.first_task_k__r__o__s__h;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingMenuForFullView extends AppCompatActivity {
    private TextView deletePost;
    private TextView editPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu_for_full_view);
        deletePost= (TextView) findViewById(R.id.deletePostFromTextMenu);
        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(AppContext.DELETE_POST_REQUEST, getIntent());
                finish();
            }
        });

        editPost = (TextView) findViewById(R.id.editPostFromTextMenu);
        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(AppContext.EDIT_POST_REQUEST, getIntent());
                finish();
            }
        });
    }

}
