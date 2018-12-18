package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

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
        videos = ToDoDocuments.FromStringToList(videoURL);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_video);
        VideoAdapterViewPager adapter = new VideoAdapterViewPager(this, videos);
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                VideoView videoView = v.findViewById(R.id.videoViewFullScreen);
                videoView.pause();
                return false;
            }
        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView videoView = v.findViewById(R.id.videoViewFullScreen);
                videoView.pause();
            }
        });

        viewPager.setCurrentItem(position);

    }
}
