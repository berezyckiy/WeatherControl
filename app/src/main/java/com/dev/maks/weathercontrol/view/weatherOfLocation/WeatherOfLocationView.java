package com.dev.maks.weathercontrol.view.weatherOfLocation;

import android.content.Context;

import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.conditions.Conditions;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecast;

public interface WeatherOfLocationView {

    void showLocationConditions(Conditions conditions);

    void showLocationForecast(Forecast forecast);

    void showErrorLoading();

    void showLoading();

    void closeLoading();

    Context getContext();
}
