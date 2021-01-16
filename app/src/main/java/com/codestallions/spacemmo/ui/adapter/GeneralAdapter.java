package com.codestallions.spacemmo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import com.codestallions.spacemmo.GlideApp;
import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.model.PlanetModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {

    private Context context;
    private List<PlanetModel> planetList = new ArrayList<>();

    public GeneralAdapter(Context context) {
        this.context = context;
    }

    public void setPlanetList(List<PlanetModel> planetList) {
        this.planetList = planetList;
    }

    @NonNull
    @Override
    public GeneralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.general_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.topLayout.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(holder.container, new Fade());
            holder.bottomLayout.setVisibility(holder.bottomLayout.getVisibility() != VISIBLE ? VISIBLE : GONE);
        });
        holder.titleTextView.setText(planetList.get(position).getName());
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(planetList.get(position).getImagePath());
        GlideApp.with(context)
                .load(storage)
                .override(200, 220)
                .into(holder.imageView);
        if (planetList.get(position).getDestinations() != null && !planetList.get(position).getDestinations().isEmpty()) {
            holder.nestedRecycler.setAdapter(new GeneralNestedAdapter(planetList.get(position).getDestinations(), context));
            holder.nestedRecycler.addOnItemTouchListener(new NestedRecyclerListener());
            holder.nestedRecycler.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView container;
        private final ConstraintLayout topLayout;
        private final ConstraintLayout bottomLayout;
        private final TextView titleTextView;
        private final ImageView imageView;
        private final RecyclerView nestedRecycler;

        public ViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.general_item_root_container);
            topLayout = view.findViewById(R.id.general_item_top_layout);
            bottomLayout = view.findViewById(R.id.general_item_bottom_layout);
            bottomLayout.setVisibility(GONE);
            titleTextView = view.findViewById(R.id.general_item_title);
            imageView = view.findViewById(R.id.general_item_image);
            nestedRecycler = view.findViewById(R.id.general_nested_recycler);
        }
    }
}