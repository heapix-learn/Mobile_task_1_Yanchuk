package com.example.first_task_k__r__o__s__h;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<LatLng> myPosition;

    private ClusterManager<ToDoDocuments> mClusterManager;

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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.info_window, null);

                // Getting the snippet from the marker
                String snippet = arg0.getSnippet();

                // Getting the snippet from the marker
                String titlestr = arg0.getTitle();

                String cutchar1= "%#";
                String cutchar2= "%##";
                String videostr = snippet.substring(0,snippet.indexOf(cutchar1));
                String vicinitystr = snippet.substring(snippet.indexOf(cutchar1)+2, snippet.indexOf(cutchar2));
                String iconurl= snippet.substring(snippet.indexOf(cutchar2)+3);

                // Getting reference to the TextView to set latitude
                TextView title = (TextView) v.findViewById(R.id.place_title);

                TextView vicinity = (TextView) v.findViewById(R.id.place_vicinity);

                ImageView image = (ImageView) v.findViewById(R.id.place_icon);

                // Setting the latitude
                title.setText(titlestr);

                vicinity.setText(vicinitystr);
                String img=getImage(iconurl);
                if (!img.equals("")) {
                    Picasso.with(MapsActivity.this).load(Uri.parse(img)).resize(250, 250).into(image);
                }
                return v;
            }
        });

        setUpClusterer();

    }

    private String getImage(String str){
        StringBuilder help= new StringBuilder();
        for (int i=0; i<str.length()-1; i++){
            if (str.charAt(i)=='&' && str.charAt(i+1)=='&'){
                if (PicAdapter.checkType(help.toString())==0) return help.toString();
                help = new StringBuilder();
                i++;
            }
            else help.append(str.charAt(i));
        }
        return "";
    }

    private void setUpClusterer() {
        mClusterManager = new ClusterManager<ToDoDocuments>(this, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        addItems();
    }

    private void addItems() {
        List<ToDoDocuments> listDocuments= new ArrayList<ToDoDocuments>();

        // Set some lat/lng coordinates to start with.

        DBNotes database = new DBNotes(this);
        listDocuments = database.getNotesAllPublic(LoginActivity.myUser.username);
        listDocuments.addAll(database.getNotesMy_Access1(LoginActivity.myUser.username));
        database.close();

        for (int i=0; i<listDocuments.size(); i++) {
            ToDoDocuments toDoDocuments = listDocuments.get(i);
            mClusterManager.addItem(toDoDocuments);
        }

    }
}
