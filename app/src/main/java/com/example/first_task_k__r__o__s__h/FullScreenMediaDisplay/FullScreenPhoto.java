package com.example.first_task_k__r__o__s__h.FullScreenMediaDisplay;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.first_task_k__r__o__s__h.Adapters.ImageAdapter;
import com.example.first_task_k__r__o__s__h.R;

import java.util.Arrays;
import java.util.List;

public class FullScreenPhoto extends AppCompatActivity {
    private int position;
    private List<String> Photos;
    private static final String PHOTOS_URL="photoSURL";
    private static final String POSITION="position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        Photos = Arrays.asList(getIntent().getExtras().getStringArray(PHOTOS_URL));
        position = getIntent().getExtras().getInt(POSITION);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this, Photos);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
