package com.dev.maks.weathercontrol.model.network;

import com.dev.maks.weathercontrol.model.pojo.searchLocation.Result;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.conditions.Conditions;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecast;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRepository implements IServiceRepository {

    private static final String URL_AUTOCOMPLETE = "http://autocomplete.wunderground.com/";
    private static final String URL_WEATHER_API = "http://api.wunderground.com/api/a359745955517f7b/";

    private WeatherService weatherService;
    private SearchLocationService searchLocationService;

    private void buildWeatherService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_WEATHER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherService = retrofit.create(WeatherService.class);
    }

    private void buildSearchLocationService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_AUTOCOMPLETE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        searchLocationService = retrofit.create(SearchLocationService.class);
    }

    @Override
    public Call<Conditions> getConditions(String locationZmw) {
        if (weatherService == null) {
            buildWeatherService();
        }
        return weatherService.getLocationConditions(locationZmw);
    }

    @Override
    public Call<Forecast> getForecast(String locationZmw) {
        if (weatherService == null) {
            buildWeatherService();
        }
        return weatherService.getLocationForecast(locationZmw);
    }

    @Override
    public Call<Result> getFoundLocations(String location) {
        if (searchLocationService == null) {
            buildSearchLocationService();
        }
        return searchLocationService.getFoundLocations(location);
    }
}
