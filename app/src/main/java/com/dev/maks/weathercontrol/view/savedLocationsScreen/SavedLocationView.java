package com.dev.maks.weathercontrol.view.savedLocationsScreen;

import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;

import java.util.List;

public interface SavedLocationView {

    void startWeatherOfLocationScreen(SavedLocation savedLocation);

    void showSavedLocations(List<SavedLocation> savedLocations);
}
