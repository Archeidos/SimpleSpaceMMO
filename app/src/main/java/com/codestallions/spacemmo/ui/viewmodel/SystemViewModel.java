package com.codestallions.spacemmo.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codestallions.spacemmo.model.Planet;
import com.codestallions.spacemmo.network.MainRepository;

import java.util.List;

public class SystemViewModel extends ViewModel {

    private MainRepository mainRepository;

    public SystemViewModel() {
        mainRepository = MainRepository.getInstance();
    }

    public MutableLiveData<List<Planet>> getLocalPlanetList() {
        return mainRepository.retrieveLocalPlanets();
    }

}
