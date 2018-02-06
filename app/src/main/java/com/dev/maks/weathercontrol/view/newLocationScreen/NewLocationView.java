package com.dev.maks.weathercontrol.view.newLocationScreen;

import com.dev.maks.weathercontrol.model.pojo.searchLocation.Location;

import java.util.List;

public interface NewLocationView {

    String getInputLocation();

    void startSearchLocation();

    void showFoundLocations(List<Location> foundedLocations);

    void showErrorLoading();

    void showLoading();

    void closeLoading();
}
