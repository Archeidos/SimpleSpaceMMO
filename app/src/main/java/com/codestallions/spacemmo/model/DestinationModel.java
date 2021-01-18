package com.codestallions.spacemmo.model;

import java.util.List;

public class DestinationModel {

    private String name;

    private List<InteractiveModel> interactives;

    public DestinationModel() {
    }

    public String getName() {
        return name;
    }

    public List<InteractiveModel> getInteractives() {
        return interactives;
    }
}
