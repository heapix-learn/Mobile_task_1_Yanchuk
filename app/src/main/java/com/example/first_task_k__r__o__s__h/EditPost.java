package com.example.first_task_k__r__o__s__h;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

public class EditPost extends Note {
    public TextView noteTitle;
    public ImageButton garbage;



    @Override
    protected void AddGarbage(){
        noteTitle = (TextView) findViewById(R.id.new_post);
        noteTitle.setText("Edit post");
        garbage = (ImageButton) findViewById(R.id.garbage);
        garbage.setVisibility(View.VISIBLE);
    }

}
