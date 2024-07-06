package one.nem.kidshift.feature.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.feature.common.R;
import one.nem.kidshift.model.ChildModel;

public class ChildListItemAdapter extends RecyclerView.Adapter<ChildListItemAdapter.ViewHolder> {

    private List<ChildModel> childDataList;
    private CompleteButtonClickedCallback callback;

    public ChildListItemAdapter() {
        // Empty constructor
    }

    public void setChildDataList(List<ChildModel> childDataList) {
        this.childDataList = childDataList;
    }

    public void setCallback(CompleteButtonClickedCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ChildListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_item_task_completion_child, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildListItemAdapter.ViewHolder holder, int position) {
        ChildModel childData = childDataList.get(position);
        holder.childName.setText(childData.getName());
        holder.completedButton.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClicked(childData.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView childName;
        Button completedButton;

        public ViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            childName = itemView.findViewById(R.id.childNameTextView);
            completedButton = itemView.findViewById(R.id.actbutton);
        }
    }

    public interface CompleteButtonClickedCallback {
        void onClicked(String taskId);
    }
}
