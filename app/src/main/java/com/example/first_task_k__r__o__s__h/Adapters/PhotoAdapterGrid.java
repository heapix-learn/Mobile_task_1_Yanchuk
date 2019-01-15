package com.example.first_task_k__r__o__s__h.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.FullScreenMediaDisplay.FullScreenPhoto;
import com.example.first_task_k__r__o__s__h.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapterGrid extends BaseAdapter {
    private Context context;
    private List<String> photo;
    private TextView textView;
    private GridView gridViewForPhoto;
    private boolean edit;
    private Bundle bundle;
    private View lineForAddMedia;
    private VideoAdapterGrid videoAdapterGrid;
    private static final String PHOTOS_URL="photoSURL";
    private static final String POSITION="position";



    public PhotoAdapterGrid(Context context, Bundle bundle, List<String> photo, TextView textView, View lineForAddMedia, VideoAdapterGrid videoAdapterGrid, GridView gridViewForPhoto, boolean edit){
        this.context=context;
        this.photo = photo;
        this.textView = textView;
        this.gridViewForPhoto=gridViewForPhoto;
        this.edit=edit;
        this.bundle=bundle;
        this.videoAdapterGrid=videoAdapterGrid;
        this.lineForAddMedia=lineForAddMedia;
    }

    @Override
    public int getCount() {
        return photo.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.mygrid_layout, parent, false);
        } else grid = convertView;
        int t=0;
        if (getCount()%3==0) t=1;
        if (gridViewForPhoto.getWidth()!=0) {
            gridViewForPhoto.setLayoutParams(new LinearLayout.LayoutParams(gridViewForPhoto.getWidth(), ((getCount() / 3) + 1-t) * 310));
        }
        ImageView imageView1 = (ImageView) grid.findViewById(R.id.img_photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView1.setClipToOutline(true);
        }
        Picasso.with(context).load(photo.get(position)).resize(300, 300).centerCrop().into(imageView1);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, FullScreenPhoto.class);
                myIntent.putExtra(PHOTOS_URL, (String[]) photo.toArray());
                myIntent.putExtra(POSITION, position);
                ActivityCompat.startActivity(context, myIntent, bundle);
            }
        });


        ImageButton imageButton = (ImageButton) grid.findViewById(R.id.btn_close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);

                textView.setText(getCount()+ " Photos");
            }
        });
        if (!edit) imageButton.setVisibility(View.GONE);
        return grid;
    }

    public void removeItem(int position){
        photo.remove(position);
        notifyDataSetChanged();
        int t=0;
        if (getCount()%3==0) t=1;
        gridViewForPhoto.setLayoutParams(new LinearLayout.LayoutParams(gridViewForPhoto.getWidth(),((getCount()/3)+1-t)*310));
        if (getCount()!=0) {
            textView.setVisibility(View.VISIBLE);
            if (edit){
                lineForAddMedia.setVisibility(View.VISIBLE);
            }
        }
        else {
            textView.setVisibility(View.GONE);
            if (videoAdapterGrid==null){
                lineForAddMedia.setVisibility(View.GONE);
            } else if (videoAdapterGrid.getCount()==0){
                lineForAddMedia.setVisibility(View.GONE);
            }
        }
    }

}
