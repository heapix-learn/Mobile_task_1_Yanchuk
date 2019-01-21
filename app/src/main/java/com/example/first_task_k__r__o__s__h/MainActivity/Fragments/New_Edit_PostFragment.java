package com.example.first_task_k__r__o__s__h.MainActivity.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.first_task_k__r__o__s__h.Adapters.CustomAutoCompleteAdapter;
import com.example.first_task_k__r__o__s__h.Adapters.PhotoAdapterGrid;
import com.example.first_task_k__r__o__s__h.Adapters.VideoAdapterGrid;
import com.example.first_task_k__r__o__s__h.AppContext;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.MapItemManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.PostManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItem;
import com.example.first_task_k__r__o__s__h.MainActivity.MapItemManager;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.PostManager;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;
import com.example.first_task_k__r__o__s__h.MyLocationListener;
import com.example.first_task_k__r__o__s__h.Post;
import com.example.first_task_k__r__o__s__h.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class New_Edit_PostFragment extends Fragment {

    protected PostManagerInterface postManager = new PostManager();
    protected MapItemManagerInterface mapItemManager = new MapItemManager();
    private String MAP_ITEM = "mapItem";
    private static final int NEW_POST_RESULT = 22;
    private static final int DELETE_POST_RESULT = 42;

    protected RunnableWithObject onSuccess = new RunnableWithObject() {
        @Override
        public void run() {

        }
    };

    protected RunnableWithObject<PackageErrors> onFailure = new RunnableWithObject<PackageErrors>() {
        @Override
        public void run() {

        }
    };

    protected EditText textTitle;
    protected EditText textDescription;
    private AutoCompleteTextView textLocation;
    private TextView textNumberOfVideo;
    private TextView textNumberOfPhoto;
    protected Post post;

    private ImageButton lockButton;
    private String DEFAULT_PHOTO_URL = "http://i.imgur.com/DvpvklR.png";
    private String DEFAULT_VIDEO_URL = "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    private String DEFAULT_VIDEO_SCREEN_URL = "http://i.imgur.com/DvpvklR.png";

    static final int GALLERY_REQUEST = 1;
    private GridView gridViewForPhoto;
    private GridView gridViewForVideo;
    private Bundle bundle;
    private View lineForAddMedia;
    private PhotoAdapterGrid photoAdapterGrid;
    private VideoAdapterGrid videoAdapterGrid;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View postFragment = inflater.inflate(R.layout.note_fragment, null);
        post = new Post();

        bundle = savedInstanceState;
        textTitle = (EditText) postFragment.findViewById(R.id.textTitle_);
        textDescription = (EditText) postFragment.findViewById(R.id.textDescription_);
        textLocation = (AutoCompleteTextView) postFragment.findViewById(R.id.textLocation_);
        lockButton = (ImageButton) postFragment.findViewById(R.id.lock__);
        gridViewForPhoto = (GridView) postFragment.findViewById(R.id.gridViewForPhoto_);
        gridViewForVideo = (GridView) postFragment.findViewById(R.id.gridViewForVideo_);
        textNumberOfPhoto = (TextView) postFragment.findViewById(R.id.number_of_photo_);
        textNumberOfVideo = (TextView) postFragment.findViewById(R.id.number_of_video_);
        lineForAddMedia = (View) postFragment.findViewById(R.id.lineForAddMedia_activity_note_);
        ImageButton addPostButton = (ImageButton) postFragment.findViewById(R.id.add_post__);
        ImageButton garbageButton = (ImageButton) postFragment.findViewById(R.id.garbage__);
        ImageButton addMediaButton = (ImageButton) postFragment.findViewById(R.id.add_media__);

        AddLocationForPost();

        textTitle.setText(post.getTitle());
        textDescription.setText(post.getTextNote());
        textDescription.setMovementMethod(new ScrollingMovementMethod());

        textLocation.setText(post.getNameLocation());

        photoAdapterGrid = new PhotoAdapterGrid(AppContext.getInstance(), bundle, post.getImagePath(), textNumberOfPhoto, lineForAddMedia, null, gridViewForPhoto, true);
        videoAdapterGrid = new VideoAdapterGrid(AppContext.getInstance(), bundle, post.getVideoPath(), post.getVideoScreen(), textNumberOfVideo, lineForAddMedia, null, gridViewForVideo, true);

        ImageGalleryIsVisible();

        VideoGalleryIsVisible();

        CustomAutoCompleteAdapter adapter = new CustomAutoCompleteAdapter(AppContext.getInstance());
        textLocation.setAdapter(adapter);
        textLocation.setOnItemClickListener(onItemClickListener);

        AddTouchListenerForTextDescription();

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddPost();
            }
        });

        garbageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGarbage();
            }
        });

        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddMedia();
            }
        });

        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLock();
            }
        });

        AddGarbage();

        return postFragment;
    }



    private void VideoGalleryIsVisible(){
        if (post.getVideoPath().size() != 0) {
            textNumberOfVideo.setVisibility(View.VISIBLE);
            lineForAddMedia.setVisibility(View.VISIBLE);
            if (gridViewForVideo.getWidth() != 0) {
                int t = 0;
                if (post.getVideoPath().size() % 3 == 0) t = 1;
                gridViewForVideo.setLayoutParams(new LinearLayout.LayoutParams(gridViewForVideo.getWidth(), ((post.getVideoPath().size() / 3) + 1 - t) * 310));
            }
            textNumberOfVideo.setText(post.getVideoPath().size() + " Videos");
            gridViewForVideo.setVisibility(View.VISIBLE);
            videoAdapterGrid = new VideoAdapterGrid(AppContext.getInstance(), bundle, post.getVideoPath(), post.getVideoScreen(), textNumberOfVideo, lineForAddMedia, photoAdapterGrid, gridViewForVideo, true);
            gridViewForVideo.setAdapter(videoAdapterGrid);

        }
    }

    private void ImageGalleryIsVisible(){
        if (post.getImagePath().size() != 0) {
            textNumberOfPhoto.setVisibility(View.VISIBLE);
            lineForAddMedia.setVisibility(View.VISIBLE);
            textNumberOfPhoto.setText(post.getImagePath().size() + " Photos");
            gridViewForPhoto.setVisibility(View.VISIBLE);

            if (gridViewForPhoto.getWidth() != 0) {
                int t = 0;
                if (post.getImagePath().size() % 3 == 0) t = 1;
                gridViewForPhoto.setLayoutParams(new LinearLayout.LayoutParams(gridViewForPhoto.getWidth(), ((post.getImagePath().size() / 3) + 1 - t) * 310));
            }
            photoAdapterGrid = new PhotoAdapterGrid(AppContext.getInstance(), bundle, post.getImagePath(), textNumberOfPhoto, lineForAddMedia, videoAdapterGrid, gridViewForPhoto, true);
            gridViewForPhoto.setAdapter(photoAdapterGrid);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void AddTouchListenerForTextDescription(){
        textDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.textDescription_) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    public void onClickLock() {
        if (post.getAccess() == 0) {
            post.setAccess(1);
            lockButton.setImageResource(R.drawable.lock_close);
        } else {
            post.setAccess(0);
            lockButton.setImageResource(R.drawable.lock_open);
        }
    }

    public void onClickAddMedia() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/* image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    private void AddLocationForPost(){
        if (post.getLocationLatitude() < -999 && post.getLocationLongitude() < -999) {
            MyLocationListener.SetUpLocationListener(AppContext.getInstance());
            post.setLocation("" + MyLocationListener.imHere.getLatitude() + "/" + MyLocationListener.imHere.getLongitude());
            Geocoder geocoder = new Geocoder(AppContext.getInstance());
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocation(MyLocationListener.imHere.getLatitude(), MyLocationListener.imHere.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.setNameLocation(list.get(0).getLocality());
            textLocation.setText(post.getNameLocation());
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Geocoder geocoder = new Geocoder(AppContext.getInstance());
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocationName(textLocation.getText().toString(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.setNameLocation(textLocation.getText().toString());
            post.setLocation(list.get(0).getLatitude() + "/" + list.get(0).getLongitude());

            if (post.getImagePath().size() == 0 && post.getVideoPath().size() == 0) {
                lineForAddMedia.setVisibility(View.GONE);
            }
        }
    };


    protected void AddGarbage() {
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    if (checkType(selectedImage.toString()) == 0) {
                        post.setImagePath(DEFAULT_PHOTO_URL);

                        if (gridViewForPhoto.getWidth() != 0) {
                            int t = 0;
                            if (post.getImagePath().size() % 3 == 0) t = 1;
                            gridViewForPhoto.setLayoutParams(new LinearLayout.LayoutParams(gridViewForPhoto.getWidth(), ((post.getImagePath().size() / 3) + 1 - t) * 310));
                        }

                        textNumberOfPhoto.setVisibility(View.VISIBLE);
                        lineForAddMedia.setVisibility(View.VISIBLE);
                        gridViewForPhoto.setVisibility(View.VISIBLE);
                        photoAdapterGrid = new PhotoAdapterGrid(AppContext.getInstance(), bundle, post.getImagePath(), textNumberOfPhoto, lineForAddMedia, videoAdapterGrid, gridViewForPhoto, true);
                        gridViewForPhoto.setAdapter(photoAdapterGrid);
                        textNumberOfPhoto.setText(post.getImagePath().size() + " Photos");
                        lineForAddMedia.setVisibility(View.VISIBLE);
                    } else {
                        post.setVideoPath(DEFAULT_VIDEO_URL);
                        post.setVideoScreen(DEFAULT_VIDEO_SCREEN_URL);
                        if (gridViewForVideo.getWidth() != 0) {
                            int t = 0;
                            if (post.getVideoPath().size() % 3 == 0) t = 1;
                            gridViewForVideo.setLayoutParams(new LinearLayout.LayoutParams(gridViewForVideo.getWidth(), ((post.getVideoPath().size() / 3) + 1 - t) * 310));
                        }
                        textNumberOfVideo.setVisibility(View.VISIBLE);
                        lineForAddMedia.setVisibility(View.VISIBLE);
                        textNumberOfVideo.setText(post.getVideoPath().size() + " Videos");
                        gridViewForVideo.setVisibility(View.VISIBLE);
                        lineForAddMedia.setVisibility(View.VISIBLE);
                        videoAdapterGrid = new VideoAdapterGrid(AppContext.getInstance(), bundle, post.getVideoPath(), post.getVideoScreen(), textNumberOfVideo, lineForAddMedia, photoAdapterGrid, gridViewForVideo, true);
                        gridViewForVideo.setAdapter(videoAdapterGrid);
                    }

                }
        }
    }

    public int checkType(String str) {
        int index = str.lastIndexOf("/images/");
        if (index != -1) return 0;
        else return 1;
    }


    private void onClickAddPost() {

        post.setTitle(textTitle.getText().toString());
        post.setTextNote(textDescription.getText().toString());
        post.setCreateDate(new Date());
        postManager.addPost(post);
        mapItemManager.addMapItem(new MapItem(post));
        Intent intent = new Intent();

        Gson gson = new Gson();
        String jsonMapItem = gson.toJson(new MapItem(post));
        intent.putExtra(MAP_ITEM, jsonMapItem);

        getTargetFragment().onActivityResult(getTargetRequestCode(), NEW_POST_RESULT, intent);

        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


    public void onClickGarbage() {
        postManager.deletePost(post);
        mapItemManager.deleteMapItem(new MapItem(post));

        Gson gson = new Gson();
        String jsonMapItem = gson.toJson(new MapItem(post));
        Intent intent = new Intent();
        intent.putExtra(MAP_ITEM, jsonMapItem);

        getTargetFragment().onActivityResult(getTargetRequestCode(), DELETE_POST_RESULT, intent);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


}