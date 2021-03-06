package com.dev.maks.weathercontrol.view.savedLocationsScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dev.maks.weathercontrol.R;
import com.dev.maks.weathercontrol.model.db.DatabaseRepository;
import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;
import com.dev.maks.weathercontrol.presenter.savedLocations.SavedLocationsPresenter;
import com.dev.maks.weathercontrol.view.BaseActivity;
import com.dev.maks.weathercontrol.view.OnItemClickListener;
import com.dev.maks.weathercontrol.view.newLocationScreen.NewLocationActivity;
import com.dev.maks.weathercontrol.view.weatherOfLocation.WeatherOfLocationActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SavedLocationsActivity extends BaseActivity implements SavedLocationView {

    @BindView(R.id.recyclerOfLocations)
    RecyclerView recyclerOfLocations;

    private SavedLocationsPresenter presenter;
    private RecyclerSavedLocationsAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locations);
        ButterKnife.bind(this);

        if (presenter == null) {
            presenter = new SavedLocationsPresenter(this, new DatabaseRepository(getApplicationContext()));
        }

        initToolbar();
        initRecycler();
    }

    private void buildDeleteLocationDialog(final SavedLocation location) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to delete this location?");
        builder
                .setCancelable(false)
                .setPositiveButton("Yes", new OnPositiveButtonClickListener(location))
                .setNegativeButton("No", new OnNegativeButtonClickListener());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void initRecycler() {
        recyclerAdapter = new RecyclerSavedLocationsAdapter(new OnSavedLocationClickListener(),
                new OnSavedLocationLongClickListener());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerOfLocations.setLayoutManager(layoutManager);
        recyclerOfLocations.setAdapter(recyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSavedLocations();
    }

    @Override
    public void startWeatherOfLocationScreen(SavedLocation savedLocation) {
        Intent intent = new Intent(SavedLocationsActivity.this, WeatherOfLocationActivity.class);
        intent.putExtra("zmw", savedLocation.getZmw());
        startActivity(intent);
    }

    @Override
    public void showSavedLocations(List<SavedLocation> savedLocations) {
        recyclerAdapter.setData(savedLocations);
    }

    @OnClick(R.id.fab)
    public void onClick() {
        startActivity(new Intent(SavedLocationsActivity.this, NewLocationActivity.class));
    }

    private class OnSavedLocationClickListener implements OnItemClickListener<SavedLocation> {

        @Override
        public void onItemClick(SavedLocation savedLocation) {
            startWeatherOfLocationScreen(savedLocation);
        }
    }

    private class OnSavedLocationLongClickListener implements OnItemClickListener<SavedLocation> {

        @Override
        public void onItemClick(SavedLocation location) {
            buildDeleteLocationDialog(location);
        }
    }

    private class OnPositiveButtonClickListener implements DialogInterface.OnClickListener {

        private SavedLocation location;

        OnPositiveButtonClickListener(SavedLocation location) {
            this.location = location;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            presenter.deleteLocation(location);
        }
    }

    private class OnNegativeButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

}
