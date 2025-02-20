package one.nem.kidshift.feature.common;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.feature.common.adapter.ChildListItemAdapter;
import one.nem.kidshift.feature.common.adapter.TaskListItemAdapter;
import one.nem.kidshift.model.HistoryModel;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.RecyclerViewAnimUtils;
import one.nem.kidshift.utils.ToolBarManager;
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
    ToolBarManager toolBarManager;
    @Inject
    RewardData rewardData;
    @Inject
    RecyclerViewAnimUtils recyclerViewAnimUtils;
    @Inject
    UserSettings userSettings;


    private boolean isChildMode;
    private String childId;
    private KSLogger logger;

    CompactCalendarView compactCalendarView;
    View calendarContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    TaskListItemAdapter taskListItemAdapter;
    TextView calendarTitleTextView;
    ImageButton calendarPrevButton;
    ImageButton calendarNextButton;

    public CommonHomeFragment() {
        // Required empty public constructor
    }

    // TODO: SwipeToRef

    public static CommonHomeFragment newInstance(boolean isChildMode, String childId) {
        CommonHomeFragment fragment = new CommonHomeFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_CHILD_MODE, isChildMode);
        args.putString(ARG_CHILD_ID, childId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger = ksLoggerFactory.create("CommonHomeFragment");

        if (userSettings.getAppCommonSetting().isChildMode()) {
            logger.debug("子供モードで起動(子供ログイン)");
            isChildMode = true;
            childId = userSettings.getAppCommonSetting().getChildId();
            logger.debug("childId: " + childId);
        } else {
            if (getArguments() != null) {
                isChildMode = getArguments().getBoolean(ARG_IS_CHILD_MODE) && getArguments().getBoolean(ARG_IS_CHILD_MODE);
                childId = getArguments().getString(ARG_CHILD_ID) != null ? getArguments().getString(ARG_CHILD_ID) : "";
            }

            if (childId != null && !childId.isEmpty()) {
                isChildMode = true;
            }

            if (isChildMode) {
                logger.debug("子供モードで起動");
                logger.debug("childId: " + childId);
            } else {
                logger.debug("保護者モードで起動");
            }
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
                showConfirmDialog(taskId, taskName);
            } else {
                showChildSelectDialog(taskId, taskName);
            }
        });

        RecyclerView taskListRecyclerView = view.findViewById(R.id.taskListRecyclerView);
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskListRecyclerView.setAdapter(taskListItemAdapter);
        taskListRecyclerView.setItemViewCacheSize(10);
        recyclerViewAnimUtils.setSlideUpAnimation(taskListRecyclerView);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::updateData);

        calendarTitleTextView = view.findViewById(R.id.calendarTitleTextView);
        calendarPrevButton = view.findViewById(R.id.calendarPrevButton);
        calendarNextButton = view.findViewById(R.id.calendarNextButton);
        calendarContainer = view.findViewById(R.id.calendarContainer);

        initCalender();
        updateData();

        return view;
    }

    private void recyclerViewRefresh() {
        requireActivity().runOnUiThread(() -> {
            taskListItemAdapter.notifyItemRangeRemoved(0, taskListItemAdapter.getItemCount());
            taskListItemAdapter.notifyItemRangeInserted(0, taskListItemAdapter.getItemCount());
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isChildMode) {
            setupFabChild();
        } else {
            setupFabParent();
        }
        setupToolBar();
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

    private void setupToolBar() {
        if (isChildMode) {
            toolBarManager.setTitle("ホーム");
            toolBarManager.setSubtitle("子供ビュー");
        } else {
            toolBarManager.setTitle("ホーム");
            toolBarManager.setSubtitle("保護者ビュー");
        }
        MenuHost menuHost = requireActivity();

        menuHost.invalidateMenu();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                logger.debug("onCreateMenu, インフレート");
                menu.clear();
                menuInflater.inflate(R.menu.common_home_toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.toggle_calendar) {
                    if (calendarContainer.getVisibility() == View.VISIBLE) {
                        Animation slideUp = AnimationUtils.loadAnimation(getContext(), one.nem.kidshift.shared.R.anim.slide_up);
                        calendarContainer.startAnimation(slideUp);
                        slideUp.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                recyclerViewRefresh();
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                calendarContainer.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                    } else {
                        Animation slideDown = AnimationUtils.loadAnimation(getContext(), one.nem.kidshift.shared.R.anim.slide_down);
                        calendarContainer.startAnimation(slideDown);
                        slideDown.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                calendarContainer.setVisibility(View.VISIBLE);
                                recyclerViewRefresh();
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        }, this.getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }


    /**
     * タスク完了確認ダイアログを表示 (子供モード用)
     *
     * @param taskId   タスクID
     * @param taskName タスク名
     */
    private void showConfirmDialog(String taskId, String taskName) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("タスクを完了しますか？")
                .setMessage(taskName + "を完了しますか？")
                .setPositiveButton("はい", (dialog, which) -> {
                    dialog.dismiss();
                    taskData.recordTaskCompletion(taskId, childId);
                })
                .setNegativeButton("いいえ", (dialog, which) -> {
                    dialog.dismiss();
                    taskData.recordTaskCompletion(taskId, childId);
                })
                .show();
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
                // Do nothing
            }

            @Override
            public void onUpdated(List<TaskItemModel> taskItem) { // Workaround
                taskListItemAdapter.notifyItemRangeRemoved(0, taskListItemAdapter.getItemCount());
                taskListItemAdapter.setTaskItemModelList(taskItem);
                taskListItemAdapter.notifyItemRangeInserted(0, taskItem.size());
            }
            @Override
            public void onFailed(String message) {
                // TODO: ユーザーに丁寧に通知
                Toast.makeText(requireContext(), "タスク情報の取得に失敗しました", Toast.LENGTH_SHORT).show(); // Workaround
            }
        }).thenAccept(taskItemModel -> {
            requireActivity().runOnUiThread(() -> {
//                taskListItemAdapter.notifyItemRangeRemoved(0, taskListItemAdapter.getItemCount());
//                taskListItemAdapter.setTaskItemModelList(taskItemModel);
//                taskListItemAdapter.notifyItemRangeInserted(0, taskItemModel.size());
                taskListItemAdapter.setTaskItemModelList(taskItemModel);
                taskListItemAdapter.notifyItemRangeChanged(0, taskItemModel.size());
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
                compactCalendarView.addEvent(new Event(Color.RED, historyModel.getRegisteredAt().getTime(), historyModel)); // debug
            });
        });
    }

    private void initCalender() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date date) { // Test
                List<Event> events = compactCalendarView.getEvents(date);

                ScrollView scrollView = new ScrollView(requireContext());
                LinearLayout linearLayout = new LinearLayout(requireContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(96, 24, 96, 24);
                scrollView.addView(linearLayout);

                events.forEach(event -> {
                    TextView textView = new TextView(requireContext());
                    textView.setText(((HistoryModel) event.getData()).getTaskName() + " @ " + ((HistoryModel) event.getData()).getChildId());
                    textView.setPadding(0, 0, 0, 24);
                    linearLayout.addView(textView);
                });

                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("タスク一覧 (DEBUG)")
                        .setMessage(events.size() + "件のタスクが登録されています")
                        .setView(scrollView)
                        .setNeutralButton("閉じる", (dialog, which) -> dialog.dismiss())
                        .show();
            }

            @Override
            public void onMonthScroll(Date date) {
                // 0000年00月の形式に変換 getYear/getMonthは非推奨
                calendarTitleTextView.setText(String.format("%d年%d月", date.getYear() + 1900, date.getMonth() + 1)); // 統合
            }
        });

        // 初回
        Date date = new Date();
        calendarTitleTextView.setText(String.format("%d年%d月", date.getYear() + 1900, date.getMonth() + 1)); // 統合

        calendarPrevButton.setOnClickListener(v -> {
            compactCalendarView.scrollLeft();
        });

        calendarNextButton.setOnClickListener(v -> {
            compactCalendarView.scrollRight();
        });
    }

    /**
     * データを更新 (updateTaskInfoとupdateCalenderを並列実行)
     */
    private void updateData() {
        requireActivity().runOnUiThread(() -> {
            swipeRefreshLayout.setRefreshing(true);
        });
        CompletableFuture.allOf(updateTaskInfo(), updateCalender()).thenRun(() -> {
            requireActivity().runOnUiThread(() -> {
                swipeRefreshLayout.setRefreshing(false);
            });
        });
    }

    /**
     * タスク追加ダイアログを表示
     */
    private void showAddTaskDialog() {
        View view = getLayoutInflater().inflate(R.layout.common_task_add_dialog_layout, null);
        view.setPadding(48, 24, 48, 24);
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("タスクを追加")
                .setView(view)
                .setPositiveButton("追加", (dialog, which) -> {
                    EditText taskNameEditText = view.findViewById(R.id.addTaskNameEditText);
                    EditText taskRewardEditText = view.findViewById(R.id.addTaskRewardEditText);
                    TaskItemModel taskItemModel = new TaskItemModel();
                    taskItemModel.setName(taskNameEditText.getText().toString());
                    taskItemModel.setReward(Integer.parseInt(taskRewardEditText.getText().toString()));
                    taskData.addTask(taskItemModel).thenRun(this::updateData);
                })
                .setNegativeButton("キャンセル", (dialog, which) -> dialog.dismiss())
                .show();

    }
}
