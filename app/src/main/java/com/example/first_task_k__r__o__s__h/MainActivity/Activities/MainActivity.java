package com.example.first_task_k__r__o__s__h.MainActivity.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.first_task_k__r__o__s__h.MainActivity.Fragments.MapFragment;
import com.example.first_task_k__r__o__s__h.MainActivity.Fragments.PhoneBookFragment;
import com.example.first_task_k__r__o__s__h.MainActivity.Fragments.SettingsFragment;
import com.example.first_task_k__r__o__s__h.MainActivity.Fragments.SupportFragment;
import com.example.first_task_k__r__o__s__h.MainActivity.Fragments.UserProfileFragment;
import com.example.first_task_k__r__o__s__h.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Fragment phoneBookFragment = new PhoneBookFragment();
        final Fragment settingsFragment = new SettingsFragment();
        final Fragment supportFragment = new SupportFragment();
        final Fragment userProfileFragment = new UserProfileFragment();
        final Fragment mapFragment = new MapFragment();
        final Fragment[] active = {mapFragment};

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_container, phoneBookFragment).hide(phoneBookFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, settingsFragment).hide(settingsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, supportFragment).hide(supportFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, userProfileFragment).hide(userProfileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, mapFragment).commit();


        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation_);
        navigationView.setSelectedItemId(R.id.navigation_map);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_settings:
                        fragmentManager.beginTransaction().hide(active[0]).show(settingsFragment).commit();
                        active[0] = settingsFragment;
                        return true;

                    case R.id.navigation_map:
                        fragmentManager.beginTransaction().hide(active[0]).show(mapFragment).commit();
                        active[0] = mapFragment;
                        return true;

                    case R.id.navigation_phone_book:
                        fragmentManager.beginTransaction().hide(active[0]).show(phoneBookFragment).commit();
                        active[0] = phoneBookFragment;
                        return true;

                    case R.id.navigation_support:
                        fragmentManager.beginTransaction().hide(active[0]).show(supportFragment).commit();
                        active[0] = supportFragment;
                        return true;

                    case R.id.navigation_user:
                        fragmentManager.beginTransaction().hide(active[0]).show(userProfileFragment).commit();
                        active[0] = userProfileFragment;
                        return true;
                }
                return false;
            }
        });
    }
}
