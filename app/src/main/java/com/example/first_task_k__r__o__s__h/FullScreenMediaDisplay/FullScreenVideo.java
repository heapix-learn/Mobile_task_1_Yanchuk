package com.example.first_task_k__r__o__s__h.FullScreenMediaDisplay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.first_task_k__r__o__s__h.Adapters.VideoAdapterViewPager;
import com.example.first_task_k__r__o__s__h.CustomViewPager;
import com.example.first_task_k__r__o__s__h.R;

import java.util.Arrays;
import java.util.List;

public class FullScreenVideo extends AppCompatActivity {
    private static final String VIDEO_URL="videoURL";
    private static final String POSITION="position";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);

        int position = getIntent().getExtras().getInt(POSITION);

        List<String> videos = Arrays.asList(getIntent().getExtras().getStringArray(VIDEO_URL));

        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.view_pager_video);
        VideoAdapterViewPager adapter = new VideoAdapterViewPager(this, videos);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);

        viewPager.setCurrentItem(position);


    }
}
