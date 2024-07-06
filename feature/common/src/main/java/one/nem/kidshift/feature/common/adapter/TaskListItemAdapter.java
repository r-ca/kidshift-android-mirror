package one.nem.kidshift.feature.common.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.feature.common.R;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class TaskListItemAdapter extends RecyclerView.Adapter<TaskListItemAdapter.ViewHolder> {

    private List<TaskItemModel> taskItemModelList;

    private CompleteButtonClickedCallback callback;

    public TaskListItemAdapter() {
    }

    public void setTaskItemModelList(List<TaskItemModel> taskItemModelList) {
        this.taskItemModelList = taskItemModelList;
    }

    public void setCallback(CompleteButtonClickedCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public TaskListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_item_common_task, null);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskListItemAdapter.ViewHolder holder, int position) {
        TaskItemModel taskItemModel = taskItemModelList.get(position);
        holder.taskTitle.setText(taskItemModel.getName());
        holder.taskContents.setText(taskItemModel.getReward() + "円"); // TODO: ハードコードやめる
        holder.completedButton.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClicked(taskItemModel.getId(), taskItemModel.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;
        TextView taskContents;
        Button completedButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title_text_view);
            taskContents = itemView.findViewById(R.id.task_contents_text_view);
            completedButton = itemView.findViewById(R.id.actbutton);

        }
    }

    public interface CompleteButtonClickedCallback {
        void onClicked(String taskId, String taskName);
    }
}
