package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class FullScreenVideo extends AppCompatActivity {
    private String videoURL;
    private int position;
    private List<String> videos;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        videoURL = getIntent().getExtras().getString(AppContext.VIDEO_URL);
        position = getIntent().getExtras().getInt(AppContext.POSITION);
        videos = AppContext.FromStringToList(videoURL);

        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.view_pager_video);
        VideoAdapterViewPager adapter = new VideoAdapterViewPager(this, videos);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);

        viewPager.setCurrentItem(position);


    }
}
