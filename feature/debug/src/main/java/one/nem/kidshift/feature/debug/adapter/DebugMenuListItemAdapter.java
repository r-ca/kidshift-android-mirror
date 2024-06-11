package one.nem.kidshift.feature.debug.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.feature.debug.R;
import one.nem.kidshift.feature.debug.model.DebugMenuListItemModel;

public class DebugMenuListItemAdapter extends RecyclerView.Adapter<DebugMenuListItemAdapter.DebugMenuListItemViewHolder> {

    private final List<DebugMenuListItemModel> debugMenuListItems;

    public DebugMenuListItemAdapter(List<DebugMenuListItemModel> debugMenuListItems) {
        this.debugMenuListItems = debugMenuListItems;
    }

    @NonNull
    @Override
    public DebugMenuListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_debug_menu, parent, false);
        return new DebugMenuListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebugMenuListItemViewHolder holder, int position) {
        DebugMenuListItemModel item = debugMenuListItems.get(position);
        // Set title
        holder.title.setText(item.getTitle());
        // Set description
        holder.description.setText(item.getDescription());

        holder.itemView.setOnClickListener( v -> {
            Navigation.findNavController(v).navigate(item.getDestinationId());
        });
    }

    @Override
    public int getItemCount() {
        return debugMenuListItems.size();
    }

    public static class DebugMenuListItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        public DebugMenuListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ref to views
            title = itemView.findViewById(R.id.debugMenuItemTitle);
            description = itemView.findViewById(R.id.debugMenuItemDescription);
        }
    }
}
