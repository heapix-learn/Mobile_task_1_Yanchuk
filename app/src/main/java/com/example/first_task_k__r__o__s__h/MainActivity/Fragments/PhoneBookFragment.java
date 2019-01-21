package com.example.first_task_k__r__o__s__h.MainActivity.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.first_task_k__r__o__s__h.R;

public class PhoneBookFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View phoneBookFragment = inflater.inflate(R.layout.phone_book_fragment, null);
        return phoneBookFragment;
    }
}
