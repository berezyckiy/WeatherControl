package com.dev.maks.weathercontrol.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;

import java.util.List;

public abstract class BaseDatabaseRepository {

    private final static String DATABASE_NAME = "myDB";
    private final static String TABLE_NAME = "locations";

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private final Context context;

    BaseDatabaseRepository(Context context) {
        this.context = context;
    }

    void open() {
        databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, 1);
        database = databaseHelper.getWritableDatabase();
    }

    void close() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    SQLiteDatabase getDatabase() {
        return database;
    }

    String getTableName() {
        return TABLE_NAME;
    }

    public abstract void addLocation(SavedLocation location);

    public abstract void deleteLocation(SavedLocation location);

    public abstract List<SavedLocation> getSavedLocations();
}
