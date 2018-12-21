package com.example.first_task_k__r__o__s__h;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class FullScreenPhoto extends AppCompatActivity {
    private String photosURL;
    private int position;
    private List<String> Photos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        photosURL = getIntent().getExtras().getString(AppContext.PHOTOS_URL);
        position = getIntent().getExtras().getInt(AppContext.POSITION);
        Photos = AppContext.FromStringToList(photosURL);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this, Photos);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
