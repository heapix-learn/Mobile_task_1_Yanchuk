package com.example.first_task_k__r__o__s__h;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FullScreenPhoto extends AppCompatActivity {
    private String photoURL;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        photoURL = getIntent().getExtras().getString(AppContext.PHOTO_URL);
        imageView = (ImageView) findViewById(R.id.photoView);
//        imageView.setImageURI(Uri.parse(photoURL));

        Picasso.with(this).load(photoURL).into(imageView);
    }
}
