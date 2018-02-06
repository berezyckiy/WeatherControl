package com.dev.maks.weathercontrol.presenter.newLocation;

import com.dev.maks.weathercontrol.model.network.IServiceRepository;
import com.dev.maks.weathercontrol.model.network.ServiceRepository;
import com.dev.maks.weathercontrol.model.pojo.searchLocation.Result;
import com.dev.maks.weathercontrol.model.pojo.searchLocation.Location;
import com.dev.maks.weathercontrol.view.newLocationScreen.NewLocationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLocationPresenter implements INewLocationPresenter {

    private IServiceRepository serviceRepository;
    private NewLocationView locationView;

    public NewLocationPresenter(NewLocationView locationView) {
        serviceRepository = new ServiceRepository();
        this.locationView = locationView;
    }

    @Override
    public void onStartSearch(String location) {
        locationView.showLoading();
        getResponse(location);
    }

    private void getResponse(String location) {
        serviceRepository.getFoundLocations(location).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                locationView.closeLoading();
                locationView.showFoundLocations(getOnlyCity(response.body()));
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                locationView.closeLoading();
                locationView.showErrorLoading();
            }
        });
    }

    private List<Location> getOnlyCity(Result result) {
        List<Location> onlyCityList = new ArrayList<>();
        for (Location location : result.getRESULTS()) {
            if (location.getType().equals("city")) {
                location.setName(getFormattedName(location.getName()));
                onlyCityList.add(location);
            }
        }
        return onlyCityList;
    }

    private String getFormattedName(String name) {
        return name.substring(0, name.indexOf(","));
    }
}
