package com.example.first_task_k__r__o__s__h.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.first_task_k__r__o__s__h.R;

import java.util.List;

public class VideoAdapterViewPager extends PagerAdapter {
    private Context context;
    private List<String> GalVideos;
    private VideoView videoView;
    private LayoutInflater mLayoutInflater;

    public VideoAdapterViewPager(Context context, List<String> GalVideos){
        this.context=context;
        this.GalVideos=GalVideos;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return GalVideos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item_video, container, false);

        videoView = (VideoView) itemView.findViewById(R.id.videoViewFullScreen);
        videoView.setVideoURI(Uri.parse(GalVideos.get(position)));
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();

        videoView.seekTo(25);
        container.addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        videoView.pause();
        container.removeView((LinearLayout)object);
    }


}