package com.codestallions.spacemmo.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codestallions.spacemmo.model.PlanetModel;
import com.codestallions.spacemmo.network.MainRepository;

import java.util.List;

public class SystemViewModel extends ViewModel {

    private MainRepository mainRepository;

    public SystemViewModel() {
        mainRepository = MainRepository.getInstance();
    }

    public MutableLiveData<List<PlanetModel>> getLocalPlanetList() {
        return mainRepository.retrieveLocalPlanets();
    }

}
