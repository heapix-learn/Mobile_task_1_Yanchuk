package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> myPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<ToDoDocuments> listDocuments= new ArrayList<ToDoDocuments>();
        // Add a marker in Sydney and move the camera

        DBNotes database = new DBNotes(this);
        listDocuments = database.getNotesAllPublic(LoginActivity.myUser.username);
        listDocuments.addAll(database.getNotesMy_Access1(LoginActivity.myUser.username));
        database.close();

        for (int i=0; i<listDocuments.size(); i++) {
            ToDoDocuments toDoDocuments = listDocuments.get(i);
            LatLng position = new LatLng(toDoDocuments.getLocationLatitude(), toDoDocuments.getLocationLongitude());
            mMap.addMarker(new MarkerOptions().position(position).title(toDoDocuments.getTitle()));
        //    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }



     /*   LatLng position = new LatLng(10,20);

        mMap.addMarker(new MarkerOptions().position(position).title("Title"));
*/
    }
}
