package com.codestallions.spacemmo.model;

import java.util.List;

public class PlanetModel {

    private String name;

    private String imagePath;

    private List<PlanetDestination> destinations;

    public PlanetModel() {
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<PlanetDestination> getDestinations() {
        return destinations;
    }
}
