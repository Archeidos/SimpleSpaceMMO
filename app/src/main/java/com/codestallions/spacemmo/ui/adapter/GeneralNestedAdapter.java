package com.codestallions.spacemmo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.model.PlanetDestination;

import java.util.ArrayList;
import java.util.List;

public class GeneralNestedAdapter extends RecyclerView.Adapter<GeneralNestedAdapter.ViewHolder> {

    private Context context;

    private List<PlanetDestination> destinations = new ArrayList<>();

    public GeneralNestedAdapter(List<PlanetDestination> destinations, Context context) {
        this.destinations = destinations;
        this.context = context;
    }

    @NonNull
    @Override
    public GeneralNestedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.general_nested_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(destinations.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.general_nested_item_text);
        }
    }
}