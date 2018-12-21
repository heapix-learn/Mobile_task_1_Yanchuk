package com.example.first_task_k__r__o__s__h;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MarkerPreview extends AppCompatActivity {
    private ImageButton like;
    private ImageButton subscription;
    private TextView noteTitle;
    private ToDoDocuments toDoDocuments;
    private LinearLayout picGallery;
    private PicAdapter imgAdapt;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_preview);
        like = (ImageButton) findViewById(R.id.like);
        subscription = (ImageButton) findViewById(R.id.subscription);
        noteTitle = (TextView) findViewById(R.id.titleMarkerPreview);
        picGallery = (LinearLayout) findViewById(R.id.gallery_preview);

        String id = getIntent().getExtras().getString("markerId", "");
        toDoDocuments = DBNotes.getOneNotesFromId(id);
        noteTitle.setText(toDoDocuments.getTitle());
        imgAdapt = new PicAdapter(this, toDoDocuments.getImagePath(), toDoDocuments.getVideoPath(), toDoDocuments.getVideoScreen());
        picGallery.setDividerPadding(5);
        for (int i=0; i<imgAdapt.getCount(); i++) {
            View add = imgAdapt.getView(i, null, null);
            add.setId(i);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(MarkerPreview.this, FullScreenGallery.class);
                    myIntent.putExtra(AppContext.VIDEO_URL, AppContext.ListPathToString(toDoDocuments.getVideoPath()));
                    myIntent.putExtra(AppContext.PHOTOS_URL, AppContext.ListPathToString(toDoDocuments.getImagePath()));
                    myIntent.putExtra(AppContext.POSITION, v.getId());
                    startActivity(myIntent);
                }
            });
            picGallery.addView(add);
        }

    }

    public void onClickLike(View view){
        like.setImageResource(R.drawable.like_true);
    }

    public void onClickSubscription(View view){
        subscription.setImageResource(R.drawable.subscription_true);
    }
    public void onClickFullView(View v){
        Intent myIntent = new Intent(this, FullViewOfThePostActivity.class);
        myIntent.putExtra(AppContext.TODO_DOCUMENT,toDoDocuments);
        startActivityForResult(myIntent, AppContext.TODO_NOTE_REQUEST);

//        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        LinearLayout screen = (LinearLayout) findViewById(R.id.screenPreview);
        screen.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout screen = (LinearLayout) findViewById(R.id.screenPreview);
        screen.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppContext.TODO_NOTE_REQUEST) {
            switch (resultCode) {
                case AppContext.DELETE_POST_REQUEST:
                    getIntent().putExtra(AppContext.GET_POST_ID, toDoDocuments.getId());
                    getIntent().putExtra(AppContext.TODO_DOCUMENT,toDoDocuments);
                    setResult(AppContext.DELETE_POST_REQUEST, getIntent());
                    finish();
                    break;
                case AppContext.EDIT_POST_REQUEST:
                    getIntent().putExtra(AppContext.GET_POST_ID, toDoDocuments.getId());
                    getIntent().putExtra(AppContext.TODO_DOCUMENT,toDoDocuments);
                    setResult(AppContext.EDIT_POST_REQUEST, getIntent());
                    finish();
                    break;

            }
        }
    }
}
