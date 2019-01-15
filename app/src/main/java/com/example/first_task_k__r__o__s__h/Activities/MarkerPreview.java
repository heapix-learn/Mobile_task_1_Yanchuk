package com.example.first_task_k__r__o__s__h.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.Adapters.PicAdapter;
import com.example.first_task_k__r__o__s__h.FullScreenMediaDisplay.FullScreenGallery;
import com.example.first_task_k__r__o__s__h.OwnMarker;
import com.example.first_task_k__r__o__s__h.R;
import com.example.first_task_k__r__o__s__h.ToDoDocuments;
import com.example.first_task_k__r__o__s__h.WorkWithServer.DBPosts;

public class MarkerPreview extends AppCompatActivity {
    private ImageButton like;
    private ImageButton subscription;
    private ToDoDocuments toDoDocuments;
    private OwnMarker ownMarker;
    private static final String TODO_DOCUMENT="ToDoDocument";
    private static final int TODO_NOTE_REQUEST=1;
    public static final String PHOTOS_URL="photoSURL";
    public static final String VIDEO_URL="videoURL";
    public static final String POSITION="position";
    public static final int DELETE_POST_REQUEST=2;
    public static final int EDIT_POST_REQUEST=4;
    public static final String OWN_MARKER="OwnMarker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_preview);
        like = (ImageButton) findViewById(R.id.like);
        subscription = (ImageButton) findViewById(R.id.subscription);
        TextView noteTitle = (TextView) findViewById(R.id.titleMarkerPreview);
        LinearLayout picGallery = (LinearLayout) findViewById(R.id.gallery_preview);
        LinearLayout notContainerPreview = (LinearLayout) findViewById(R.id.screenPreview);
        LinearLayout containerPreview = (LinearLayout) findViewById(R.id.containerPreview);

        notContainerPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        containerPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        String id = getIntent().getExtras().getString("markerId", "");
        toDoDocuments = DBPosts.getOnePostFromId(id);
        ownMarker = DBPosts.getOneMarkerFromPostId(id);
        noteTitle.setText(toDoDocuments.getTitle());
        PicAdapter imgAdapt = new PicAdapter(this, toDoDocuments.getImagePath(), toDoDocuments.getVideoPath(), toDoDocuments.getVideoScreen());
        picGallery.setDividerPadding(5);
        for (int i = 0; i< imgAdapt.getCount(); i++) {
            View add = imgAdapt.getView(i, null, null);
            add.setId(i);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(MarkerPreview.this, FullScreenGallery.class);
                    myIntent.putExtra(VIDEO_URL, (String[]) toDoDocuments.getVideoPath().toArray());
                    myIntent.putExtra(PHOTOS_URL, (String[]) toDoDocuments.getImagePath().toArray());
                    myIntent.putExtra(POSITION, v.getId());
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
        myIntent.putExtra(TODO_DOCUMENT,toDoDocuments);
        startActivityForResult(myIntent, TODO_NOTE_REQUEST);
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
        if (requestCode == TODO_NOTE_REQUEST) {
            switch (resultCode) {
                case DELETE_POST_REQUEST:
                    getIntent().putExtra(TODO_DOCUMENT,toDoDocuments);
                    getIntent().putExtra(OWN_MARKER,ownMarker);
                    setResult(DELETE_POST_REQUEST, getIntent());
                    finish();
                    break;
                case EDIT_POST_REQUEST:
                    getIntent().putExtra(TODO_DOCUMENT,toDoDocuments);
                    getIntent().putExtra(OWN_MARKER,ownMarker);
                    setResult(EDIT_POST_REQUEST, getIntent());
                    finish();
                    break;

            }
        }
    }
}
