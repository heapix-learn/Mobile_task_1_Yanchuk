package com.example.first_task_k__r__o__s__h.FullScreenMediaDisplay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.first_task_k__r__o__s__h.Adapters.GalleryAdapterViewPager;
import com.example.first_task_k__r__o__s__h.CustomViewPager;
import com.example.first_task_k__r__o__s__h.R;

import java.util.Arrays;
import java.util.List;

public class FullScreenGallery extends AppCompatActivity {
    private int position;
    private List<String> videos;
    private List<String> photos;

    private static final String PHOTOS_URL="photoSURL";
    private static final String VIDEO_URL="videoURL";
    private static final String POSITION="position";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_gallery);


        position = getIntent().getExtras().getInt(POSITION);


        photos = Arrays.asList(getIntent().getExtras().getStringArray(PHOTOS_URL));
        videos = Arrays.asList(getIntent().getExtras().getStringArray(VIDEO_URL));

        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.view_pager_gallery);
        GalleryAdapterViewPager adapter = new GalleryAdapterViewPager(this, videos, photos);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(position);

    }
}
