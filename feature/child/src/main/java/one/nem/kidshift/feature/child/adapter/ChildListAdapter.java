package one.nem.kidshift.feature.child.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.model.ChildModel;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder>{

    private List<ChildModel> childList;
    private ButtonEventCallback buttonEventCallback;

    public ChildListAdapter() {
    }

    public void setChildList(List<ChildModel> childList) {
        this.childList = childList;
    }

    public void setButtonEventCallback(ButtonEventCallback buttonEventCallback) {
        this.buttonEventCallback = buttonEventCallback;
    }

    @NonNull
    @Override
    public ChildListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChildListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return childList == null ? 0 : childList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface ButtonEventCallback {
        void onEditButtonClick(ChildModel childModel);
        void onLoginButtonClick(ChildModel childModel);
    }
}
