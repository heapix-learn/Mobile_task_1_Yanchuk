package com.example.first_task_k__r__o__s__h;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;


public class Note extends AppCompatActivity {
    private EditText txtToDoDetails;
    private EditText textNote;
    private ToDoDocuments todoDocuments;
    private ImageView imageView;
    private Gallery picGallery;
    private VideoView videoView;
    public static final int RESULT_SAVE=100;
    public static final int RESULT_DELETE=101;
    static final int GALLERY_REQUEST = 1;
    private static final int NAME_LENGTH=20;
    private boolean update=false;
    private PicAdapter imgAdapt;
    private boolean imgKey = false;
    private int keyPosition=0;
    public boolean finish=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        txtToDoDetails = (EditText) findViewById(R.id.textToDoDetails);
        todoDocuments = getIntent().getParcelableExtra("ToDoDocuments");
        textNote=(EditText) findViewById(R.id.note_text);
        picGallery = (Gallery) findViewById(R.id.gallery_note);
        imageView = (ImageView) findViewById(R.id.picture);
        videoView = (VideoView) findViewById(R.id.videoView);

        //create a new adapter
        if (todoDocuments.getImagePath().size()!=0) {
            picGallery.setVisibility(View.VISIBLE);
            imgAdapt = new PicAdapter(this, todoDocuments.getImagePath(), todoDocuments.getTypeOfResource());
            picGallery.setAdapter(imgAdapt);
        }

        picGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //handle long clicks
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (keyPosition!=position) imgKey=false;
                if (imgKey==false) {
                    switch (PicAdapter.checkType(position)){
                        case '0':
                  //          videoView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(ToDoDocuments.ConvertBase64ToBitmap(todoDocuments.getImagePath().get(position)));
                            break;
                        case '1':
//                            imageView.setVisibility(View.GONE);
//                            videoView.setVisibility(View.VISIBLE);
//                            Uri selectedVideo = todoDocuments.getImagePath().get(position);
//                            videoView.setVideoURI(selectedVideo);
//                            videoView.setMediaController(new MediaController(Note.this));
//                            videoView.requestFocus(0);
//                            videoView.start(); // начинаем воспроизведение автоматически
                            break;
                    }

                    imgKey=next(imgKey);
                }
                else {
                    imageView.setVisibility(View.GONE);
                    videoView.setVisibility(View.GONE);
                    imgKey=next(imgKey);
                }
                keyPosition=position;
            }
            boolean next(boolean key){
                return !key;
            }
        });


        if (todoDocuments.getLocationLatitude()<-999 && todoDocuments.getLocationLongitude()<-999) {
            MyLocationListener.SetUpLocationListener(this);
            todoDocuments.setLocation("" + MyLocationListener.imHere.getLatitude()+"/"+MyLocationListener.imHere.getLongitude());
        }
        txtToDoDetails.setText(todoDocuments.getTitle());
        textNote.setText(todoDocuments.getTextNote());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.todo_details, menu);

        if (todoDocuments.getAccess()==0) {
            MenuItem item = menu.findItem(R.id.publicc);
            item.setChecked(true);
        }
        else {
            MenuItem item = menu.findItem(R.id.privatee);
            item.setChecked(true);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.privatee: {
                item.setChecked(true);
                todoDocuments.setAccess(1);
                update=true;
                return true;
            }
            case R.id.publicc: {
                item.setChecked(true);
                todoDocuments.setAccess(0);
                update=true;
                return true;
            }
            case R.id.back: {
                if (update == false && txtToDoDetails.getText().toString().equals(todoDocuments.getTitle()) && textNote.getText().toString().equals(todoDocuments.getTextNote())){
                    finish=true;
                    finish();
                } else {
                    saveDocument();
                    finish();
                }
                return true;
            }
            case R.id.save: {
                saveDocument();
                finish();
                return true;
            }
            case R.id.delete: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirm_delete);

                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(RESULT_DELETE, getIntent());
                                finish();
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                    });
                AlertDialog dialog = builder.create();
                dialog.show();
                finish=true;
                return true;
            }

            case R.id.gallery:{
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("video/* image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                update=true;
                return true;
            }

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveDocument(){
        StringBuilder sb = new StringBuilder(txtToDoDetails.getText());
        StringBuilder ss= new StringBuilder(textNote.getText());

        if (update == false && txtToDoDetails.getText().toString().equals(todoDocuments.getTitle()) && textNote.getText().toString().equals(todoDocuments.getTextNote())){
            finish=true;
            setResult(RESULT_CANCELED, getIntent());
        } else {
            todoDocuments.setTitle(sb.toString());
            todoDocuments.setTextNote(ss.toString());
            todoDocuments.setCreateDate(new Date());
            finish=true;
            setResult(RESULT_SAVE, getIntent());
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (finish==false){
//            StringBuilder sb = new StringBuilder(txtToDoDetails.getText());
//            StringBuilder ss= new StringBuilder(textNote.getText());
//
//            if (update == false && txtToDoDetails.getText().toString().equals(todoDocuments.getTitle()) && textNote.getText().toString().equals(todoDocuments.getTextNote())){
//                finish=true;
//                setResult(RESULT_CANCELED, getIntent());
//            } else {
//                todoDocuments.setTitle(sb.toString());
//                todoDocuments.setTextNote(ss.toString());
//                todoDocuments.setCreateDate(new Date());
//                finish=true;
//                setResult(RESULT_SAVE, getIntent());
//
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    if (checkType(selectedImage.toString())==0){
                        todoDocuments.setTypeOfResource('0');
                    }

                    Bitmap bitmap=null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    bitmap= Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/20,
                            bitmap.getHeight()/20, false);
                    if (bitmap!=null) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        todoDocuments.setImagePath(encoded);
                        if (byteArrayOutputStream!=null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    imgAdapt = new PicAdapter(this, todoDocuments.getImagePath(), todoDocuments.getTypeOfResource());
                            picGallery.setAdapter(imgAdapt);
                            picGallery.setVisibility(View.VISIBLE);


                }
        }
    }
    public int checkType(String str){
        int index = str.lastIndexOf("/images/");
        if (index!=-1) return 0;
        else return 1;
    }


}
