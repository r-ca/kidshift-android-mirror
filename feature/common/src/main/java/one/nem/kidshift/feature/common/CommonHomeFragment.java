package one.nem.kidshift.feature.common;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.feature.common.adapter.ChildListItemAdapter;
import one.nem.kidshift.feature.common.adapter.TaskListItemAdapter;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import one.nem.kidshift.utils.models.FabEventCallback;

@AndroidEntryPoint
public class CommonHomeFragment extends Fragment {

    private static final String ARG_IS_CHILD_MODE = "isChildMode";
    private static final String ARG_CHILD_ID = "childId";

    @Inject
    KSLoggerFactory ksLoggerFactory;
    @Inject
    TaskData taskData;
    @Inject
    ChildData childData;
    @Inject
    FabManager fabManager;
    @Inject
    RewardData rewardData;


    private boolean isChildMode;
    private String childId;
    private KSLogger logger;

    CompactCalendarView compactCalendarView;
    SwipeRefreshLayout swipeRefreshLayout;
    TaskListItemAdapter taskListItemAdapter;

    public CommonHomeFragment() {
        // Required empty public constructor
    }

    // TODO: SwipeToRef

    public static CommonHomeFragment newInstance(boolean isChildMode, String childId) {
        CommonHomeFragment fragment = new CommonHomeFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_CHILD_MODE, isChildMode);
        if (isChildMode) {
            args.putString(ARG_CHILD_ID, childId);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChildMode = getArguments().getBoolean(ARG_IS_CHILD_MODE);
            childId = getArguments().getString(ARG_CHILD_ID);
        }
        logger = ksLoggerFactory.create("CommonHomeFragment");

        if (isChildMode) {
            logger.info("Child mode, childId: " + childId);
        } else {
            logger.info("Parent mode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_home, container, false);

        compactCalendarView = view.findViewById(R.id.calendar);
        taskListItemAdapter = new TaskListItemAdapter();
        taskListItemAdapter.setCallback((taskId, taskName) -> {
            if (isChildMode) {
                if (showConfirmDialog(taskName)) {
                    taskData.recordTaskCompletion(taskId, childId);
                }
            } else {
                showChildSelectDialog(taskId, taskName);
            }
        });

        RecyclerView taskListRecyclerView = view.findViewById(R.id.taskListRecyclerView);
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskListRecyclerView.setAdapter(taskListItemAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::updateData);

        initCalender();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
        if (isChildMode) {
            setupFabChild();
        } else {
            setupFabParent();
        }
    }

    /**
     * 親モードの場合(=子供モードではない場合)のFABの設定
     */
    private void setupFabParent() {
        fabManager.show();
        fabManager.setFabEventCallback(new FabEventCallback() {
            @Override
            public void onClicked() {
                showAddTaskDialog();
            }

            @Override
            public void onLongClicked() {
                // Do nothing
            }
        });
    }

    /**
     * 子供モードの場合のFABの設定
     */
    private void setupFabChild() {
        fabManager.hide();
    }

    /**
     * タスク完了確認ダイアログを表示 (子供モード用)
     *
     * @param taskName タスク名
     * @return OKボタンが押されたかどうか
     */
    private boolean showConfirmDialog(String taskName) {
        AtomicBoolean selection = new AtomicBoolean(false);
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("タスクを完了しますか？")
                .setMessage(taskName + "を完了しますか？")
                .setPositiveButton("はい", (dialog, which) -> {
                    dialog.dismiss();
                    selection.set(true);
                })
                .setNegativeButton("いいえ", (dialog, which) -> {
                    dialog.dismiss();
                    selection.set(false);
                })
                .show();
        return selection.get();
    }

    /**
     * タスク完了ダイアログ(子供選択画面)を表示 (親モード用)
     *
     * @param taskId   タスクID
     * @param taskName タスク名
     */
    private void showChildSelectDialog(String taskId, String taskName) { // TODO: Assignされている子供かどうかを考慮するように
        RecyclerView childListRecyclerView = new RecyclerView(requireContext());
        childListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        childListRecyclerView.setPadding(0, 48, 0, 0);
        // TODO: キャッシュから取得する方にする？
        childData.getChildListDirect().thenAccept(childModelList -> {
            ChildListItemAdapter childListItemAdapter = new ChildListItemAdapter(childModelList);
            childListItemAdapter.setCallback(childId -> {
                taskData.recordTaskCompletion(taskId, childId);
            });
            childListRecyclerView.setAdapter(childListItemAdapter);
        }).thenRun(() -> {
            requireActivity().runOnUiThread(() -> {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(taskName + "を完了したお子様を選択")
                        .setView(childListRecyclerView)
                        .setNeutralButton("閉じる", (dialog, which) -> dialog.dismiss())
                        .show();
            });
        });
    }

    /**
     * タスク情報を更新
     * @return CompletableFuture<Void>
     */
    @SuppressLint("NotifyDataSetChanged")
    private CompletableFuture<Void> updateTaskInfo() { // TODO: updatedの場合の処理など実装
        return taskData.getTasks(new TaskItemModelCallback() {
            @Override
            public void onUnchanged() {
            }

            @Override
            public void onUpdated(List<TaskItemModel> taskItem) {
            }

            @Override
            public void onFailed(String message) {
            }
        }).thenAccept(taskItemModel -> {
            taskListItemAdapter.setTaskItemModelList(taskItemModel);
            requireActivity().runOnUiThread(() -> {
                taskListItemAdapter.notifyDataSetChanged();
            });
        });
    }

    /**
     * カレンダーを更新
     * @return CompletableFuture<Void>
     */
    private CompletableFuture<Void> updateCalender() {
        return rewardData.getRewardHistoryList().thenAccept(historyModels -> {
            historyModels.forEach(historyModel -> {
                compactCalendarView.addEvent(new Event(Color.RED, historyModel.getRegisteredAt().getTime(), historyModel.getTaskName())); // debug
            });
        });
    }

    private void initCalender() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date date) {
                Toast.makeText(requireContext(), "Day was clicked: " + date, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date date) {
                // Do nothing
            }
        });
    }

    /**
     * データを更新 (updateTaskInfoとupdateCalenderを並列実行)
     */
    private void updateData() {
        swipeRefreshLayout.setRefreshing(true);
        CompletableFuture.allOf(updateTaskInfo(), updateCalender()).thenRun(() -> {
            // Workaround: リスト更新処理があまりにも重くてアニメーションが壊れるため
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // do nothing
            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    /**
     * タスク追加ダイアログを表示
     */
    private void showAddTaskDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Placeholder")
                .setMessage("Placeholder")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
