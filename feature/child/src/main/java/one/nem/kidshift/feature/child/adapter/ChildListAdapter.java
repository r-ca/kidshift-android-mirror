package one.nem.kidshift.feature.child.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.feature.child.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_child_manage_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildListAdapter.ViewHolder holder, int position) {
        ChildModel childModel = childList.get(position);
        holder.childNameTextView.setText(childModel.getName());
        holder.editButton.setOnClickListener(v -> {
            if (buttonEventCallback != null) {
                buttonEventCallback.onEditButtonClick(childModel);
            }
        });
        holder.loginButton.setOnClickListener(v -> {
            if (buttonEventCallback != null) {
                buttonEventCallback.onLoginButtonClick(childModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return childList == null ? 0 : childList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView childNameTextView;
        Button editButton;
        Button loginButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childNameTextView = itemView.findViewById(R.id.childNameTextView);
            editButton = itemView.findViewById(R.id.editButton);
            loginButton = itemView.findViewById(R.id.loginButton);
        }
    }

    public interface ButtonEventCallback {
        void onEditButtonClick(ChildModel childModel);
        void onLoginButtonClick(ChildModel childModel);
    }
}
