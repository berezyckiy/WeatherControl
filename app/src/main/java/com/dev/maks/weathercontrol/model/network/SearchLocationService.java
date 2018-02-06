package com.dev.maks.weathercontrol.model.network;

import com.dev.maks.weathercontrol.model.pojo.searchLocation.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface SearchLocationService {

    @GET("aq?query=")
    Call<Result> getFoundLocations(@Query("query") String location);
}
