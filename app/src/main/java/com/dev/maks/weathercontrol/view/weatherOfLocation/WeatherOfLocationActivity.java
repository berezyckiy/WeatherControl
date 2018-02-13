package com.dev.maks.weathercontrol.view.weatherOfLocation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.maks.weathercontrol.R;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.conditions.Conditions;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecast;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecastday_;
import com.dev.maks.weathercontrol.presenter.weatherOfLocation.WeatherOfLocationPresenter;
import com.dev.maks.weathercontrol.view.BaseActivity;
import com.dev.maks.weathercontrol.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherOfLocationActivity extends BaseActivity
        implements WeatherOfLocationView, SwipeRefreshLayout.OnRefreshListener {

    private static final Character SYMBOL_CALCIUM = '\u00B0';

    @BindView(R.id.precipitation)
    TextView precipitation;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.temperature)
    TextView temperature;

    @BindView(R.id.humidity)
    TextView humidity;

    @BindView(R.id.feelsLike)
    TextView feelsLike;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.loadingIndicator)
    ProgressBar loadingIndicator;

    @BindView(R.id.recyclerOfWeatherNextDays)
    RecyclerView recyclerOfWeatherNextDays;

    private WeatherOfLocationPresenter presenter;
    private RecyclerOfForecastAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_of_location);
        ButterKnife.bind(this);

        if (presenter == null) {
            presenter = new WeatherOfLocationPresenter(this);
        }

        initToolbar();
        addButtonHome();
        initRecycler();

        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.getLocationWeather(getIntent().getStringExtra("zmw"));
    }

    private void addButtonHome() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setConditionViewsVisibility() {
        feelsLike.setVisibility(View.VISIBLE);
    }

    private void setForecastVisibility() {
        feelsLike.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initRecycler() {
        recyclerAdapter = new RecyclerOfForecastAdapter(new OnForecastDayClickListener());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerOfWeatherNextDays.setLayoutManager(layoutManager);
        recyclerOfWeatherNextDays.setItemAnimator(new DefaultItemAnimator());
        recyclerOfWeatherNextDays.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void showLocationConditions(Conditions conditions) {
        setConditionViewsVisibility();

        String temp = String.valueOf(conditions.getCurrentObservation().getTempC()) + SYMBOL_CALCIUM;
        String humid = "Relative humidity: " + conditions.getCurrentObservation().getRelativeHumidity();
        String feels = "Feels like: " + conditions.getCurrentObservation().getFeelslikeC() + SYMBOL_CALCIUM;

        precipitation.setText(conditions.getCurrentObservation().getWeather());
        location.setText(conditions.getCurrentObservation().getDisplayLocation().getCity());
        temperature.setText(temp);
        humidity.setText(humid);
        feelsLike.setText(feels);
    }

    @Override
    public void showLocationForecast(Forecast forecast) {
        List<Forecastday_> forecastdays = new ArrayList<>();
        forecastdays.addAll(forecast.getForecast().getSimpleforecast().getForecastday());
        forecastdays.remove(0);

        recyclerAdapter.setData(forecastdays);
    }

    @Override
    public void showErrorLoading() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (hasWindowFocus()) {
            loadingIndicator.setVisibility(ProgressBar.VISIBLE);
        }
    }

    @Override
    public void closeLoading() {
        if (loadingIndicator != null) {
            loadingIndicator.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onRefresh() {
        presenter.getLocationWeather(getIntent().getStringExtra("zmw"));
        swipeRefreshLayout.setRefreshing(false);
    }

    private class OnForecastDayClickListener implements OnItemClickListener<Forecastday_> {

        @Override
        public void onItemClick(Forecastday_ forecast) {
            String temp = String.valueOf(forecast.getHigh().getCelsius()) + SYMBOL_CALCIUM;
            String humid = "Relative humidity: " + forecast.getAvehumidity();

            precipitation.setText(forecast.getConditions());
            temperature.setText(temp);
            humidity.setText(humid);

            setForecastVisibility();
        }
    }
}
