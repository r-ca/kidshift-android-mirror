package one.nem.kidshift.feature.parent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.MainViewHolder> {

    private final List<TaskItemModel> taskDataList;

    ParentAdapter(List<TaskItemModel> taskDataList) { this.taskDataList = taskDataList; }



    static class MainViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle;
        TextView taskContents;

        MainViewHolder(@NonNull View itemView){
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title_text_view);
            taskContents = itemView.findViewById(R.id.task_contents_text_view);
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent_task_list_item, parent,false);
        return new MainViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MainViewHolder holder,int position){
        TaskItemModel taskData = this.taskDataList.get(position);
        holder.taskTitle.setText(taskData.getDisplayName());
        holder.taskContents.setText(Long.toString(taskData.getReward()));
    }

    @Override
    public int getItemCount(){
        return taskDataList.size();
    }
}
