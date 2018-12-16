package com.example.first_task_k__r__o__s__h;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class FullViewOfThePostActivity extends AppCompatActivity {
    private ToDoDocuments todoDocument;
    private TextView textTitle;
    private TextView textDescription;
    private TextView textNumberOfVideo;
    private TextView textNumberOfPhoto;
    private GridView gridViewForPhoto;
    private GridView gridViewForVideo;
    private PhotoAdapterGrid photoAdapterGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view_of_the_post);
        todoDocument = getIntent().getParcelableExtra(AppContext.TODO_DOCUMENT);

        textTitle = (TextView) findViewById(R.id.textTitleFullView);
        textDescription=(TextView) findViewById(R.id.textDescriptionFullView);
        gridViewForPhoto = (GridView) findViewById(R.id.gridViewForPhotoFullView);
        gridViewForVideo = (GridView) findViewById(R.id.gridViewForVideoFullView);
        textNumberOfPhoto = (TextView) findViewById(R.id.number_of_photoFullView);
        textNumberOfVideo = (TextView) findViewById(R.id.number_of_videoFullView);
        textTitle.setText(todoDocument.getTitle());
        textDescription.setText(todoDocument.getTextNote());
        if (todoDocument.getImagePath().size()!=0) {
            textNumberOfPhoto.setVisibility(View.VISIBLE);
            textNumberOfPhoto.setText(todoDocument.getImagePath().size() + " Photos");
            gridViewForPhoto.setVisibility(View.VISIBLE);
            photoAdapterGrid = new PhotoAdapterGrid(this, todoDocument.getImagePath(), textNumberOfPhoto, gridViewForPhoto, false);
            gridViewForPhoto.setAdapter(photoAdapterGrid);
        }
        if (todoDocument.getVideoPath().size()!=0){
            textNumberOfVideo.setVisibility(View.VISIBLE);
            textNumberOfVideo.setText(todoDocument.getVideoPath().size() + " Videos");
            gridViewForVideo.setVisibility(View.VISIBLE);
            gridViewForVideo.setAdapter(new VideoAdapterGrid(this, todoDocument.getVideoPath(), textNumberOfVideo, gridViewForVideo, false));
        }
    }

    public void onClickSettingsPost(View v){
        Intent myIntent = new Intent(this, SettingMenuForFullView.class);
        startActivityForResult(myIntent, AppContext.TODO_NOTE_REQUEST);
    }


}
