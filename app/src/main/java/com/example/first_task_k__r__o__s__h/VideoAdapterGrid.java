package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class VideoAdapterGrid extends BaseAdapter {
    private Context context;
    private List<String> video;
    VideoView videoView;

    public VideoAdapterGrid(Context context, List<String> video){
        this.context=context;
        this.video = video;
    }

    @Override
    public int getCount() {
        return video.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            videoView = new VideoView(context);
            videoView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            videoView.setPadding(8,8,8,8);
        } else{
            videoView = (VideoView) convertView;
        }
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.setVideoURI(Uri.parse(video.get(position)));
        return videoView;
    }

}
