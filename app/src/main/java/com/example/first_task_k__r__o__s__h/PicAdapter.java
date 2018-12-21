package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PicAdapter extends BaseAdapter {
    //use the default gallery background image
    int defaultItemBackground;

    //gallery context
    private Context galleryContext;

    //array to store bitmaps to display
    private List<String> image;
    private List<String> video;
    private List<String> videoScreen;



    public PicAdapter(Context c, List<String> imageInfo, List<String> videoInfo, List<String> videoScreenInfo) {

        //instantiate context
        galleryContext = c;

        //create url array
        image=imageInfo;
        video=videoInfo;
        videoScreen=videoScreenInfo;
        //get the styling attributes - use default Andorid system resources
//        TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
//
//        //get the background resource
//        defaultItemBackground = styleAttrs.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 0);
//
//        //recycle attributes
//        styleAttrs.recycle();
    }

    //return number of data items i.e. bitmap images
    @Override
    public int getCount() {
        return image.size()+video.size();
    }

    //return item at specified position
    @Override
    public Object getItem(int position) {
        return position;
    }

    //return item ID at specified position
    @Override
    public long getItemId(int position) {
        return position;
    }

    //get view specifies layout and display options for each thumbnail in the gallery
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (position>=video.size()) {
            View grid;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) galleryContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grid = inflater.inflate(R.layout.mygrid_layout, parent, false);
            } else grid = convertView;

            ImageView imageView1 = (ImageView) grid.findViewById(R.id.img_photo);
            imageView1.setScaleType(RoundRectCornerImageView.ScaleType.CENTER_CROP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView1.setClipToOutline(true);
            }
            Picasso.with(galleryContext).load(image.get(position-video.size())).resize(300, 300).centerCrop().into(imageView1);

            ImageButton imageButton = (ImageButton) grid.findViewById(R.id.btn_close);
            imageButton.setVisibility(View.GONE);
            grid.setPadding(0,0,10,0);

            return grid;
        }else {
            View grid;
            TextView videoTime;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) galleryContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                grid = inflater.inflate(R.layout.mygrid_layout, parent, false);
            } else grid = convertView;

            ImageView imageView1 = (ImageView) grid.findViewById(R.id.img_photo);
            imageView1.setScaleType(RoundRectCornerImageView.ScaleType.CENTER_CROP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView1.setClipToOutline(true);
            }

            Picasso.with(galleryContext).load(videoScreen.get(position)).resize(300, 300).centerCrop().into(imageView1);

            ImageButton imageButton = (ImageButton) grid.findViewById(R.id.btn_close);
            videoTime = (TextView) grid.findViewById(R.id.textTime);
            imageButton.setVisibility(View.GONE);
            videoTime.setVisibility(View.VISIBLE);
            grid.setPadding(0,0,10,0);
            return grid;
        }

    }


}
