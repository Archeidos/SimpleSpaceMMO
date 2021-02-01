package com.codestallions.spacemmo.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codestallions.spacemmo.model.PlayerModel;
import com.codestallions.spacemmo.network.MainRepository;

public class MainViewModel extends ViewModel {

    private MainRepository mainRepository;

    public MainViewModel() {
        mainRepository = MainRepository.getInstance();
    }

    public MutableLiveData<PlayerModel> syncPlayerData() {
        return mainRepository.retrievePlayerData();
    }
}
