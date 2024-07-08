package one.nem.kidshift.feature.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.feature.common.R;
import one.nem.kidshift.model.ChildModel;

public class SelectShowChildListItemAdapter extends RecyclerView.Adapter<SelectShowChildListItemAdapter.ViewHolder> {

    private List<ChildModel> childDataList;
    private CompleteButtonClickedCallback callback;

    public SelectShowChildListItemAdapter() {
        // Empty constructor
    }

    public SelectShowChildListItemAdapter(List<ChildModel> childDataList) {
        this.childDataList = childDataList;
    }

    public void setChildDataList(List<ChildModel> childDataList) {
        this.childDataList = childDataList;
    }

    public void setCallback(CompleteButtonClickedCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public SelectShowChildListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_select_show_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectShowChildListItemAdapter.ViewHolder holder, int position) {
        ChildModel childData = childDataList.get(position);
        holder.childName.setText(childData.getName());
        holder.selectButton.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClicked(childData.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return childDataList == null ? 0 : childDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView childName;
        Button selectButton;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.childNameTextView);
            selectButton = itemView.findViewById(R.id.selectButton);
        }
    }

    public interface CompleteButtonClickedCallback {
        void onClicked(String taskId);
    }
}
