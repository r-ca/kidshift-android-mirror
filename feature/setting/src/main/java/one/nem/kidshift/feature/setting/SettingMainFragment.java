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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.model.callback.ParentModelCallback;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import one.nem.kidshift.utils.models.FabEventCallback;

@AndroidEntryPoint
public class SettingMainFragment extends Fragment {

    @Inject
    ChildData childData;

    @Inject
    ParentData parentData;

    @Inject
    KSLoggerFactory ksLoggerFactory;

    @Inject
    FabManager fabManager;

    private KSLogger logger;

    TextView username;

    TextView userMailAddress;

    SettingAdapter mainAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LayoutInflater dialogInflater;



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
     *
     * @return CompletableFuture<Void>
     */
    private CompletableFuture<Void> updateParentInfo() {
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
     *
     * @return CompletableFuture<Void>
     */
    @SuppressLint("NotifyDataSetChanged")
    private CompletableFuture<Void> updateChildInfo() {
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
    private CompletableFuture<Void> updateInfo() {
        CompletableFuture<Void> updateParent = updateParentInfo();
        CompletableFuture<Void> updateChildList = updateChildInfo();

        swipeRefreshLayout.setRefreshing(true);

        return CompletableFuture.allOf(updateParent, updateChildList).thenRun(() -> {
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting_main, container, false);

        // ビューの取得
        username = view.findViewById(R.id.username);
        userMailAddress = view.findViewById(R.id.useradress);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mainAdapter = new SettingAdapter();

        // RecyclerViewの設定
        RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mainAdapter);

        // ユーザー情報の更新(初回)
        updateInfo().thenRunAsync(() -> {
            logger.debug("ユーザー情報の更新完了");
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.dialogInflater = requireActivity().getLayoutInflater();


        // FABイベント設定
        fabManager.setFabEventCallback(new FabEventCallback() {
            @Override
            public void onClicked() {
                //子供の名前追加のダイアログ
                View dialogView = dialogInflater.inflate(R.layout.add_child_list_dialog, null);
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("お子様の名前を入力してください。")
                        .setView(dialogView)
                        .setPositiveButton("追加", (dialog, which) -> {
                            ChildModel childModel = new ChildModel();
                            childModel.setName(Objects.requireNonNull(((TextView) dialogView.findViewById(R.id.childNameEditText)).getText()).toString());
                            childData.addChild(childModel).thenAccept(childModel1 -> { // Debug
                                logger.debug("子供を追加しました: " + childModel1.getName());
                            }).thenRun(() -> {
                                updateChildInfo();
                            });
                        })
                        .setNeutralButton("閉じる", (dialog, which) -> {
                            dialog.cancel();
                        }).show();
            }

            @Override
            public void onLongClicked() {
                // Do nothing
            }
        });

        // ログインコード発行ボタンのコールバック
        mainAdapter.setLoginButtonCallback(childId -> {
            View addChildDialogView = dialogInflater.inflate(R.layout.add_child_list_dialog, null);
            ((TextView) addChildDialogView.findViewById(R.id.childNameEditText))
                    .setText(childData.issueLoginCode(childId).join());
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
            builder.setTitle("ログインコード")
                    .setView(addChildDialogView)
                    .setNeutralButton("閉じる", null);
            builder.show();
        });

        // スワイプリフレッシュのリスナー
        // ユーザー情報の更新
        swipeRefreshLayout.setOnRefreshListener(this::updateInfo);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 更新する?
    }
}