package one.nem.kidshift.feature.common;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.feature.common.adapter.ChildListItemAdapter;
import one.nem.kidshift.feature.common.adapter.TaskListItemAdapter;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

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

    private boolean isChildMode;
    private String childId;
    private KSLogger logger;

    CompactCalendarView compactCalendarView;
    TaskListItemAdapter taskListItemAdapter;

    public CommonHomeFragment() {
        // Required empty public constructor
    }

    // TODO: SwipeToRefreshの実装

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
                showChildSelectDialog(taskId);
            }
        });

        return view;
    }

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

    private void showChildSelectDialog(String taskId) { // TODO: Assignされている子供かどうかを考慮するように
        RecyclerView childListRecyclerView = new RecyclerView(requireContext());
        childListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        childData.getChildListDirect().thenAccept(childModelList -> {
            ChildListItemAdapter childListItemAdapter = new ChildListItemAdapter();
            childListRecyclerView.setAdapter(childListItemAdapter);
            childListItemAdapter.setCallback(childId -> {
                taskData.recordTaskCompletion(taskId, childId);
            });
        });

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("お手伝いをしたお子様を選択")
                .setView(childListRecyclerView)
                .setNeutralButton("閉じる", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTaskInfo() { // TODO: updatedの場合の処理など実装
        taskData.getTasks(new TaskItemModelCallback() {
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

    private void updateCalender() {
        // TODO: タスクの完了状況をカレンダーに表示
    }
}
