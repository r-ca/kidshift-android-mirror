package one.nem.kidshift.feature.parent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.tasks.TaskItemModel;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.MainViewHolder> {

    private final List<ChildModel> childDataList;

    ChildListAdapter(List<ChildModel> childDataList) {
        this.childDataList = childDataList;
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        CheckBox childname;

        MainViewHolder(@NonNull View itemView) {
            super(itemView);
            childname = itemView.findViewById(R.id.childname);
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_child_list,parent,false);
        return new MainViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MainViewHolder holder,int position){
        ChildModel childData = this.childDataList.get(position);
        holder.childname.setText(childData.getName());
    }

    @Override
    public int getItemCount(){ return childDataList.size();}
}
