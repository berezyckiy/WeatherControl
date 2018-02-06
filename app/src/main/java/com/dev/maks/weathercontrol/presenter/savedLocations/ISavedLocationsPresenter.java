package com.dev.maks.weathercontrol.presenter.savedLocations;

import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;

import java.util.List;

interface ISavedLocationsPresenter {

    void saveLocation(SavedLocation savedLocation);

    void deleteLocation(SavedLocation savedLocation);

    void getSavedLocations();
}
