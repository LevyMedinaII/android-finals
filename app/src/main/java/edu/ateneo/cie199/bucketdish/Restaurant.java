package edu.ateneo.cie199.bucketdish;

/**
 * Created by Kyle Bartido on 16/11/2017.
 */

public class Restaurant {
    public String name;
    public String location;
    public String cuisines;
    public Double budget;

    public Restaurant(String name, String location, String cuisines, Double budget) {
        this.name = name;
        this.location = location;
        this.cuisines = cuisines;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
