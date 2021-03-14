package com.codestallions.spacemmo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codestallions.spacemmo.R;
import com.codestallions.spacemmo.model.DestinationModel;
import com.codestallions.spacemmo.ui.fragments.DestinationDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class GeneralNestedAdapter extends RecyclerView.Adapter<GeneralNestedAdapter.ViewHolder> {

    private Context context;

    private List<DestinationModel> destinations = new ArrayList<>();

    public GeneralNestedAdapter(Context context) {
        this.context = context;
    }

    public void setDestinations(List<DestinationModel> destinations) {
        this.destinations = destinations;
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
        holder.destinationItem.setOnClickListener(v -> {
            DestinationDialogFragment dialog = DestinationDialogFragment.newInstance(holder.title.getText().toString(), destinations.get(position).getLocationInAU());
            dialog.show(FragmentManager.findFragment(holder.itemView).getParentFragmentManager(), "fragment_destination");
        });
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout destinationItem;
        private final TextView title;

        public ViewHolder(View view) {
            super(view);
            destinationItem = view.findViewById(R.id.general_nested_item_container);
            title = view.findViewById(R.id.general_nested_item_text);
        }
    }
}