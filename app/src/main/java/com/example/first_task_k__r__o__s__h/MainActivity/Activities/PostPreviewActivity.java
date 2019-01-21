package com.example.first_task_k__r__o__s__h.MainActivity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.Adapters.PicAdapter;
import com.example.first_task_k__r__o__s__h.FullScreenMediaDisplay.FullScreenGallery;
import com.example.first_task_k__r__o__s__h.MainActivity.DB.Store;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.PostManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.PostManager;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;
import com.example.first_task_k__r__o__s__h.Post;
import com.example.first_task_k__r__o__s__h.R;
import com.google.gson.Gson;

public class PostPreviewActivity extends AppCompatActivity {
    private ImageButton like;
    private ImageButton subscription;
    private TextView noteTitle;
    private  LinearLayout picGallery;
    private LinearLayout notContainerPreview;
    private LinearLayout containerPreview;

    private Post post;
    private MapItem mapItem;

    public static final String PHOTOS_URL="photoSURL";
    public static final String VIDEO_URL="videoURL";
    public static final String POSITION="position";
    public static final String MAP_ITEM ="mapItem";
    private PostManagerInterface postManager = new PostManager();
    private int FULL_VIEW_POST = 25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_preview);
        like = (ImageButton) findViewById(R.id.like);
        subscription = (ImageButton) findViewById(R.id.subscription);
        noteTitle = (TextView) findViewById(R.id.titleMarkerPreview);
        picGallery = (LinearLayout) findViewById(R.id.gallery_preview);
        notContainerPreview = (LinearLayout) findViewById(R.id.screenPreview);
        containerPreview = (LinearLayout) findViewById(R.id.containerPreview);

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


        RunnableWithObject<Post> onSuccess = new RunnableWithObject<Post>() {
            @Override
            public void run() {
                post = this.getDescription();
                setPreviewDescription(post);
                Store store = new Store();
                store.setMapItem(mapItem);
                store.setPost(post);
            }
        };
        RunnableWithObject<PackageErrors> onFailure = new RunnableWithObject<PackageErrors>();
        Gson gson = new Gson();
        String jsonMapItem = getIntent().getExtras().getString(MAP_ITEM);
        mapItem = gson.fromJson(jsonMapItem, MapItem.class);
        post = postManager.getPostWithStore(mapItem);
        if (post == null) {
            postManager.getPostWithNetWork(mapItem, onSuccess, onFailure);
        } else {
            setPreviewDescription(post);
        }


    }

    private void setPreviewDescription(final Post post){
        noteTitle.setText(post.getTitle());
        PicAdapter imgAdapt = new PicAdapter(PostPreviewActivity.this, post.getImagePath(), post.getVideoPath(), post.getVideoScreen());
        picGallery.setDividerPadding(5);
        for (int i = 0; i< imgAdapt.getCount(); i++) {
            View add = imgAdapt.getView(i, null, null);
            add.setId(i);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(PostPreviewActivity.this, FullScreenGallery.class);
                    myIntent.putExtra(VIDEO_URL, (String[]) post.getVideoPath().toArray());
                    myIntent.putExtra(PHOTOS_URL, (String[]) post.getImagePath().toArray());
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
        setResult(FULL_VIEW_POST);
    }

}
