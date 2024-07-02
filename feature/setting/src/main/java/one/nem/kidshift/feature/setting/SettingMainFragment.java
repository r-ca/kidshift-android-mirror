package one.nem.kidshift.feature.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.model.callback.ParentModelCallback;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class SettingMainFragment extends Fragment {

    @Inject
    ChildData childData;

    @Inject
    ParentData parentData;

    @Inject
    KSLoggerFactory ksLoggerFactory;

    private KSLogger logger;

    TextView username;

    TextView userMailAddress;

    SettingAdapter mainAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public SettingMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = ksLoggerFactory.create("SettingMainFragment");
    }

    /**
     * 親情報を更新する
     * @return CompletableFuture<Void>
     */
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
            userMailAddress.setText(parentModel.getEmail() != null ? parentModel.getEmail() : "親のアドレス");
        });

    }

    /**
     * 子供情報を更新する
     * @return CompletableFuture<Void>
     */
    @SuppressLint("NotifyDataSetChanged")
    private CompletableFuture<Void> updateChildInfo(){
        return childData.getChildList(new ChildModelCallback() {
            @Override
            public void onUnchanged() {

            }

            @Override
            public void onUpdated(List<ChildModel> childModelList) {

            }

            @Override
            public void onFailed(String message) {

            }
        }).thenAccept(childModels -> {
            mainAdapter.setChildDataList(childModels);

            requireActivity().runOnUiThread(() -> {
                mainAdapter.notifyDataSetChanged();
            });

        });
    }

    /**
     * ユーザー情報を更新するラッパー
     */
    private void updateInfo() {
        CompletableFuture<Void> updateParent = updateParentInfo();
        CompletableFuture<Void> updateChildList = updateChildInfo();

        logger.debug(String.valueOf((swipeRefreshLayout == null)));

        swipeRefreshLayout.setRefreshing(true);

        logger.debug("アップデート開始");
        updateParent.thenCombine(updateChildList, (res1, res2) -> null).thenRun(() -> {
            logger.debug("アップデート完了");
            swipeRefreshLayout.setRefreshing(false);
        }).exceptionally(e -> {
            logger.error("アップデート失敗: " + e.getMessage());
            swipeRefreshLayout.setRefreshing(false);
            return null;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting_main, container, false);

        // ビューの取得
        username = view.findViewById(R.id.username);
        userMailAddress = view.findViewById(R.id.useradress);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

        // RecyclerViewの設定
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mainAdapter = new SettingAdapter();
        recyclerView.setAdapter(mainAdapter);

        // ユーザー情報の更新(初回)
        updateInfo();

        // スワイプリフレッシュのリスナー
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateInfo(); // ユーザー情報の更新
        });

        // ダイアログの設定
        LayoutInflater inflater1 = requireActivity().getLayoutInflater();
        View view1 = inflater1.inflate(R.layout.add_child_list_dialog,null);

        //子供の名前追加のダイアログ
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("お子様の名前を入力してください。")
                .setView(view1)
                .setPositiveButton("追加",null)
                .setNeutralButton("閉じる",null);
        builder.create();

        // ダイアログの表示
        view.findViewById(R.id.addchildname).setOnClickListener(v -> {
            builder.show();
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 更新する?
    }
}