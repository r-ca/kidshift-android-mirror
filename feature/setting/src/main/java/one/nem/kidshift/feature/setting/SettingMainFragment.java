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
        }).thenAcceptAsync(parentModel -> {
            try {
                Thread.sleep(5000);  // デバッグのための遅延、実際のアプリでは削除
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requireActivity().runOnUiThread(() -> {
                username.setText(parentModel.getName() != null ? parentModel.getName() : "親の名前");
                userMailAddress.setText(parentModel.getEmail() != null ? parentModel.getEmail() : "親のアドレス");
            });
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
        }).thenAcceptAsync(childModels -> {
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

        logger.debug(String.valueOf((swipeRefreshLayout == null)));

        swipeRefreshLayout.setRefreshing(true);

        logger.debug("アップデート開始");
        return updateParent.thenCombineAsync(updateChildList, (res1, res2) -> null)
                .thenRunAsync(() -> {
                    requireActivity().runOnUiThread(() -> {
                        logger.debug("アップデート完了");
                        swipeRefreshLayout.setRefreshing(false);
                    });
                }).exceptionally(e -> {
                    requireActivity().runOnUiThread(() -> {
                        logger.error("アップデート失敗: " + e.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    });
                    return null;
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        logger.debug("Point 1");

        View view = inflater.inflate(R.layout.fragment_setting_main, container, false);

        logger.debug("Point 2");

        // ビューの取得
        username = view.findViewById(R.id.username);
        userMailAddress = view.findViewById(R.id.useradress);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

        logger.debug("Point 3");

        // RecyclerViewの設定
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mainAdapter = new SettingAdapter();
        recyclerView.setAdapter(mainAdapter);

        logger.debug("Point 4");

        // ユーザー情報の更新(初回)
        updateInfo().thenRun(() -> {
            logger.debug("ユーザー情報の更新完了");
        });

        logger.debug("Point 5");

        // スワイプリフレッシュのリスナー
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateInfo(); // ユーザー情報の更新
        });

        logger.debug("Point 6");

        // ダイアログの設定
        LayoutInflater dialogInflater = requireActivity().getLayoutInflater();

        View addChildDialogView = dialogInflater.inflate(R.layout.fragment_login_dialog_view, null);

        View childListItemView = inflater.inflate(R.layout.list_item_child_name_list, container, false);

        logger.debug("Point 7");

        mainAdapter.setLoginButtonCallback(new SettingAdapter.LoginButtonCallback() {
            @Override
            public void onLoginButtonClicked(String childId) {
//                Toast.makeText(getContext(), "ボタンがクリックされました(" + childId + ")", Toast.LENGTH_LONG).show();
                int loginCode = childData.issueLoginCode(childId).join();
                TextView loginCodeTextView = addChildDialogView.findViewById(R.id.loginCode);
                new StringBuilder(Integer.toString(loginCode)).insert(3, "-");

                loginCodeTextView.setText(
                        new StringBuilder(Integer.toString(loginCode)).insert(3, "-")
                );

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
                builder.setTitle("ログインコード")
                        .setView(addChildDialogView)
                        .setNeutralButton("閉じる", null);
                builder.create();

//                childListItemView.findViewById(R.id.loginButton).setOnClickListener(v -> {
                builder.show();
//                });
            }
        });

        logger.debug("Point 8");

//        int loginCode = childData.issueLoginCode("543256").join();
//        TextView loginCodeTextView = addChildDialogView.findViewById(R.id.loginCode);
//        new StringBuilder(Integer.toString(loginCode)).insert(3,"-");
//
//        loginCodeTextView.setText(
//                new StringBuilder(Integer.toString(loginCode)).insert(3,"-"));

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("ログインコード")
                .setView(addChildDialogView)
                .setNeutralButton("閉じる", null);
        builder.create();
//

        logger.debug("Point 9");
//        childListItemView.findViewById(R.id.loginButton).setOnClickListener(v -> {
//            builder.show();
//        });


        logger.debug("Point 10");

        // ダイアログの表示

        if (!fabManager.isShown()) fabManager.show();

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

        logger.debug("Point 11");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 更新する?
    }
}
