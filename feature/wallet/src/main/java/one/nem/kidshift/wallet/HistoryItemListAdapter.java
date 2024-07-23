package one.nem.kidshift.wallet;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import one.nem.kidshift.model.HistoryModel;

public class HistoryItemListAdapter extends RecyclerView.Adapter<HistoryItemListAdapter.ViewHolder> {

    enum ViewType {
        WITH_HEADER(1),
        ITEM(0);

        private final int value;

        ViewType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

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

    public List<HistoryModel> getCheckedHistoryDataList() {
        List<HistoryModel> checkedHistoryDataList = new ArrayList<>();
        for (HistoryModelExtended historyModelExtended : historyDataList.getList()) {
            if (historyModelExtended.isChecked()) {
                checkedHistoryDataList.add(historyModelExtended);
            }
        }
        return checkedHistoryDataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ViewType.WITH_HEADER.getValue();
        } else {
            if (isFirstOfMonth(historyDataList.getList().get(position))) {
                return ViewType.WITH_HEADER.getValue();
            } else {
                return ViewType.ITEM.getValue();
            }
        }
    }

    @NonNull
    @Override
    public HistoryItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ViewType.WITH_HEADER.getValue()) {
            LinearLayout view = new LinearLayout(parent.getContext());
            view.addView(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_list_item, parent, false));
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_list_item, parent, false);
            return new ViewHolder(view);
        }
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_list_item, parent, false);
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemListAdapter.ViewHolder holder, int position) {
        HistoryModelExtended historyData = this.historyDataList.getList().get(position);
        holder.historyItemNameTextView.setText(historyData.getTaskName());
        holder.historyItemRewardTextView.setText(historyData.getReward() + "円");
        holder.historyItemCheckBox.setChecked(historyData.isChecked());
        holder.historyItemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.historyItemCheckBox.isShown()) {
                historyData.setChecked(isChecked);
            }
        });
    }

    private boolean isFirstOfMonth(HistoryModel historyModel) {
        // 1個前の要素と比較して月が変わったかどうかを判定する
        if ()
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
