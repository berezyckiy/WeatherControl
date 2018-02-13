package com.dev.maks.weathercontrol.view.newLocationScreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.maks.weathercontrol.R;
import com.dev.maks.weathercontrol.model.pojo.searchLocation.Location;
import com.dev.maks.weathercontrol.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class RecyclerFoundedLocationsAdapter extends RecyclerView.Adapter<RecyclerFoundedLocationsAdapter.ViewHolder> {

    private List<Location> itemsList = new ArrayList<>();
    private OnItemClickListener<Location> listener;

    RecyclerFoundedLocationsAdapter(OnItemClickListener<Location> listener) {
        this.listener = listener;
    }

    public void setData(List<Location> itemsList) {
        this.itemsList.clear();
        this.itemsList.addAll(itemsList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerFoundedLocationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolder(inflateView(parent), listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private Location getItem(int position) {
        return itemsList.get(position);
    }

    private View inflateView(ViewGroup view) {
        return LayoutInflater.from(view.getContext()).inflate(R.layout.location_item, view, false);
    }

    private RecyclerFoundedLocationsAdapter.ViewHolder createViewHolder(View view, OnItemClickListener<Location> listener) {
        return new RecyclerFoundedLocationsAdapter.ViewHolder(view, listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.country)
        TextView country;

        private OnItemClickListener<Location> listener;

        ViewHolder(View itemView, OnItemClickListener<Location> listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.listener = listener;
        }

        void bind(final Location location) {
            name.setText(location.getName());
            country.setText(location.getC());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(location);
                    }
                }
            });
        }
    }
}
