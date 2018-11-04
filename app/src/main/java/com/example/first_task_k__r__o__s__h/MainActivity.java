package com.example.first_task_k__r__o__s__h;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void Exit(View view){
         Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
         MainActivity.this.startActivity(myIntent);
    }

    public void addNote(View view){
        Intent myIntent = new Intent(MainActivity.this, Note.class);
        MainActivity.this.startActivity(myIntent);
    }


}
