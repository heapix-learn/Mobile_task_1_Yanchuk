package com.example.first_task_k__r__o__s__h.MainActivity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first_task_k__r__o__s__h.Adapters.PhotoAdapterGrid;
import com.example.first_task_k__r__o__s__h.Adapters.VideoAdapterGrid;
import com.example.first_task_k__r__o__s__h.AppContext;
import com.example.first_task_k__r__o__s__h.MainActivity.Activities.SettingMenuForFullView;
import com.example.first_task_k__r__o__s__h.MainActivity.Interfaces.PostManagerInterface;
import com.example.first_task_k__r__o__s__h.MainActivity.PackageErrors;
import com.example.first_task_k__r__o__s__h.MainActivity.PostManager;
import com.example.first_task_k__r__o__s__h.MainActivity.RunnableWithObject;
import com.example.first_task_k__r__o__s__h.Post;
import com.example.first_task_k__r__o__s__h.R;

public class FullViewOfThePostFragment extends Fragment {

    private Post post;
    private TextView textTitle;
    private TextView textDescription;
    private TextView textNumberOfVideo;
    private TextView textNumberOfPhoto;
    private GridView gridViewForPhoto;
    private GridView gridViewForVideo;
    private PhotoAdapterGrid photoAdapterGrid;
    private Bundle bundle;
    private View lineForAddMedia;
    private static final String TODO_DOCUMENT="ToDoDocument";
    public static final int DELETE_POST_REQUEST=2;
    public static final int TODO_NOTE_SETTING_REQUEST=3;
    public static final int EDIT_POST_REQUEST=4;
    private PostManagerInterface postManager = new PostManager();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fullViewOgThePostFraagment = inflater.inflate(R.layout.full_view_of_the_post_fragment, null);


        bundle = savedInstanceState;

//        post = getIntent().getParcelableExtra(TODO_DOCUMENT);

        textTitle = (TextView) fullViewOgThePostFraagment.findViewById(R.id.textTitleFullView);
        textDescription=(TextView) fullViewOgThePostFraagment.findViewById(R.id.textDescriptionFullView);
        gridViewForPhoto = (GridView) fullViewOgThePostFraagment.findViewById(R.id.gridViewForPhotoFullView);
        gridViewForVideo = (GridView) fullViewOgThePostFraagment.findViewById(R.id.gridViewForVideoFullView);
        textNumberOfPhoto = (TextView) fullViewOgThePostFraagment.findViewById(R.id.number_of_photoFullView);
        textNumberOfVideo = (TextView) fullViewOgThePostFraagment.findViewById(R.id.number_of_videoFullView);
        lineForAddMedia = (View) fullViewOgThePostFraagment.findViewById(R.id.lineForAddMedia_activity_note_);
        textTitle.setText(post.getTitle());
        textDescription.setText(post.getTextNote());
        if (post.getImagePath().size()!=0) {
            textNumberOfPhoto.setVisibility(View.VISIBLE);
            textNumberOfPhoto.setText(post.getImagePath().size() + " Photos");
            gridViewForPhoto.setVisibility(View.VISIBLE);
            photoAdapterGrid = new PhotoAdapterGrid(AppContext.getInstance(), bundle, post.getImagePath(), textNumberOfPhoto, lineForAddMedia, null, gridViewForPhoto, false);
            gridViewForPhoto.setAdapter(photoAdapterGrid);
        }
        if (post.getVideoPath().size()!=0){
            textNumberOfVideo.setVisibility(View.VISIBLE);
            textNumberOfVideo.setText(post.getVideoPath().size() + " Videos");
            gridViewForVideo.setVisibility(View.VISIBLE);
            gridViewForVideo.setAdapter(new VideoAdapterGrid(AppContext.getInstance(), bundle, post.getVideoPath(), post.getVideoScreen(), textNumberOfVideo, lineForAddMedia, null, gridViewForVideo, false));
        }

        ImageButton settingsFullView = (ImageButton) fullViewOgThePostFraagment.findViewById(R.id.settings_full_view);
        settingsFullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSettingsPost();
            }
        });

        return fullViewOgThePostFraagment;
    }



    public void onClickSettingsPost(){
        Intent myIntent = new Intent(getContext(), SettingMenuForFullView.class);
        startActivityForResult(myIntent, TODO_NOTE_SETTING_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TODO_NOTE_SETTING_REQUEST) {
            switch (resultCode) {
                case DELETE_POST_REQUEST:
                    RunnableWithObject onSuccess = new RunnableWithObject(){
                        @Override
                        public void run(){

                        }
                    };

                    RunnableWithObject onFailure = new RunnableWithObject<PackageErrors>(){
                        @Override
                        public void run(){
                            Toast.makeText(AppContext.getInstance(), "server error", Toast.LENGTH_SHORT).show();
                        }
                    };

//                    postManager.deletePost(post, onSuccess, onFailure);
                    break;
                case EDIT_POST_REQUEST:

                    break;
            }
        }
    }
}
