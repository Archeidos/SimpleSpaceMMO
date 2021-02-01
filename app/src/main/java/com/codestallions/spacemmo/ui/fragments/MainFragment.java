package com.codestallions.spacemmo.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.SpaceMMO;
import com.codestallions.spacemmo.databinding.FragmentMainBinding;
import com.codestallions.spacemmo.ui.activities.MainActivity;
import com.codestallions.spacemmo.ui.viewmodel.MainViewModel;

public class MainFragment extends BaseFragment {

    private MainViewModel mainViewModel;
    private FragmentMainBinding mainBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mainBinding.setMainViewModel(mainViewModel);

        mainViewModel.syncPlayerData().observe(getViewLifecycleOwner(), playerModel -> {
            if (playerModel != null) {
                SpaceMMO.getSession().cachePlayerData(playerModel);
            } else {
               MainActivity mainActivity = (MainActivity) requireActivity();
               Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
               mainActivity.navigateToLogin();
            }
        });

        return mainBinding.getRoot();
    }
}
