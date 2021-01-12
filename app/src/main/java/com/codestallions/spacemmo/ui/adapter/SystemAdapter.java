package com.codestallions.spacemmo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.codestallions.spacemmo.GlideApp;
import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.model.Planet;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.ViewHolder> {

    private Fragment fragment;
    private List<Planet> planetList = new ArrayList<>();

    public SystemAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public SystemAdapter(List<Planet> planetList, Fragment fragment) {
        this.planetList = planetList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SystemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_system_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.planetNameTextView.setText(planetList.get(position).getName());
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(planetList.get(position).getImagePath());
        GlideApp.with(fragment)
                .load(storage)
                .override(200, 220)
                .into(holder.planetImageView);
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView planetNameTextView;
        private final ImageView planetImageView;

        public ViewHolder(View view) {
            super(view);
            planetNameTextView = view.findViewById(R.id.system_item_text);
            planetImageView = view.findViewById(R.id.system_item_image);
        }
    }
}
