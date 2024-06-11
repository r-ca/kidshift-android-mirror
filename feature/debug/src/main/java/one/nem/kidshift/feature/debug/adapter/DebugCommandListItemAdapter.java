package one.nem.kidshift.feature.debug.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.feature.debug.R;
import one.nem.kidshift.feature.debug.model.DebugCommandItemModel;

public class DebugCommandListItemAdapter extends RecyclerView.Adapter<DebugCommandListItemAdapter.DebugCommandListItemViewHolder>{

    private final List<DebugCommandItemModel> debugCommandListItems;

    public DebugCommandListItemAdapter(List<DebugCommandItemModel> debugCommandListItems) {
        this.debugCommandListItems = debugCommandListItems;
    }

    @NonNull
    @Override
    public DebugCommandListItemAdapter.DebugCommandListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lits_item_debug_debug_command_item, parent, false);
        return new DebugCommandListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebugCommandListItemAdapter.DebugCommandListItemViewHolder holder, int position) {
        DebugCommandItemModel item = debugCommandListItems.get(position);

        holder.command.setText(item.getCommand());
        holder.result.setText(item.getResult());
    }

    @Override
    public int getItemCount() {
        return debugCommandListItems.size();
    }

    public static class DebugCommandListItemViewHolder extends RecyclerView.ViewHolder {

        TextView command;
        TextView result;

        public DebugCommandListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            // Get ref to views
            command = itemView.findViewById(R.id.commandTextView);
            result = itemView.findViewById(R.id.resultTextView);
        }
    }
}
