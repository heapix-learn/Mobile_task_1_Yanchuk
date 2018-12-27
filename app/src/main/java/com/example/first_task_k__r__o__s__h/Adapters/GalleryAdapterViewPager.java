package com.example.first_task_k__r__o__s__h.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.first_task_k__r__o__s__h.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapterViewPager extends PagerAdapter {
    private Context context;
    private List<String> GalVideos;
    private List<String> GalPhotos;
    private LayoutInflater mLayoutInflater;

    public GalleryAdapterViewPager(Context context, List<String> GalVideos, List<String> GalPhotos){
        this.context=context;
        this.GalVideos=GalVideos;
        this.GalPhotos=GalPhotos;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return GalVideos.size()+GalPhotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position<GalVideos.size()) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item_video, container, false);

            VideoView videoView = (VideoView) itemView.findViewById(R.id.videoViewFullScreen);
            videoView.setVideoURI(Uri.parse(GalVideos.get(position)));
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.requestFocus();

            videoView.seekTo(25);
            container.addView(itemView);
            return itemView;
        }
        else {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.photoView);
            Picasso.with(context).load(GalPhotos.get(position-GalVideos.size())).into(imageView);
            container.addView(itemView);
            return itemView;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }


}