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
import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.room.utils.CacheWrapper;
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

    @Inject
    CacheWrapper cacheWrapper;

    @Inject
    UserSettings userSettings;

    @Inject
    KSActions ksActions;


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
                mainAdapter.notifyItemRangeChanged(0, childModels.size());
            });
        });
    }

    /**
     * ユーザー情報を更新するラッパー
     */
    private CompletableFuture<Void> updateInfo() {
        CompletableFuture<Void> updateParent = updateParentInfo();
        CompletableFuture<Void> updateChildList = updateChildInfo();

//        swipeRefreshLayout.setRefreshing(true);

        logger.debug("アップデート開始");
        return updateParent.thenCombine(updateChildList, (res1, res2) -> null)
                .thenRunAsync(() -> {
//                    requireActivity().runOnUiThread(() -> {
//                        logger.debug("アップデート完了");
//                        swipeRefreshLayout.setRefreshing(false);
//                    });
                }).exceptionally(e -> {
//                    requireActivity().runOnUiThread(() -> {
//                        logger.error("アップデート失敗: " + e.getMessage());
//                        swipeRefreshLayout.setRefreshing(false);
//                    });
                    return null;
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        logger.debug("Point 1");

        View view = inflater.inflate(R.layout.fragment_setting_main, container, false);

        username = view.findViewById(R.id.username);
        userMailAddress = view.findViewById(R.id.useradress);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

        // RecyclerViewの設定
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mainAdapter = new SettingAdapter();
        recyclerView.setAdapter(mainAdapter);

//        updateInfo().thenRun(() -> {
//            logger.debug("ユーザー情報の更新完了");
//        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

//        ksActions.syncChildList().thenAcceptAsync(childList -> {
//            mainAdapter.setChildDataList(childList);
//            requireActivity().runOnUiThread(() -> {
//                mainAdapter.notifyDataSetChanged();
//            });
//        });

        cacheWrapper.getChildList().thenAcceptAsync(childList -> {
            mainAdapter.setChildDataList(childList);
            requireActivity().runOnUiThread(() -> {
//                mainAdapter.notifyDataSetChanged();
            });
        });

        CompletableFuture.supplyAsync(() -> userSettings.getCache().getParent()).thenAcceptAsync(parentModel -> {
            requireActivity().runOnUiThread(() -> {
                username.setText(parentModel.getName() != null ? parentModel.getName() : "親の名前");
                userMailAddress.setText(parentModel.getEmail() != null ? parentModel.getEmail() : "親のアドレス");
            });
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        // TODO: 更新する?
    }
}
