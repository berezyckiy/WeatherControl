package com.dev.maks.weathercontrol.model.network;

import android.graphics.Bitmap;

import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.conditions.Conditions;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface WeatherService {

    @GET("conditions/q/zmw:{zmw}.json")
    Call<Conditions> getLocationConditions(@Path("zmw") String locationZmw);

    @GET("forecast/q/zmw:{zmw}.json")
    Call<Forecast> getLocationForecast(@Path("zmw") String locationZmw);
}
