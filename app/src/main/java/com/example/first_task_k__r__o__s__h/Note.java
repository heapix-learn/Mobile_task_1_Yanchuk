package com.example.first_task_k__r__o__s__h;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Note extends AppCompatActivity {
    private EditText textTitle;
    private EditText textDescription;
    private AutoCompleteTextView textLocation;
    private TextView textNumberOfVideo;
    private TextView textNumberOfPhoto;
    private ToDoDocuments todoDocuments;

    private ImageButton lock;
    private String DEFAULT_PHOTO_URL="http://i.imgur.com/DvpvklR.png";
    private String DEFAULT_VIDEO_URL="http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    private String DEFAULT_VIDEO_SCREEN_URL="http://i.imgur.com/DvpvklR.png";

    public static final int RESULT_SAVE=100;
    public static final int RESULT_DELETE=101;
    static final int GALLERY_REQUEST = 1;
    private GridView gridViewForPhoto;
    private GridView gridViewForVideo;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_note);
        todoDocuments = getIntent().getParcelableExtra(AppContext.TODO_DOCUMENT);
        bundle = savedInstanceState;
        textTitle = (EditText) findViewById(R.id.textTitle);
        textDescription=(EditText) findViewById(R.id.textDescription);
        textLocation=(AutoCompleteTextView) findViewById(R.id.textLocation);

        lock = (ImageButton) findViewById(R.id.lock);
        gridViewForPhoto = (GridView) findViewById(R.id.gridViewForPhoto);
        gridViewForVideo = (GridView) findViewById(R.id.gridViewForVideo);
        textNumberOfPhoto = (TextView) findViewById(R.id.number_of_photo);
        textNumberOfVideo = (TextView) findViewById(R.id.number_of_video);

        if (todoDocuments.getLocationLatitude()<-999 && todoDocuments.getLocationLongitude()<-999) {
            MyLocationListener.SetUpLocationListener(this);
            todoDocuments.setLocation("" + MyLocationListener.imHere.getLatitude()+"/"+MyLocationListener.imHere.getLongitude());
            Geocoder geocoder = new Geocoder(Note.this);
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocation(MyLocationListener.imHere.getLatitude(),MyLocationListener.imHere.getLongitude(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            todoDocuments.setNameLocation(list.get(0).getLocality());
            textLocation.setText(todoDocuments.getNameLocation());
        }

        textTitle.setText(todoDocuments.getTitle());
        textDescription.setText(todoDocuments.getTextNote());
        textLocation.setText(todoDocuments.getNameLocation());

        if (todoDocuments.getImagePath().size()!=0){
                textNumberOfPhoto.setVisibility(View.VISIBLE);
                textNumberOfPhoto.setText(todoDocuments.getImagePath().size() + " Photos");
                gridViewForPhoto.setVisibility(View.VISIBLE);
                gridViewForPhoto.setAdapter(new PhotoAdapterGrid(this, bundle,todoDocuments.getImagePath(), textNumberOfPhoto, gridViewForPhoto, true));
        }
        if (todoDocuments.getVideoPath().size()!=0){
            textNumberOfVideo.setVisibility(View.VISIBLE);
            textNumberOfVideo.setText(todoDocuments.getVideoPath().size() + " Videos");
            gridViewForVideo.setVisibility(View.VISIBLE);
            gridViewForVideo.setAdapter(new VideoAdapterGrid(this, bundle, todoDocuments.getVideoPath(), todoDocuments.getVideoScreen(), textNumberOfVideo, gridViewForVideo, true));
            gridViewForVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    VideoView mView = (VideoView) view;
                    mView.start();

                }
            });
        }


        CustomAutoCompleteAdapter adapter = new CustomAutoCompleteAdapter(this);
        textLocation.setAdapter(adapter);
        textLocation.setOnItemClickListener(onItemClickListener);

        AddGarbage();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Geocoder geocoder = new Geocoder(Note.this);
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocationName(textLocation.getText().toString(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            todoDocuments.setNameLocation(textLocation.getText().toString());
            todoDocuments.setLocation(list.get(0).getLatitude()+"/"+list.get(0).getLongitude());
        }
    };


    protected void AddGarbage(){
    }

    public void onClickLock(View view){
        if (todoDocuments.getAccess()==0) {
            todoDocuments.setAccess(1);
            lock.setImageResource(R.drawable.lock_close);
        } else {
            todoDocuments.setAccess(0);
            lock.setImageResource(R.drawable.lock_open);
        }
    }

    public void onClickAddMedia(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/* image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    if (checkType(selectedImage.toString())==0){
                        todoDocuments.setImagePath(DEFAULT_PHOTO_URL);
                        textNumberOfPhoto.setVisibility(View.VISIBLE);
                        gridViewForPhoto.setVisibility(View.VISIBLE);
                        gridViewForPhoto.setAdapter(new PhotoAdapterGrid(this, bundle,todoDocuments.getImagePath(), textNumberOfPhoto, gridViewForPhoto,true));
                        textNumberOfPhoto.setText(todoDocuments.getImagePath().size() + " Photos");
                    } else {
                        todoDocuments.setVideoPath(DEFAULT_VIDEO_URL);
                        todoDocuments.setVideoScreen(DEFAULT_VIDEO_SCREEN_URL);
                        textNumberOfVideo.setVisibility(View.VISIBLE);
                        textNumberOfVideo.setText(todoDocuments.getVideoPath().size() + " Videos");
                        gridViewForVideo.setVisibility(View.VISIBLE);
                        gridViewForVideo.setAdapter(new VideoAdapterGrid(this, bundle, todoDocuments.getVideoPath(), todoDocuments.getVideoScreen(), textNumberOfVideo, gridViewForVideo,true));
                    }
                }
        }
    }

    public int checkType(String str){
        int index = str.lastIndexOf("/images/");
        if (index!=-1) return 0;
        else return 1;
    }


    public void onClickAddNote(View view){
        saveDocument();
        finish();
    }

    private void saveDocument(){
        todoDocuments.setTitle(textTitle.getText().toString());
        todoDocuments.setTextNote(textDescription.getText().toString());
        todoDocuments.setCreateDate(new Date());
        setResult(RESULT_SAVE, getIntent());
    }

}
