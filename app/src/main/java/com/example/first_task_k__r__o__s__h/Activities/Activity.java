package com.example.first_task_k__r__o__s__h.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.first_task_k__r__o__s__h.R;

public class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);

        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation_map_activity);
        navigationView.setSelectedItemId(R.id.navigation_map);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_settings:
                        break;
                    case R.id.navigation_map:
                        break;
                    case R.id.navigation_phone_book:
                        break;
                    case R.id.navigation_support:
                        break;
                    case R.id.navigation_user:
                        break;
                }
                return false;
            }
        });
    }
}
