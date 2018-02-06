package com.dev.maks.weathercontrol.presenter.weatherOfLocation;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.dev.maks.weathercontrol.model.network.IServiceRepository;
import com.dev.maks.weathercontrol.model.network.ServiceRepository;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.conditions.Conditions;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecast;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecastday_;
import com.dev.maks.weathercontrol.view.weatherOfLocation.WeatherOfLocationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherOfLocationPresenter implements IWeatherOfLocationPresenter {

    private IServiceRepository serviceRepository;
    private WeatherOfLocationView weatherOfLocationView;

    public WeatherOfLocationPresenter(WeatherOfLocationView weatherOfLocationView) {
        this.weatherOfLocationView = weatherOfLocationView;
        serviceRepository = new ServiceRepository();
    }

    @Override
    public void getLocationWeather(final String locationZmw) {
        weatherOfLocationView.showLoading();
        getConditionsAndForecast(locationZmw);
    }

    private void getConditionsAndForecast(final String locationZmw) {
        serviceRepository.getConditions(locationZmw).enqueue(new Callback<Conditions>() {
            @Override
            public void onResponse(Call<Conditions> call, Response<Conditions> response) {
                getForecast(locationZmw);
                weatherOfLocationView.showLocationConditions(response.body());
            }

            @Override
            public void onFailure(Call<Conditions> call, Throwable t) {
                weatherOfLocationView.closeLoading();
                weatherOfLocationView.showErrorLoading();
            }
        });
    }

    private void getForecast(String locationZmw) {
        serviceRepository.getForecast(locationZmw).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, final Response<Forecast> response) {
                loadIconsAndShowForecast(response.body());
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                weatherOfLocationView.closeLoading();
                weatherOfLocationView.showErrorLoading();
            }
        });
    }

    private void loadIconsAndShowForecast(final Forecast forecast) {
        for (final Forecastday_ forecastday : forecast.getForecast().getSimpleforecast().getForecastday()) {
            Picasso.with(weatherOfLocationView.getContext())
                    .load(forecastday.getIconUrl())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            forecastday.setBitmap(bitmap);
                            weatherOfLocationView.closeLoading();
                            weatherOfLocationView.showLocationForecast(forecast);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            forecastday.setBitmap(null);
                            weatherOfLocationView.closeLoading();
                            weatherOfLocationView.showLocationForecast(forecast);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
        }
    }
}
