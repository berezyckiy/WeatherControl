package com.dev.maks.weathercontrol.model.network;

import com.dev.maks.weathercontrol.model.pojo.searchLocation.Result;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.conditions.Conditions;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecast;

import retrofit2.Call;

public interface IServiceRepository {

    Call<Conditions> getConditions(String locationZmw);

    Call<Forecast> getForecast(String locationZmw);

    Call<Result> getFoundLocations(String location);
}
