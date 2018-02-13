package com.dev.maks.weathercontrol.view.weatherOfLocation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.maks.weathercontrol.R;
import com.dev.maks.weathercontrol.model.pojo.weatherOfLocation.forecast.Forecastday_;
import com.dev.maks.weathercontrol.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecyclerOfForecastAdapter extends RecyclerView.Adapter<RecyclerOfForecastAdapter.ViewHolder> {

    private List<Forecastday_> itemsList = new ArrayList<>();
    private OnItemClickListener<Forecastday_> listener;

    RecyclerOfForecastAdapter(OnItemClickListener<Forecastday_> listener) {
        this.listener = listener;
    }

    public void setData(List<Forecastday_> itemsList) {
        this.itemsList.clear();
        this.itemsList.addAll(itemsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerOfForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolder(inflateView(parent), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerOfForecastAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private Forecastday_ getItem(int position) {
        return itemsList.get(position);
    }

    private View inflateView(ViewGroup view) {
        return LayoutInflater.from(view.getContext()).inflate(R.layout.forecast_item, view, false);
    }

    private RecyclerOfForecastAdapter.ViewHolder createViewHolder(View view, OnItemClickListener<Forecastday_> listener) {
        return new RecyclerOfForecastAdapter.ViewHolder(view, listener);
    }

    private void clearSelections(Forecastday_ forecast) {
        for (Forecastday_ temp : itemsList) {
            if (temp != forecast) {
                temp.setChecked(false);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.weekDay)
        TextView name;

        @BindView(R.id.highAndLowTemp)
        TextView temperature;

        @BindView(R.id.selected)
        ImageView selected;

        @BindView(R.id.icon)
        ImageView icon;

        private OnItemClickListener<Forecastday_> listener;

        ViewHolder(View itemView, OnItemClickListener<Forecastday_> listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.listener = listener;
        }

        void bind(final Forecastday_ forecast) {
            name.setText(forecast.getDate().getWeekday());
            temperature.setText(forecast.getHigh().getCelsius() + "\\" + forecast.getLow().getCelsius());
            selected.setVisibility(forecast.isChecked() ? View.VISIBLE : View.INVISIBLE);
            icon.setImageBitmap(forecast.getBitmap());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(forecast);
                        forecast.setChecked(true);
                        clearSelections(forecast);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
