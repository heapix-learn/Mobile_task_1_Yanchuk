package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        } else{
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load(photo.get(position)).resize(300, 300).centerCrop().into(imageView);
        return imageView;
    }

}
