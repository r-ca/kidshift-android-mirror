package one.nem.kidshift.feature.common;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.feature.common.adapter.TaskListItemAdapter;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class CommonHomeFragment extends Fragment {

    private static final String ARG_IS_CHILD_MODE = "isChildMode";

    @Inject
    KSLoggerFactory ksLoggerFactory;
    @Inject
    TaskData taskData;
    @Inject
    ChildData childData;

    private boolean isChildMode;
    private KSLogger logger;

    CompactCalendarView compactCalendarView;
    TaskListItemAdapter taskListItemAdapter;

    public CommonHomeFragment() {
        // Required empty public constructor
    }

    public static CommonHomeFragment newInstance(boolean isChildMode) {
        CommonHomeFragment fragment = new CommonHomeFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_CHILD_MODE, isChildMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChildMode = getArguments().getBoolean(ARG_IS_CHILD_MODE);
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
                // 確認ダイアログ呼び出し
            } else {
                // 子供選択ダイアログ呼び出し
            }
        });

        return view;
    }

    private boolean showConfirmDialog(String taskName) {
        // 確認ダイアログ表示
        return false;
    }

    private void showChildSelectDialog() {
        // 子供選択ダイアログ表示
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateTaskInfo() {
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

    }
}
