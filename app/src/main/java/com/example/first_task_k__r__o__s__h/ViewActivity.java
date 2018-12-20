package com.example.first_task_k__r__o__s__h;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private String info;
    private TextView txtToDoDetails;
    private TextView textNote;
    private Gallery picGallery;
    private ImageView imageView;
    private VideoView videoView;
    private List<String> galleryInfo;
    private boolean imgKey;
    private PicAdapter imgAdapt;
    private int keyPosition=0;
    private String types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view);
//        info = getIntent().getExtras().getString("name", "");
//        String titlestr = getIntent().getExtras().getString("title", "");
//        txtToDoDetails = (TextView) findViewById(R.id.textToDoDetails);
//        textNote=(TextView) findViewById(R.id.note_text);
//        picGallery = (Gallery) findViewById(R.id.gallery_note);
//        imageView = (ImageView) findViewById(R.id.picture);
//        videoView = (VideoView) findViewById(R.id.videoView);
//
//        String cutchar1= "%#";
//        String cutchar2= "%##";
//        String types = info.substring(0,info.indexOf(cutchar1));
//        String vicinitystr = info.substring(info.indexOf(cutchar1)+2, info.indexOf(cutchar2));
//        String iconurl= info.substring(info.indexOf(cutchar2)+3);
//        txtToDoDetails.setText(titlestr);
//        textNote.setText(vicinitystr);
//
//        galleryInfo=ToDoDocuments.FromStringToList(iconurl);
//
//        if (galleryInfo.size()!=0) {
//            picGallery.setVisibility(View.VISIBLE);
//            imgAdapt = new PicAdapter(this, galleryInfo,types);
//            picGallery.setAdapter(imgAdapt);
//        }
//
//        picGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            //handle long clicks
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                if (keyPosition!=position) imgKey=false;
//                if (imgKey==false) {
//                    switch (PicAdapter.checkType(position)){
//                        case '0':
//                            videoView.setVisibility(View.GONE);
//
//                            imageView.setVisibility(View.VISIBLE);
//                            imageView.setImageBitmap(ToDoDocuments.ConvertBase64ToBitmap(galleryInfo.get(position)));
//                            break;
//                        case '1':
////                            imageView.setVisibility(View.GONE);
////
////                            videoView.setVisibility(View.VISIBLE);
////                            Uri selectedVideo = galleryInfo.get(position);
////                            videoView.setVideoURI(selectedVideo);
////                            videoView.setMediaController(new MediaController(ViewActivity.this));
////                            videoView.requestFocus(0);
////                            videoView.start(); // начинаем воспроизведение автоматически
//                            break;
//                    }
//
//                    imgKey=next(imgKey);
//                }
//                else {
//                    imageView.setVisibility(View.GONE);
//                    videoView.setVisibility(View.GONE);
//                    imgKey=next(imgKey);
//                }
//                keyPosition=position;
//            }
//            boolean next(boolean key){
//                return !key;
//            }
//        });

    }
}
