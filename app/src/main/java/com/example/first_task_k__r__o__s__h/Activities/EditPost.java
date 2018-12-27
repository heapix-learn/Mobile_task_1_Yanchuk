package com.example.first_task_k__r__o__s__h.Activities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.R;

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
