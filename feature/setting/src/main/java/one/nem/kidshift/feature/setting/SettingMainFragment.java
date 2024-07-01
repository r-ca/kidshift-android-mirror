package one.nem.kidshift.feature.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ParentModelCallback;

@AndroidEntryPoint
public class SettingMainFragment extends Fragment {

    @Inject
    ChildData childData;

    @Inject
    ParentData parentData;

    public SettingMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting_main, container, false);

        TextView username = view.findViewById(R.id.username);
        TextView useradress = view.findViewById(R.id.useradress);

        try {
        CompletableFuture<ParentModel> completableFuture = parentData.getParent(new ParentModelCallback() {

            @Override
            public void onUnchanged() {
                // TODO
                //かわってないとき
            }

            @Override
            public void onUpdated(ParentModel parent) {
                // TODO
                //変わってたら
                requireActivity().runOnUiThread(() -> {
                    username.setText(parent.getName());
                    useradress.setText(parent.getEmail());
                });
            }

            @Override
            public void onFailed(String message) {
                // TODO
                requireActivity().runOnUiThread(()->{
                    username.setText("アドレスを設定してください。");
                });

            }
        });


        /*
        TODO:
            - コールバックの処理を実装
            - 結果に応じてRecyclerViewを更新する
            - キャッシュ受け取りの時にjoinでUIスレッドをブロックしないように
            - Placeholderの表示?
            - エラーハンドリング try catch文
                - onFailed時にそれを通知
         */


        ParentModel parent = completableFuture.join();

        if (parent == null) {
            parent = new ParentModel(); // Workaround（非ログインデバッグ用）
            parent.setName("親の名前");
            parent.setEmail("親のアドレス");
        }

        // Pull-to-refresh（スワイプで更新）
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        ParentModel finalParent = parent;
        swipeRefreshLayout.setOnRefreshListener(() ->{

            username.setText(finalParent.getName());
            useradress.setText(finalParent.getEmail());

            RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            List<ChildModel> child = childData.getChildList().join();

            RecyclerView.Adapter mainAdapter = new SettingAdapter(child);
            recyclerView.setAdapter(mainAdapter);

            swipeRefreshLayout.setRefreshing(false);

        });

            username.setText(parent.getName());
            useradress.setText(parent.getEmail());
        } catch (Exception e) {
            //
        }
            RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            List<ChildModel> child = childData.getChildList().join();

            RecyclerView.Adapter mainAdapter = new SettingAdapter(child);
            recyclerView.setAdapter(mainAdapter);

            LayoutInflater inflater1 = requireActivity().getLayoutInflater();
            View view1 = inflater1.inflate(R.layout.add_child_list_dialog,null);

            //子供の名前追加のダイアログ
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setTitle("お子様の名前を入力してください。")
                    .setView(view1)
                    .setPositiveButton("追加",null)
                    .setNeutralButton("閉じる",null);
            builder.create();

        view.findViewById(R.id.addchildname).setOnClickListener(v -> {
            builder.show();
        });


        return view;
    }
}