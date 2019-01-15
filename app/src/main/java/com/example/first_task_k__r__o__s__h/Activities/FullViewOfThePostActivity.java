package com.example.first_task_k__r__o__s__h.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.Adapters.PhotoAdapterGrid;
import com.example.first_task_k__r__o__s__h.Adapters.VideoAdapterGrid;
import com.example.first_task_k__r__o__s__h.R;
import com.example.first_task_k__r__o__s__h.ToDoDocuments;

public class FullViewOfThePostActivity extends AppCompatActivity {
    private ToDoDocuments todoDocument;
    private TextView textTitle;
    private TextView textDescription;
    private TextView textNumberOfVideo;
    private TextView textNumberOfPhoto;
    private GridView gridViewForPhoto;
    private GridView gridViewForVideo;
    private PhotoAdapterGrid photoAdapterGrid;
    private Bundle bundle;
    private View lineForAddMedia;
    private static final String TODO_DOCUMENT="ToDoDocument";
    public static final int DELETE_POST_REQUEST=2;
    public static final int TODO_NOTE_SETTING_REQUEST=3;
    public static final int EDIT_POST_REQUEST=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        setContentView(R.layout.activity_full_view_of_the_post);
        todoDocument = getIntent().getParcelableExtra(TODO_DOCUMENT);

        textTitle = (TextView) findViewById(R.id.textTitleFullView);
        textDescription=(TextView) findViewById(R.id.textDescriptionFullView);
        gridViewForPhoto = (GridView) findViewById(R.id.gridViewForPhotoFullView);
        gridViewForVideo = (GridView) findViewById(R.id.gridViewForVideoFullView);
        textNumberOfPhoto = (TextView) findViewById(R.id.number_of_photoFullView);
        textNumberOfVideo = (TextView) findViewById(R.id.number_of_videoFullView);
        lineForAddMedia = (View) findViewById(R.id.lineForAddMedia_activity_note);
        textTitle.setText(todoDocument.getTitle());
        textDescription.setText(todoDocument.getTextNote());
        if (todoDocument.getImagePath().size()!=0) {
            textNumberOfPhoto.setVisibility(View.VISIBLE);
            textNumberOfPhoto.setText(todoDocument.getImagePath().size() + " Photos");
            gridViewForPhoto.setVisibility(View.VISIBLE);
            photoAdapterGrid = new PhotoAdapterGrid(this, bundle, todoDocument.getImagePath(), textNumberOfPhoto, lineForAddMedia, null, gridViewForPhoto, false);
            gridViewForPhoto.setAdapter(photoAdapterGrid);
        }
        if (todoDocument.getVideoPath().size()!=0){
            textNumberOfVideo.setVisibility(View.VISIBLE);
            textNumberOfVideo.setText(todoDocument.getVideoPath().size() + " Videos");
            gridViewForVideo.setVisibility(View.VISIBLE);
            gridViewForVideo.setAdapter(new VideoAdapterGrid(this, bundle, todoDocument.getVideoPath(), todoDocument.getVideoScreen(), textNumberOfVideo, lineForAddMedia, null, gridViewForVideo, false));
        }
    }

    public void onClickSettingsPost(View v){
        Intent myIntent = new Intent(this, SettingMenuForFullView.class);
        startActivityForResult(myIntent, TODO_NOTE_SETTING_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TODO_NOTE_SETTING_REQUEST) {
            switch (resultCode) {
                case DELETE_POST_REQUEST:
                    setResult(DELETE_POST_REQUEST, getIntent());
                    finish();
                    break;
                case EDIT_POST_REQUEST:
                    setResult(EDIT_POST_REQUEST, getIntent());
                    finish();
                    break;
            }
        }
    }


}
