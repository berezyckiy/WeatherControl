package com.dev.maks.weathercontrol.presenter.savedLocations;

import android.database.sqlite.SQLiteException;

import com.dev.maks.weathercontrol.model.db.BaseDatabaseRepository;
import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;
import com.dev.maks.weathercontrol.view.savedLocationsScreen.SavedLocationView;

public class SavedLocationsPresenter implements ISavedLocationsPresenter {

    private BaseDatabaseRepository database;
    private SavedLocationView savedLocationView;

    public SavedLocationsPresenter(BaseDatabaseRepository databaseRepository) {
        this.database = databaseRepository;
    }

    public SavedLocationsPresenter(SavedLocationView view, BaseDatabaseRepository databaseRepository) {
        this.database = databaseRepository;
        this.savedLocationView = view;
    }

    @Override
    public void saveLocation(SavedLocation savedLocation) {
        database.addLocation(savedLocation);
    }

    @Override
    public void deleteLocation(SavedLocation savedLocation) {
        database.deleteLocation(savedLocation);
        savedLocationView.showSavedLocations(database.getSavedLocations());
    }

    @Override
    public void getSavedLocations() {
        savedLocationView.showSavedLocations(database.getSavedLocations());
    }
}
