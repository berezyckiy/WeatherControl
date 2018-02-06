package com.dev.maks.weathercontrol.model.pojo.savedLocations;

public class SavedLocation {

    private Integer id;
    private String name;
    private String country;
    private String zmw;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZmw() {
        return zmw;
    }

    public void setZmw(String zmw) {
        this.zmw = zmw;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
