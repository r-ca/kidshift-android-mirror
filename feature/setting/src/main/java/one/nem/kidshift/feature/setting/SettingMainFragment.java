package one.nem.kidshift.feature.setting;

import android.annotation.SuppressLint;
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
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ParentModelCallback;
import one.nem.kidshift.utils.KSLogger;

@AndroidEntryPoint
public class SettingMainFragment extends Fragment {

    @Inject
    ChildData childData;

    @Inject
    ParentData parentData;

    @Inject
    KSLogger logger;

    TextView username;

    TextView useradress;

    SettingAdapter mainAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public SettingMainFragment() {
        // Required empty public constructor
    }

    private CompletableFuture<Void> updateParentInfo(){
        return parentData.getParent(new ParentModelCallback() {
            @Override
            public void onUnchanged() {

            }

            @Override
            public void onUpdated(ParentModel parent) {

            }

            @Override
            public void onFailed(String message) {

            }
        }).thenAccept(parentModel -> {
            username.setText(parentModel.getName() != null ? parentModel.getName() : "親の名前");
            useradress.setText(parentModel.getEmail() != null ? parentModel.getEmail() : "親のアドレス");
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private CompletableFuture<Void> updateChildInfo(){
        return childData.getChildList().thenAccept(childModels -> {
            mainAdapter.setChildDataList(childModels);

            requireActivity().runOnUiThread(() -> {
                mainAdapter.notifyDataSetChanged();
            });

        });
    }

    private void updateInfo() {
        CompletableFuture<Void> updateParent = updateParentInfo();
        CompletableFuture<Void> updateChildList = updateChildInfo();

        logger.debug(String.valueOf((swipeRefreshLayout == null)));

        swipeRefreshLayout.setRefreshing(true);

        logger.debug("アップデート開始");
        updateParent.thenCombine(updateChildList, (res1, res2) -> null).thenRun(() -> {
            logger.debug("アップデート完了");
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View view = inflater.inflate(R.layout.fragment_setting_main, container, false);

            username = view.findViewById(R.id.username);
            useradress = view.findViewById(R.id.useradress);

            RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

            // Pull-to-refresh（スワイプで更新）
            swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);


        recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

//            List<ChildModel> child = childData.getChildList().join();

            mainAdapter = new SettingAdapter();
            recyclerView.setAdapter(mainAdapter);


            try {

            /*
            TODO:
                - コールバックの処理を実装
                - 結果に応じてRecyclerViewを更新する
                - キャッシュ受け取りの時にjoinでUIスレッドをブロックしないように
                - Placeholderの表示?
                - エラーハンドリング try catch文
                    - onFailed時にそれを通知
             */

//                updateParentInfo();
//                updateChildInfo();

                updateInfo();

            swipeRefreshLayout.setOnRefreshListener(() ->{

//                updateParentInfo();
//
//                updateChildInfo();

                updateInfo();
//
//                swipeRefreshLayout.setRefreshing(false);

            });

    //            username.setText(parent.getName());
    //            useradress.setText(parent.getEmail());
            } catch (Exception e) {

            }

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