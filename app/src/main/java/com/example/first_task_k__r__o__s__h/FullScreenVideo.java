package com.example.first_task_k__r__o__s__h;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class FullScreenVideo extends AppCompatActivity {
    private String videoURL;
    private VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        videoURL = getIntent().getExtras().getString(AppContext.VIDEO_URL);
        videoView = (VideoView) findViewById(R.id.videoViewFullScreen);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.setVideoURI(Uri.parse(videoURL));
        videoView.start();
    }
}
