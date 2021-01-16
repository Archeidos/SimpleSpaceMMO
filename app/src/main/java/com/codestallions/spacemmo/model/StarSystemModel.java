package com.codestallions.spacemmo.model;

import java.util.List;

public class StarSystemModel {

    private String name;

    private String location;

    private float security;

    private List<PlanetModel> planets;

    public StarSystemModel() {
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public float getSecurity() {
        return security;
    }

    public List<PlanetModel> getPlanets() {
        return planets;
    }
}
