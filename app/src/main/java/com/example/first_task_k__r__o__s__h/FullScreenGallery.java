package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class FullScreenGallery extends AppCompatActivity {
    private String videoURL;
    private String photoURL;
    private int position;
    private List<String> videos;
    private List<String> photos;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_gallery);
        videoURL = getIntent().getExtras().getString(AppContext.VIDEO_URL);
        photoURL = getIntent().getExtras().getString(AppContext.PHOTOS_URL);
        position = getIntent().getExtras().getInt(AppContext.POSITION);
        videos = AppContext.FromStringToList(videoURL);
        photos = AppContext.FromStringToList(photoURL);
        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.view_pager_gallery);
        GalleryAdapterViewPager adapter = new GalleryAdapterViewPager(this, videos, photos);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(position);

    }
}
