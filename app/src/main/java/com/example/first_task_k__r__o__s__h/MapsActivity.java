package com.example.first_task_k__r__o__s__h;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ClusterManager<ToDoDocuments> mClusterManager;
    private Cluster<ToDoDocuments> chosenCluster;
    private ImageButton imageButtonAddNoteMaps;
    public static String TODO_DOCUMENT = "ToDoDocuments";
    public static int TODO_NOTE_REQUEST=1;
    public static List<ToDoDocuments> listDocuments = new ArrayList<ToDoDocuments>();
    public int size=0;
    private final static UserApi userApi=Controller.getApi();
    private SizeOfAccounts accounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        imageButtonAddNoteMaps = (ImageButton) findViewById(R.id.add_button);
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
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_dark));

            if (!success) {
            }
        } catch (Resources.NotFoundException e) {

        }


//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            // Use default InfoWindow frame
//            @Override
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            // Defines the contents of the InfoWindow
//            @Override
//            public View getInfoContents(Marker arg0) {
//
//                // Getting view from the layout file info_window_layout
//                View v = getLayoutInflater().inflate(R.layout.info_window, null);
//
//                // Getting the snippet from the marker
//                final String snippet = arg0.getSnippet();
//
//                // Getting the snippet from the marker
//                String titlestr = arg0.getTitle();
//
//                String cutchar1= "%#";
//                String cutchar2= "%##";
//                final String types = snippet.substring(0,snippet.indexOf(cutchar1));
//                String vicinitystr = snippet.substring(snippet.indexOf(cutchar1)+2, snippet.indexOf(cutchar2));
//                String base= snippet.substring(snippet.indexOf(cutchar2)+3);
//
//                // Getting reference to the TextView to set latitude
//                final TextView title = (TextView) v.findViewById(R.id.place_title);
//
//                TextView vicinity = (TextView) v.findViewById(R.id.place_vicinity);
//
//                ImageView image = (ImageView) v.findViewById(R.id.place_icon);
//
//                // Setting the latitude
//                title.setText(titlestr);
//
//                vicinity.setText(vicinitystr);
//                String img=getImage(base, types);
//                if (!img.equals("")) {
//
////                       Picasso.with(MapsActivity.this).load(Uri.parse(img)).resize(250, 250).into(image);
//                    byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
////
////                    File file=null;
////                    Picasso.with(MapsActivity.this).load(file).resize(250, 250).into(image);
//                    image.setImageBitmap(ToDoDocuments.ConvertBase64ToBitmap(img));
//                }
//
//                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                    public void onInfoWindowClick(Marker marker) {
//                        Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
//                        intent.putExtra("name", snippet);
//                        intent.putExtra("title", title.getText());
//                        startActivity(intent);
//                    }
//
//                });
//
//                return v;
//            }
//
//        });

        setUpClusterer();


    }

    private String getImage(String base, String types){
        StringBuilder help= new StringBuilder();
        int index = -1;
        for (int i=0; i<types.length(); i++){
            if (types.charAt(i)=='0'){
                index=i;
                break;
            }
        }

        int helpCountOfElement=0;
        if (index!=-1){
            for (int i=0; i<base.length()-1; i++){
                if (base.charAt(i)=='&' && base.charAt(i+1)=='&'){
                    if (helpCountOfElement==index)
                        return help.toString();
                    else {
                        helpCountOfElement++;
                        i++;
                        help = new StringBuilder();
                    }
                }
                else help.append(base.charAt(i));
            }

        }
        return "";
    }

    private void setUpClusterer() {
        mClusterManager = new ClusterManager<ToDoDocuments>(this, mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        addItems_help();
    }

    private void addItems() {
        List<ToDoDocuments> listDocuments= new ArrayList<ToDoDocuments>();

        // Set some lat/lng coordinates to start with.
        int info = getIntent().getExtras().getInt("global", 0);


        if (info==0) {
            listDocuments = DBNotes.getNotesAllMy(LoginActivity.myUser.username);
        }else{
            listDocuments = DBNotes.getNotesAllPublic();
        }

        for (int i=0; i<listDocuments.size(); i++) {
            ToDoDocuments toDoDocuments = listDocuments.get(i);
            mClusterManager.addItem(toDoDocuments);
        }

    }


    private void addItems_help() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 200; i++) {
            double offset = 0.005;
            lat = lat + offset;
            lng = lng + offset;
            ToDoDocuments offsetItem = new ToDoDocuments();
            offsetItem.setLocation(lat+"/"+lng);
            mClusterManager.setRenderer(new OwnIconRendered(getApplicationContext(), mMap, mClusterManager));

            mClusterManager.addItem(offsetItem);

        }
    }



    public class OwnIconRendered extends DefaultClusterRenderer<ToDoDocuments>{

        private boolean shouldCluster = true;
        private static final int MIN_CLUSTER_SIZE = 1;


        public OwnIconRendered(Context context, GoogleMap map, ClusterManager<ToDoDocuments> clusterManager) {
            super(context, map, clusterManager);
            shouldCluster=true;

        }

//        @Override
//        protected void onBeforeClusterItemRendered(ToDoDocuments item, MarkerOptions markerOptions) {
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ellipse));
//        }
        @Override
        protected void onClusterRendered(Cluster<ToDoDocuments> cluster, Marker marker) {

            Bitmap bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.ellipse);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (cluster.getSize()!=0) {
                    bitmapImg= Bitmap.createScaledBitmap(bitmapImg, (int) (bitmapImg.getWidth()*Math.log(cluster.getSize())/3),
                            (int) (bitmapImg.getHeight()*Math.log(cluster.getSize()))/3, false);
                }

            }

            BitmapDescriptor img = BitmapDescriptorFactory.fromBitmap(bitmapImg);
            marker.setIcon(img);
            marker.setAnchor(0.5f, 0.5f);
            marker.setTitle(String.valueOf(cluster.getSize()));
            marker.setInfoWindowAnchor((float) 0.5, (float) 0.5);
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<ToDoDocuments> cluster) {

            if(shouldCluster)
            {
                return cluster.getSize() > MIN_CLUSTER_SIZE;
            }
            else
            {
                return shouldCluster;
            }

        }

    }

    public void onClickAddNoteMaps(View view){
        ToDoDocuments toDoDocuments = new ToDoDocuments();
        showDocuments(toDoDocuments);
    }

    private void showDocuments(ToDoDocuments toDoDocuments){
        imageButtonAddNoteMaps.setVisibility(View.GONE);
        Intent myIntent = new Intent(this, Note.class);
        myIntent.putExtra(TODO_DOCUMENT,toDoDocuments);
        startActivityForResult(myIntent, TODO_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode ,Intent data){
        imageButtonAddNoteMaps.setVisibility(View.VISIBLE);
        if (requestCode == TODO_NOTE_REQUEST){
            switch(resultCode){
                case RESULT_CANCELED: {
                    break;
                }
                case Note.RESULT_SAVE: {
                    final ToDoDocuments toDoDocuments =  data.getParcelableExtra("ToDoDocuments");
                    addDocument(toDoDocuments);
                    if (toDoDocuments.getId().equals("-1")){

                        userApi.getSizeOfNotes("1").enqueue(new Callback<SizeOfAccounts>() {
                            @Override
                            public void onResponse(Call<SizeOfAccounts> call, Response<SizeOfAccounts> response) {
                                accounts=response.body();
                                accounts.setSize(String.valueOf(Integer.parseInt(accounts.getSize())+1));
                                userApi.deleteSizeOfNotes("1").enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        userApi.pushSizeOfNotes(accounts).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                toDoDocuments.setId(accounts.getSize());
                                                DBNotes.insertNote(toDoDocuments);
                                            }
                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(MapsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(MapsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            @Override
                            public void onFailure(Call<SizeOfAccounts> call, Throwable t) {
                                Toast.makeText(MapsActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        DBNotes.updateNote(toDoDocuments);
                    }
                    break;
                }
                case Note.RESULT_DELETE: {
                    ToDoDocuments toDoDocuments = data.getParcelableExtra("ToDoDocuments");
                    DBNotes.deleteNote(toDoDocuments);
                    deleteDocument(toDoDocuments);
                    break;
                }

                default:
                    break;

            }
        }
    }

    private void addDocument(ToDoDocuments toDoDocuments){
        if (toDoDocuments.getNumber()==-1) {
            toDoDocuments.setNumber(listDocuments.size());
            listDocuments.add(toDoDocuments);

        }   else {
            listDocuments.set(toDoDocuments.getNumber(), toDoDocuments);
        }
        mClusterManager.addItem(toDoDocuments);
    }

    public void deleteDocument(ToDoDocuments toDoDocuments){
        listDocuments.remove(toDoDocuments);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        listDocuments.clear();
    }


}
