package com.codestallions.spacemmo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.codestallions.spacemmo.R;

public class DestinationDialogFragment extends DialogFragment {

    private DestinationDialogFragment() {
    }

    public static DestinationDialogFragment newInstance(String title) {
        DestinationDialogFragment fragment = new DestinationDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_destination_dialog, container, false);
    }
}
