package com.example.first_task_k__r__o__s__h.MainActivity.Fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.first_task_k__r__o__s__h.MainActivity.Activities.PostPreviewActivity;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.MapSourceInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.MapSource;
import com.example.first_task_k__r__o__s__h.MainActivity.OwnIconRendered;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;
import com.example.first_task_k__r__o__s__h.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private ImageButton imageButtonNewPost;
    private ClusterManager<MapItem> mClusterManager;
    private MapSourceInterface mapSourceInterface;
    private RunnableWithObject<PackageErrors> onFailure;
    private final static int PREVIEW_REQUEST = 1;
    private final static int FULL_VIEW_RESULT = 5;
    private final static int NEW_POST_REQUEST = 21;
    private final static int NEW_POST_RESULT = 22;
    private final static int DELETE_POST_RESULT = 42;
    private final static int EDIT_POST_REQUEST = 43;
    private final static int EDIT_POST_RESULT = 44;
    private final static int FULL_VIEW_REQUEST = 45;
    private String MAP_ITEM = "mapItem";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View viewMapFragment = inflater.inflate(R.layout.map_fragment, null);
        imageButtonNewPost = (ImageButton) viewMapFragment.findViewById(R.id.add_button_map_fragment);

        mapSourceInterface = MapSource.getInstance();
        onFailure = new RunnableWithObject<PackageErrors>() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        };

        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        imageButtonNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment newPostFragment = new New_Edit_PostFragment();
                newPostFragment.setTargetFragment(MapFragment.this, NEW_POST_REQUEST);
                fragmentManager.beginTransaction().add(R.id.main_container__, newPostFragment).commit();
            }
        });

        return viewMapFragment;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        GoogleMap mMap = googleMap;
        try {
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity().getApplicationContext(), R.raw.map_style_dark));
        } catch (Resources.NotFoundException e) {

        }
        mapSourceInterface = MapSource.getInstance();

        mClusterManager = new ClusterManager<MapItem>(getActivity().getApplicationContext(), mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mClusterManager.setRenderer(new OwnIconRendered(getActivity().getApplicationContext(), mMap, mClusterManager));
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MapItem>() {
            @Override
            public boolean onClusterItemClick(MapItem mapItem) {
                Intent myIntent = new Intent(getContext(), PostPreviewActivity.class);
                Gson gson = new Gson();
                String jsonMapItem = gson.toJson(mapItem);
                myIntent.putExtra(MAP_ITEM, jsonMapItem);
                startActivityForResult(myIntent, PREVIEW_REQUEST);
                return false;
            }
        });
        mMap.setOnMarkerClickListener(mClusterManager);


        List<MapItem> source = mapSourceInterface.getAll(mapSourceInterface.TYPE_PRIVATE, new RunnableWithObject<List<MapItem>>() {
            @Override
            public void run() {
                mClusterManager.clearItems();
                for (int i = 0; i < this.getDescription().size(); i++) {
                    mClusterManager.addItem(this.getDescription().get(i));
                }
                mClusterManager.cluster();
            }
        }, onFailure);
        for (int i = 0; i < source.size(); i++) {
            mClusterManager.addItem(source.get(i));
        }
        mClusterManager.cluster();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode){
            case PREVIEW_REQUEST:
                if (resultCode == FULL_VIEW_RESULT){
                    startFullViewPost();
                }
                break;
            case NEW_POST_REQUEST:
                if (resultCode==NEW_POST_RESULT){
                    addMapItem(getMapItemFromIntent(data));
                }
                break;
            case EDIT_POST_REQUEST:
                if (resultCode==EDIT_POST_RESULT){
                    updateMapItem(getMapItemFromIntent(data));
                } else if (resultCode == DELETE_POST_RESULT){
                    deleteMapItem(getMapItemFromIntent(data));
                }
                break;
            case FULL_VIEW_REQUEST:
                if (resultCode == DELETE_POST_RESULT){
                    deleteMapItem(getMapItemFromIntent(data));
                } else if (resultCode == EDIT_POST_RESULT){
                    startEditPostFragment();
                }
                break;
        }
    }

    MapItem getMapItemFromIntent(Intent data){
        Gson gson = new Gson();
        String jsonMapItem = data.getExtras().getString(MAP_ITEM, "");
        return gson.fromJson(jsonMapItem, MapItem.class);
    }

    private void startFullViewPost(){

    }

    private void startEditPostFragment(){

    }

    private void addMapItem (MapItem mapItem){
        mClusterManager.addItem(mapItem);
        mClusterManager.cluster();
    }

    private void deleteMapItem (MapItem mapItem){
        mClusterManager.removeItem(mapItem);
        mClusterManager.cluster();
    }

    private void updateMapItem(MapItem mapItem){
        mClusterManager.removeItem(mapItem);
        mClusterManager.addItem(mapItem);
        mClusterManager.cluster();
    }

}
