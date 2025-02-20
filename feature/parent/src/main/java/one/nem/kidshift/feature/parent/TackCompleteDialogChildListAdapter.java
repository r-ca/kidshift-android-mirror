package one.nem.kidshift.feature.parent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.model.ChildModel;

public class TackCompleteDialogChildListAdapter extends RecyclerView.Adapter<TackCompleteDialogChildListAdapter.MainViewHolder> {

    private final List<ChildModel> childDataList;

    TackCompleteDialogChildListAdapter(List<ChildModel> childDataList){this.childDataList = childDataList;}

    static class MainViewHolder extends RecyclerView.ViewHolder{
        TextView childName;

        MainViewHolder(@NonNull View itemView){
            super(itemView);
            childName = itemView.findViewById(R.id.childNameTextView);
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_act_child_name,parent,false);
        return new MainViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MainViewHolder holder,int position){
        ChildModel childData = this.childDataList.get(position);
        holder.childName.setText(childData.getName());
    }

    public int getItemCount(){ return childDataList.size();}
}
