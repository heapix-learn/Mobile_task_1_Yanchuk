package com.example.first_task_k__r__o__s__h.MainActivity.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.first_task_k__r__o__s__h.R;

public class UserProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userProfileFragment = inflater.inflate(R.layout.user_profile_fragment, null);
        return userProfileFragment;
    }
}
