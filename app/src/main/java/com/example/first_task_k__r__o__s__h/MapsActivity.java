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

        // Add a marker in Sydney and move the camera

        File prefer = new File(getApplicationInfo().dataDir, "shared_prefs");
        if (prefer.exists() && prefer.isDirectory()){
            String[] list = prefer.list();
            Long d2000=Long.parseLong("946684800000");
            for (String aList : list) {

                SharedPreferences sharedPref = getSharedPreferences(aList.replace(".xml", ""), Context.MODE_PRIVATE);

       /*         SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(aList);
                editor.apply();*/
                ToDoDocuments toDoDocuments = new ToDoDocuments();
                toDoDocuments.setTitle(sharedPref.getString(AppContext.FIELD_TITLE, null));
                toDoDocuments.setNumber(sharedPref.getInt(AppContext.FIELD_NUMBER, 0));
                toDoDocuments.setCreateDate(new Date(sharedPref.getLong(AppContext.FIELD_CREATE_DATE, 0)));
                if (toDoDocuments.getCreateDate().getTime()>d2000) {
                    toDoDocuments.setLogin(sharedPref.getString(AppContext.FIELD_LOGIN, null));
                    toDoDocuments.setContext(sharedPref.getString(AppContext.FIELD_CONTEXT, null));
                    toDoDocuments.setTextNote(sharedPref.getString(AppContext.FIELD_TEXT_NOTE, null));
                    toDoDocuments.setImagePath(Uri.parse(sharedPref.getString(AppContext.FIELD_IMAGE_PATH, null)));

                    toDoDocuments.setLocationLatitude(sharedPref.getFloat(AppContext.FIELD_LOCATION_LATITUDE, 0));
                    toDoDocuments.setLocationLongitude(sharedPref.getFloat(AppContext.FIELD_LOCATION_LONGITUDE, 0));

                    LatLng position = new LatLng(toDoDocuments.getLocationLatitude(),toDoDocuments.getLocationLongitude());

                    mMap.addMarker(new MarkerOptions().position(position).title(toDoDocuments.getContext()));
                   // mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                }


            }
        }




     /*   LatLng position = new LatLng(10,20);

        mMap.addMarker(new MarkerOptions().position(position).title("Title"));
*/

    }
}
