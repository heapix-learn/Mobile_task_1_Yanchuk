package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PicAdapter extends BaseAdapter {
    //use the default gallery background image
    int defaultItemBackground;

    //gallery context
    private Context galleryContext;

    //array to store bitmaps to display
    private List<String> image;
    private List<String> video;


    public PicAdapter(Context c, List<String> image_info) {

        //instantiate context
        galleryContext = c;

        //create url array
        image=image_info;

        //get the styling attributes - use default Andorid system resources
        TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);

        //get the background resource
        defaultItemBackground = styleAttrs.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 0);

        //recycle attributes
        styleAttrs.recycle();
    }

    //return number of data items i.e. bitmap images
    @Override
    public int getCount() {
        return image.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;

            //create the view
        RoundRectCornerImageView imageView = new RoundRectCornerImageView(galleryContext);
            //specify the bitmap at this position in the array

//            Picasso.with(galleryContext).load(image.get(position)).centerCrop().into(imageView);
            //set layout options
            imageView.setLayoutParams(new Gallery.LayoutParams(290, 290));
            //scale type within view area
            imageView.setScaleType(RoundRectCornerImageView.ScaleType.CENTER_CROP);
            //set default gallery item background
//
//            imageView.setBackgroundColor(Color.WHITE);
//            imageView.setBackgroundResource(R.drawable.customshare_marker_preview);
            //return the view


            Picasso.with(galleryContext).load(image.get(position)).resize(290, 290).centerCrop().into(imageView);
            imageView.setBackgroundResource(R.drawable.customshare_header);
//            imageView.setImageResource(R.drawable.screen);
//        imageView.setImageURI(Uri.parse(image.get(position)));
            ret = imageView;







//                //create the view
//                VideoView videoView = new VideoView(galleryContext);
//                //specify the bitmap at this position in the array
////                videoView.setVideoURI(imageURI.get(position));
//
//
//                //set layout options
//                videoView.setLayoutParams(new Gallery.LayoutParams(300, 200));
//                //scale type within view area
//            //    videoView.setScaleType(ViView.ScaleType.FIT_CENTER);
//                //set default gallery item background
//                videoView.setBackgroundResource(defaultItemBackground);
//                //return the view
//                ret = videoView;

        return ret;
    }


}
