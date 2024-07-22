package one.nem.kidshift.wallet;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.model.HistoryModel;

public class HistoryItemListAdapter extends RecyclerView.Adapter<HistoryItemListAdapter.ViewHolder> {

    private List<HistoryModel> historyDataList;

    public void setHistoryDataList(List<HistoryModel> historyDataList) {
        this.historyDataList = historyDataList;
    }

    @NonNull
    @Override
    public HistoryItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemListAdapter.ViewHolder holder, int position) {
        HistoryModel historyData = this.historyDataList.get(position);
        holder.historyItemNameTextView.setText(historyData.getTaskName());
        holder.historyItemRewardTextView.setText(historyData.getReward() + "円");
    }

    @Override
    public int getItemCount() {
        return historyDataList == null ? 0 : historyDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView historyItemNameTextView;
        TextView historyItemRewardTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyItemNameTextView = itemView.findViewById(R.id.historyItemNameTextView);
            historyItemRewardTextView = itemView.findViewById(R.id.historyItemRewardTextView);
        }
    }
}
