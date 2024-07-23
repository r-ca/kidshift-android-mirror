package one.nem.kidshift.wallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import one.nem.kidshift.model.HistoryModel;

public class HistoryItemListAdapter extends RecyclerView.Adapter<HistoryItemListAdapter.ViewHolder> {

    public static class HistoryModelExtended extends HistoryModel {
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        // add isChecked to the constructor
        public HistoryModelExtended() {
            super();
            isChecked = false;
        }

        // copy constructor
        public HistoryModelExtended(HistoryModel historyModel) {
            super();
            this.setId(historyModel.getId());
            this.setPaid(historyModel.isPaid());
            this.setChildId(historyModel.getChildId());
            this.setRegisteredAt(historyModel.getRegisteredAt());
            this.setTaskId(historyModel.getTaskId());
            this.setTaskName(historyModel.getTaskName());
            this.setReward(historyModel.getReward());
            this.setChecked(false);
        }
    }

    public static class HistoryModelExtendedList {
        private List<HistoryModelExtended> list;

        public List<HistoryModelExtended> getList() {
            return list;
        }

        public void setList(List<HistoryModelExtended> list) {
            this.list = list;
        }

        // clear all checked items
        public void clearChecked() {
            for (HistoryModelExtended item : list) {
                item.setChecked(false);
            }
        }

        // constructor
        public HistoryModelExtendedList() {
            list = new ArrayList<>();
        }
    }

    private HistoryModelExtendedList historyDataList;

    public void setHistoryDataList(List<HistoryModel> historyDataList) {
        this.historyDataList = new HistoryModelExtendedList();
        for (HistoryModel historyModel : historyDataList) {
            this.historyDataList.getList().add(new HistoryModelExtended(historyModel));
        }
    }

    @NonNull
    @Override
    public HistoryItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemListAdapter.ViewHolder holder, int position) {
        HistoryModelExtended historyData = this.historyDataList.getList().get(position);
        holder.historyItemNameTextView.setText(historyData.getTaskName());
        holder.historyItemRewardTextView.setText(historyData.getReward() + "円");
        holder.historyItemCheckBox.setChecked(historyData.isChecked());
        holder.historyItemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.historyDataList.getList().get(position).setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return historyDataList == null ? 0 : historyDataList.getList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyItemNameTextView;
        TextView historyItemRewardTextView;
        CheckBox historyItemCheckBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyItemNameTextView = itemView.findViewById(R.id.historyItemNameTextView);
            historyItemRewardTextView = itemView.findViewById(R.id.historyItemRewardTextView);
            historyItemCheckBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
