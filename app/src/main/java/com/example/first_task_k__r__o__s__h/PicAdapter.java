package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.VideoView;

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
    private static String types;

    public PicAdapter(Context c, List<String> image_info, String types) {

        this.types=types;
        //instantiate context
        galleryContext = c;

        //create bitmap array
        image=image_info;

        //get the styling attributes - use default Andorid system resources
        TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);

        //get the background resource
        defaultItemBackground = styleAttrs.getResourceId(R.styleable.PicGallery_android_galleryItemBackground, 0);

        //recycle attributes
        styleAttrs.recycle();
    }

    //return number of data items i.e. bitmap images
    public int getCount() {
        return image.size();
    }

    //return item at specified position
    public Object getItem(int position) {
        return position;
    }

    //return item ID at specified position
    public long getItemId(int position) {
        return position;
    }

    //get view specifies layout and display options for each thumbnail in the gallery
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;
        switch (checkType(position)) {
            case '0':
                //create the view
                ImageView imageView = new ImageView(galleryContext);
                //specify the bitmap at this position in the array
                imageView.setImageBitmap(ToDoDocuments.ConvertBase64ToBitmap(image.get(position)));
                //set layout options
                imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
                //scale type within view area
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //set default gallery item background
                imageView.setBackgroundResource(defaultItemBackground);
                //return the view
                ret = imageView;
                break;
            case '1':
                //create the view
                VideoView videoView = new VideoView(galleryContext);
                //specify the bitmap at this position in the array
//                videoView.setVideoURI(imageURI.get(position));
                File file = ToDoDocuments.ConvertBase64ToFile(galleryContext, image.get(position));

                videoView.setVideoPath(file.getPath());
                //set layout options
                videoView.setLayoutParams(new Gallery.LayoutParams(300, 200));
                //scale type within view area
            //    videoView.setScaleType(ViView.ScaleType.FIT_CENTER);
                //set default gallery item background
                videoView.setBackgroundResource(defaultItemBackground);
                //return the view
                ret = videoView;
                break;
        }
        return ret;
    }

    public static char checkType(int position){
        return types.charAt(position);
    }

}
