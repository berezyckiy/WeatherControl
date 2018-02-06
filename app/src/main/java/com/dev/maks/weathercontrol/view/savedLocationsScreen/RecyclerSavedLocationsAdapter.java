package com.dev.maks.weathercontrol.view.savedLocationsScreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.maks.weathercontrol.R;
import com.dev.maks.weathercontrol.model.pojo.savedLocations.SavedLocation;
import com.dev.maks.weathercontrol.view.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

class RecyclerSavedLocationsAdapter extends RecyclerView.Adapter<RecyclerSavedLocationsAdapter.ViewHolder> {

    private List<SavedLocation> itemsList = new ArrayList<>();
    private OnItemClickListener<SavedLocation> clickListener;
    private OnItemClickListener<SavedLocation> longClickListener;


    RecyclerSavedLocationsAdapter(OnItemClickListener<SavedLocation> clickListener, OnItemClickListener<SavedLocation> longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    public void setData(List<SavedLocation> itemsList) {
        this.itemsList.clear();
        this.itemsList.addAll(itemsList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolder(inflateView(parent), clickListener, longClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    private SavedLocation getItem(int position) {
        return itemsList.get(position);
    }

    private View inflateView(ViewGroup view) {
        return LayoutInflater.from(view.getContext()).inflate(R.layout.location_item, view, false);
    }

    private ViewHolder createViewHolder(View view, OnItemClickListener<SavedLocation> clickListener, OnItemClickListener<SavedLocation> longClickListener) {
        return new ViewHolder(view, clickListener, longClickListener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private OnItemClickListener<SavedLocation> clickListener;
        private OnItemClickListener<SavedLocation> longClickListener;
        private TextView name;
        private TextView country;

        ViewHolder(View itemView, OnItemClickListener<SavedLocation> clickListener, OnItemClickListener<SavedLocation> longClickListener) {
            super(itemView);

            this.clickListener = clickListener;
            this.longClickListener = longClickListener;
            name = itemView.findViewById(R.id.name);
            country = itemView.findViewById(R.id.country);
        }

        void bind(final SavedLocation location) {
            name.setText(location.getName());
            country.setText(location.getCountry());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClick(location);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemClick(location);
                    return true;
                }
            });
        }
    }
}
