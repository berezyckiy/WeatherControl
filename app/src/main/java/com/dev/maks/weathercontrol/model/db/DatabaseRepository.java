package com.dev.maks.weathercontrol.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;

import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository extends BaseDatabaseRepository {

    public DatabaseRepository(Context context) {
        super(context);
    }

    @Override
    public void addLocation(SavedLocation location) {
        open();
        if (!isLocationSaved(location)) {
            ContentValues cv = new ContentValues();
            cv.put("name", location.getName());
            cv.put("country", location.getCountry());
            cv.put("zmw", location.getZmw());
            getDatabase().insert(getTableName(), null, cv);
        }
        close();
    }

    @Override
    public void deleteLocation(SavedLocation location) {
        open();
        getDatabase().delete(getTableName(), "id = " + location.getId(), null);
        close();
    }

    @Override
    public List<SavedLocation> getSavedLocations() {
        open();

        List<SavedLocation> savedLocations = new ArrayList<>();
        SavedLocation savedLocation;
        Cursor cursor = getDatabase().query(getTableName(), null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                savedLocation = new SavedLocation();
                savedLocation.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
                savedLocation.setName(cursor.getString(cursor.getColumnIndex("name")));
                savedLocation.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                savedLocation.setZmw(cursor.getString(cursor.getColumnIndex("zmw")));
                savedLocations.add(savedLocation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();

        return savedLocations;
    }

    private boolean isLocationSaved(SavedLocation savedLocation) {
        Cursor cursor = getDatabase().query(getTableName(), null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex("zmw")).equals(savedLocation.getZmw())) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
}
