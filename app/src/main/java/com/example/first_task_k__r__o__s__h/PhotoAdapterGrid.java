package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import java.util.List;

public class PhotoAdapterGrid extends BaseAdapter {
    private Context context;
    private List<String> photo;
    ImageView imageView;

    public PhotoAdapterGrid(Context context, List<String> photo){
        this.context=context;
        this.photo = photo;
    }

    @Override
    public int getCount() {
        return photo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setBackgroundResource(R.drawable.customshare_header);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        } else{
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load(photo.get(position)).resize(300, 300).centerCrop().into(imageView);
        return imageView;
//        View grid = new View(context);
 //       View grid = LayoutInflater.from(parent.getContext()).inflate(R.layout.mygrid_layout, parent);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//
//         grid = inflater.inflate(R.layout.mygrid_layout, parent, false);
//        ImageView imageView1 = (ImageView) grid.findViewById(R.id.img_photo);
//        imageView1.setImageDrawable(imageView.getDrawable());
//            final CustomView customView1 = new CustomView(context);
//            final CustomView customView2 = new CustomView(context);
//
//            final LinearLayout container = (LinearLayout) grid.findViewById(R.id.container);
//            container.addView(customView1);
//            container.addView(customView2);

//        return (View) grid;
    }

}
