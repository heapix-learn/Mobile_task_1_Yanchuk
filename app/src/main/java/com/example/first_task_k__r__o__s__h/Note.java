package com.example.first_task_k__r__o__s__h;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


public class Note extends AppCompatActivity {
    private EditText textTitle;
    private EditText textDescription;
    private EditText textLocation;
    private TextView textNumberOfVideo;
    private TextView textNumberOfPhoto;
    private ToDoDocuments todoDocuments;

    private ImageButton lock;
    private String DEFAULT_PHOTO_URL="http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png";
    private String DEFAULT_VIDEO_URL="http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";

    private Gallery picGallery;
    private VideoView videoView;
    public static final int RESULT_SAVE=100;
    public static final int RESULT_DELETE=101;
    static final int GALLERY_REQUEST = 1;
    private GridView gridViewForPhoto;
    private GridView gridViewForVideo;

    private static final int NAME_LENGTH=20;
    private boolean update=false;
    private PicAdapter imgAdapt;
    private boolean imgKey = false;
    private int keyPosition=0;
    public boolean finish=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_note);
        todoDocuments = getIntent().getParcelableExtra("ToDoDocuments");

        textTitle = (EditText) findViewById(R.id.textTitle);
        textDescription=(EditText) findViewById(R.id.textDescription);
        textLocation=(EditText) findViewById(R.id.textLocation);
        lock = (ImageButton) findViewById(R.id.lock);
        gridViewForPhoto = (GridView) findViewById(R.id.gridViewForPhoto);
        gridViewForVideo = (GridView) findViewById(R.id.gridViewForVideo);
        textNumberOfPhoto = (TextView) findViewById(R.id.number_of_photo);
        textNumberOfVideo = (TextView) findViewById(R.id.number_of_video);

        if (todoDocuments.getLocationLatitude()<-999 && todoDocuments.getLocationLongitude()<-999) {
            MyLocationListener.SetUpLocationListener(this);
//            todoDocuments.setLocation("" + MyLocationListener.imHere.getLatitude()+"/"+MyLocationListener.imHere.getLongitude());
        }

        textTitle.setText(todoDocuments.getTitle());
        textDescription.setText(todoDocuments.getTextNote());
//        textLocation.setText(todoDocuments.getLocation());

        if (todoDocuments.getImagePath().size()!=0){
                textNumberOfPhoto.setVisibility(View.VISIBLE);
                textNumberOfPhoto.setText(todoDocuments.getImagePath().size() + " Photos");
                todoDocuments.setImagePath(DEFAULT_PHOTO_URL);
                gridViewForPhoto.setVisibility(View.VISIBLE);
                gridViewForPhoto.setAdapter(new PhotoAdapterGrid(this, todoDocuments.getImagePath()));
                gridViewForPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(Note.this, ""+position,Toast.LENGTH_SHORT).show();
                    }
                });
        }
        if (todoDocuments.getVideoPath().size()!=0){
            textNumberOfVideo.setVisibility(View.VISIBLE);
            textNumberOfVideo.setText(todoDocuments.getVideoPath().size() + " Videos");
            gridViewForVideo.setVisibility(View.VISIBLE);
            gridViewForVideo.setAdapter(new VideoAdapterGrid(this, todoDocuments.getVideoPath()));
            gridViewForVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    VideoView mView = (VideoView) view;
                    mView.start();

                }
            });
        }

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
                        textNumberOfPhoto.setText(todoDocuments.getImagePath().size() + " Photos");
                        gridViewForPhoto.setVisibility(View.VISIBLE);
                        gridViewForPhoto.setAdapter(new PhotoAdapterGrid(this, todoDocuments.getImagePath()));
                        gridViewForPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(Note.this, ""+position,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        todoDocuments.setVideoPath(DEFAULT_VIDEO_URL);
                        textNumberOfVideo.setVisibility(View.VISIBLE);
                        textNumberOfVideo.setText(todoDocuments.getVideoPath().size() + " Videos");
                        gridViewForVideo.setVisibility(View.VISIBLE);
                        gridViewForVideo.setAdapter(new VideoAdapterGrid(this, todoDocuments.getVideoPath()));
                        gridViewForVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                VideoView mView = (VideoView) view;
                                mView.start();

                            }
                        });
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
        finish=true;
        setResult(RESULT_SAVE, getIntent());
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//
//        switch (item.getItemId()) {
//            case R.id.back: {
//                if (update == false && txtToDoDetails.getText().toString().equals(todoDocuments.getTitle()) && textNote.getText().toString().equals(todoDocuments.getTextNote())){
//                    finish=true;
//                    finish();
//                } else {
//                    saveDocument();
//                    finish();
//                }
//                return true;
//            }
//            case R.id.delete: {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage(R.string.confirm_delete);
//
//                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                setResult(RESULT_DELETE, getIntent());
//                                finish();
//                            }
//                        });
//                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                    });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                finish=true;
//                return true;
//            }
//
//            case R.id.gallery:{
//                update=true;
//                return true;
//            }
//
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//

