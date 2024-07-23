package one.nem.kidshift.wallet;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public interface CheckBoxChangedCallback {
        void onCheckBoxChanged();
    }

    public boolean hasChecked() {
        for (HistoryModelExtended historyModelExtended : historyDataList.getList()) {
            if (historyModelExtended.isChecked()) {
                return true;
            }
        }
        return false;
    }

    private HistoryModelExtendedList historyDataList;
    private CheckBoxChangedCallback callback;

    public void setHistoryDataList(List<HistoryModel> historyDataList) {
        this.historyDataList = new HistoryModelExtendedList();
        for (HistoryModel historyModel : historyDataList) {
            this.historyDataList.getList().add(new HistoryModelExtended(historyModel));
        }
    }

    public void setCallback(CheckBoxChangedCallback callback) {
        this.callback = callback;
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

            view.setOrientation(LinearLayout.VERTICAL);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.addView(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_month_header, parent, false));
            view.addView(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_list_item, parent, false));
            return new MonthHeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_list_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemListAdapter.ViewHolder holder, int position) {
        HistoryModelExtended historyData = this.historyDataList.getList().get(position);
        if (historyData.isPaid()) {
            holder.historyItemCheckBox.setVisibility(View.GONE);
        } else {
            holder.historyItemCheckBox.setVisibility(View.VISIBLE);
        }
        holder.historyItemNameTextView.setText(historyData.getTaskName());
        holder.historyItemRewardTextView.setText(historyData.getReward() + "円");
        holder.historyItemCheckBox.setChecked(historyData.isChecked());
        holder.historyItemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (holder.historyItemCheckBox.isShown()) {
                historyData.setChecked(isChecked);
                callback.onCheckBoxChanged();
            }
        });
        if (holder instanceof MonthHeaderViewHolder) {
//            ((MonthHeaderViewHolder) holder).monthHeaderTextView.setText(historyData.getRegisteredAt().getMonth() + "月");
            // DEBUG: 月をまたぐデータがないので
            ((MonthHeaderViewHolder) holder).monthHeaderTitle.setText(historyData.getRegisteredAt().getDate() + "日");
            ((MonthHeaderViewHolder) holder).monthTotalTextView.setText(getMonthTotal(historyData) + "円");
            ((MonthHeaderViewHolder) holder).checkAllButton.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "実装中", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private boolean isFirstOfMonth(HistoryModel historyModel) {
        // 1個前の要素と比較して月が変わったかどうかを判定する
        if (historyDataList.getList().indexOf(historyModel) == 0) {
            return true;
        } else {
            HistoryModel previousHistoryModel = historyDataList.getList().get(historyDataList.getList().indexOf(historyModel) - 1);
            // getMonth()はDeprecated TODO: やめる
//            return historyModel.getRegisteredAt().getMonth() != previousHistoryModel.getRegisteredAt().getMonth();
            // DEBUG: 月をまたぐデータがないので
            return historyModel.getRegisteredAt().getDate() != previousHistoryModel.getRegisteredAt().getDate();
        }
    }

    private int getMonthTotal(HistoryModel historyModel) {
        int total = historyModel.getReward();
        int index = historyDataList.getList().indexOf(historyModel) + 1;
        try {
            while (!isFirstOfMonth(this.historyDataList.getList().get(index))) {
                total += historyModel.getReward();
                index++;
            }
        } catch (IndexOutOfBoundsException e) {
            // 1個しかない場合 Workaround
            // TODO: 例外をひねり潰すのではなく，そもそも発生しないようにするべき
        }
        return total;
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

    public static class MonthHeaderViewHolder extends HistoryItemListAdapter.ViewHolder {
        // かなり邪道な方法だけど，とりあえず取得できるので

        TextView monthHeaderTitle;
        TextView monthTotalTextView;
        ImageButton checkAllButton;

        public MonthHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            monthHeaderTitle = itemView.findViewById(R.id.monthHeaderTitle);
            monthTotalTextView = itemView.findViewById(R.id.monthTotalTextView);
            checkAllButton = itemView.findViewById(R.id.checkAllButton);
        }
    }
}
