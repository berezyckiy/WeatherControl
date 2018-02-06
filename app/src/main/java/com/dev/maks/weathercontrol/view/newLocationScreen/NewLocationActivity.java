package com.dev.maks.weathercontrol.view.newLocationScreen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev.maks.weathercontrol.R;
import com.dev.maks.weathercontrol.model.db.DatabaseRepository;
import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;
import com.dev.maks.weathercontrol.model.pojo.searchLocation.Location;
import com.dev.maks.weathercontrol.presenter.newLocation.NewLocationPresenter;
import com.dev.maks.weathercontrol.presenter.savedLocations.SavedLocationsPresenter;
import com.dev.maks.weathercontrol.view.BaseActivity;
import com.dev.maks.weathercontrol.view.OnItemClickListener;
import com.dev.maks.weathercontrol.view.weatherOfLocation.WeatherOfLocationActivity;

import java.util.List;

public class NewLocationActivity extends BaseActivity implements NewLocationView {

    private NewLocationPresenter locationPresenter;

    private EditText inputLocation;
    private RecyclerFoundedLocationsAdapter recyclerAdapter;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_location);

        if (locationPresenter == null) {
            locationPresenter = new NewLocationPresenter(this);
        }

        initToolbar();
        initEditLocation();
        initRecycler();
        initLoadingIndicator();
        addButtonHome();
    }

    private void initEditLocation() {
        inputLocation = (EditText) findViewById(R.id.inputLocation);
        inputLocation.addTextChangedListener(new EditLocationChangeListener());
    }

    public void initLoadingIndicator() {
        loadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);
    }

    private void addButtonHome() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Location");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void showSaveLocationDialog(final Location location) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to save this location?");
        builder
                .setCancelable(false)
                .setPositiveButton("Yes", new OnPositiveButtonClickListener(location))
                .setNeutralButton("Cancel", new OnNeutralButtonClickListener())
                .setNegativeButton("No", new OnNegativeButtonClickListener(location));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void initRecycler() {
        RecyclerView recyclerOfFoundLocations = (RecyclerView) findViewById(R.id.recyclerOfFoundLocations);

        recyclerAdapter = new RecyclerFoundedLocationsAdapter(new OnFoundLocationClickListener());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerOfFoundLocations.setLayoutManager(layoutManager);
        recyclerOfFoundLocations.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public String getInputLocation() {
        return inputLocation.getText().toString();
    }

    @Override
    public void startSearchLocation() {
        locationPresenter.onStartSearch(getInputLocation());
    }

    @Override
    public void showFoundLocations(List<Location> foundedLocations) {
        recyclerAdapter.setData(foundedLocations);
    }

    @Override
    public void showErrorLoading() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        View view = this.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), 0);
            loadingIndicator.setVisibility(ProgressBar.VISIBLE);
        }
    }

    @Override
    public void closeLoading() {
        if (loadingIndicator != null) {
            loadingIndicator.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    private class EditLocationChangeListener implements TextWatcher {

        private StartSearchLocation startSearchLocation = new StartSearchLocation(null);

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before,
                                  int count) {
            startSearchLocation.cancel();
        }

        @Override
        public void afterTextChanged(final Editable s) {
            if (s.length() != 0) {
                startSearchLocation = new StartSearchLocation(s.toString());
                new Handler().postDelayed(startSearchLocation, 1500);
            }
        }
    }

    private class StartSearchLocation implements Runnable {

        private String location;
        private boolean isCancelled;

        StartSearchLocation(String location) {
            this.location = location;
        }

        @Override
        public void run() {
            if (!isCancelled && location != null) {
                startSearchLocation();
            }
        }

        void cancel() {
            isCancelled = true;
        }
    }

    private class OnFoundLocationClickListener implements OnItemClickListener<Location> {

        @Override
        public void onItemClick(Location location) {
            showSaveLocationDialog(location);
        }
    }

    private class OnPositiveButtonClickListener implements DialogInterface.OnClickListener {

        private Location location;

        public OnPositiveButtonClickListener(Location location) {
            this.location = location;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            SavedLocationsPresenter presenter =
                    new SavedLocationsPresenter(new DatabaseRepository(getApplicationContext()));
            SavedLocation savedLocation = new SavedLocation();
            savedLocation.setName(location.getName());
            savedLocation.setCountry(location.getC());
            savedLocation.setZmw(location.getZmw());
            presenter.saveLocation(savedLocation);
            Intent intent = new Intent(NewLocationActivity.this, WeatherOfLocationActivity.class);
            intent.putExtra("zmw", savedLocation.getZmw());
            startActivity(intent);
            finish();
        }
    }

    private class OnNegativeButtonClickListener implements DialogInterface.OnClickListener {

        private Location location;

        public OnNegativeButtonClickListener(Location location) {
            this.location = location;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent(NewLocationActivity.this, WeatherOfLocationActivity.class);
            intent.putExtra("zmw", location.getZmw());
            startActivity(intent);
            finish();
        }
    }

    private class OnNeutralButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

}