//
////    @Override
////    protected void onPause() {
////        super.onPause();
////        if (finish==false){
////            StringBuilder sb = new StringBuilder(txtToDoDetails.getText());
////            StringBuilder ss= new StringBuilder(textNote.getText());
////
////            if (update == false && txtToDoDetails.getText().toString().equals(todoDocuments.getTitle()) && textNote.getText().toString().equals(todoDocuments.getTextNote())){
////                finish=true;
////                setResult(RESULT_CANCELED, getIntent());
////            } else {
////                todoDocuments.setTitle(sb.toString());
////                todoDocuments.setTextNote(ss.toString());
////                todoDocuments.setCreateDate(new Date());
////                finish=true;
////                setResult(RESULT_SAVE, getIntent());
////
////            }
////        }
////    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch(requestCode) {
//            case GALLERY_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    if (checkType(selectedImage.toString())==0){
//                        todoDocuments.setTypeOfResource('0');
//                        Bitmap bitmap=null;
//                        try {
//                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        bitmap= Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/15,
//                                bitmap.getHeight()/15, false);
//                        if (bitmap!=null) {
//                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
//                            byte[] byteArray = byteArrayOutputStream.toByteArray();
//                            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//                            todoDocuments.setImagePath(encoded);
//                            if (byteArrayOutputStream!=null) {
//                                try {
//                                    byteArrayOutputStream.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                    else {
//                        todoDocuments.setTypeOfResource('1');
//                        Uri selectedVideoUri = imageReturnedIntent.getData();
//                        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
//                        Cursor cursor = managedQuery(selectedVideoUri, projection, null, null, null);
//
//                        cursor.moveToFirst();
//                        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
//                        Log.d("File Name:",filePath);
//
//                        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);
//                        // Setting the thumbnail of the video in to the image view
//                     //   msImage.setImageBitmap(thumb);
//                        InputStream inputStream = null;
//// Converting the video in to the bytes
//                        try
//                        {
//                            inputStream = getContentResolver().openInputStream(selectedVideoUri);
//                        }
//                        catch (FileNotFoundException e)
//                        {
//                            e.printStackTrace();
//                        }
//                        int bufferSize = 1024;
//                        byte[] buffer = new byte[bufferSize];
//                        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//                        int len = 0;
//                        try
//                        {
//                            while ((len = inputStream.read(buffer)) != -1)
//                            {
//                                byteBuffer.write(buffer, 0, len);
//                            }
//                        }
//                        catch (IOException e)
//                        {
//                            e.printStackTrace();
//                        }
//                        System.out.println("converted!");
//
//                        String videoData="";
//                        //Converting bytes into base64
//                        videoData = Base64.encodeToString(byteBuffer.toByteArray(), Base64.DEFAULT);
//                        Log.d("VideoData**>  " , videoData);
////
////                        String sinSaltoFinal2 = videoData.trim();
////                        String sinsinSalto2 = sinSaltoFinal2.replaceAll("\n", "");
////                        Log.d("VideoData**>  " , sinsinSalto2);
//
//                        todoDocuments.setImagePath(videoData);
//                    }
//
//
//
//                    imgAdapt = new PicAdapter(this, todoDocuments.getImagePath(), todoDocuments.getTypeOfResource());
//                            picGallery.setAdapter(imgAdapt);
//                            picGallery.setVisibility(View.VISIBLE);
//                }
//        }
//    }
//    public int checkType(String str){
//        int index = str.lastIndexOf("/images/");
//        if (index!=-1) return 0;
//        else return 1;
//    }
//
//    public File getFileDir(){
//        return this.getFilesDir();
//    }

}
