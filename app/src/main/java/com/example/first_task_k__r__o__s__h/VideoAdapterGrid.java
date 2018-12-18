package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class VideoAdapterGrid extends BaseAdapter {
    private Context context;
    private List<String> video;
    private List<String> videoScreen;
    private TextView textView;
    private GridView gridViewForVideo;
    private boolean edit;
    private Bundle bundle;


    public VideoAdapterGrid(Context context, Bundle bundle, List<String> video, List<String> videoScreen, TextView textView, GridView gridViewForVideo, boolean edit){
        this.context=context;
        this.video = video;
        this.videoScreen=videoScreen;
        this.textView = textView;
        this.gridViewForVideo=gridViewForVideo;
        this.edit=edit;
        this.bundle=bundle;
    }

    @Override
    public int getCount() {
        return video.size();
    }

    @Override
    public Object getItem(int position) {
        return video.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        TextView videoTime;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.mygrid_layout, parent, false);
        } else grid = convertView;
        int t=0;
        if (getCount()%3==0) t=1;
        if (gridViewForVideo.getWidth()!=0) {
            gridViewForVideo.setLayoutParams(new LinearLayout.LayoutParams(gridViewForVideo.getWidth(), ((getCount() / 3) + 1) * 310));
        }
        videoTime = grid.findViewById(R.id.textTime);
        videoTime.setVisibility(View.VISIBLE);
        if (getCount()==0){
            gridViewForVideo.setVisibility(View.GONE);
        }
        ImageView imageView1 = (ImageView) grid.findViewById(R.id.img_photo);
        imageView1.setScaleType(RoundRectCornerImageView.ScaleType.CENTER_CROP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView1.setClipToOutline(true);
        }



//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(video.get(position),new HashMap<String, String>());
//        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
//        imageView1.setImageBitmap(bitmap);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, FullScreenVideo.class);
                myIntent.putExtra(AppContext.VIDEO_URL, AppContext.ListPathToString(video));
                myIntent.putExtra(AppContext.POSITION, position);
                ActivityCompat.startActivity(context, myIntent, bundle);
            }
        });

        Picasso.with(context).load(videoScreen.get(position)).resize(300, 300).centerCrop().into(imageView1);
        ImageButton imageButton = (ImageButton) grid.findViewById(R.id.btn_close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
                if (getCount()!=0) textView.setVisibility(View.VISIBLE);
                else textView.setVisibility(View.GONE);
                textView.setText(getCount()+ " Videos");
            }
        });
        if (!edit) imageButton.setVisibility(View.GONE);
        return grid;
    }

    public void removeItem(int position){
        video.remove(position);
        notifyDataSetChanged();
        int t=0;
        if (getCount()%3==0) t=1;
        gridViewForVideo.setLayoutParams(new LinearLayout.LayoutParams(gridViewForVideo.getWidth(),((getCount()/3)+1-t)*310));
        if (getCount()==0){
            gridViewForVideo.setVisibility(View.GONE);
        }

    }
}
