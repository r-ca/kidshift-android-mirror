package one.nem.kidshift.feature.setting;

import android.inputmethodservice.Keyboard;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.nem.kidshift.model.ChildModel;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MainViewHolder> {

    public interface LoginButtonCallback {
        void onLoginButtonClicked(String childId);
    }

    private List<ChildModel> childDataList;

    private LoginButtonCallback loginButtonCallback;

    SettingAdapter() {

    }

    public void setChildDataList(List<ChildModel> childDataList) {
        this.childDataList = childDataList;
    }

    public void setLoginButtonCallback(LoginButtonCallback loginButtonCallback) {
        this.loginButtonCallback = loginButtonCallback;
    }


    static class MainViewHolder extends RecyclerView.ViewHolder{
        TextView childname;
        Button loginButton;

        MainViewHolder(@NonNull View itemView) {
            super(itemView);
            childname = itemView.findViewById(R.id.childname);
            loginButton = itemView.findViewById(R.id.loginButton);
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_child_name_list,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position){
        ChildModel childData = this.childDataList.get(position);
        holder.childname.setText(childData.getName());
        holder.loginButton.setOnClickListener(v -> {
            if (this.loginButtonCallback != null) {
                this.loginButtonCallback.onLoginButtonClicked(childData.getId());
            }
        });
    }

    @Override
    public int getItemCount(){
        return childDataList != null ? childDataList.size() : 0;
    }
}
