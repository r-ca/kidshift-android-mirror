package one.nem.kidshift.feature.parent;

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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import one.nem.kidshift.utils.models.FabEventCallback;

@AndroidEntryPoint
public class ParentMainFragment extends Fragment {

    @Inject
    KSLoggerFactory ksLoggerFactory;
    @Inject
    TaskData taskData;
    @Inject
    ChildData childData;
    @Inject
    FabManager fabManager;

    private KSLogger logger;

    ParentAdapter parentAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LayoutInflater layoutInflater;

    @SuppressLint("DatasetChange")
    private void updateTaskInfo(){
        swipeRefreshLayout.setRefreshing(true);
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
            parentAdapter.setTaskDataList(taskItemModel);
            requireActivity().runOnUiThread(()->{
                parentAdapter.notifyDataSetChanged();
            });
        }).thenRun(() -> {
           swipeRefreshLayout.setRefreshing(false);
        });
    }

    public ParentMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.logger = ksLoggerFactory.create("ParentMainFragment");
        this.layoutInflater = requireActivity().getLayoutInflater();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //タスク一覧表示
        View view = inflater.inflate(R.layout.fragment_parent_main, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        RecyclerView recyclerView = view.findViewById(R.id.main_recycle_view);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        parentAdapter = new ParentAdapter();
        parentAdapter.setCallback(taskId -> {
            View childListView = layoutInflater.inflate(R.layout.act_child_select_dialog, null);
            RecyclerView childListRecyclerView = childListView.findViewById(R.id.act_recycle_view);
            childListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            childData.getChildListDirect().thenAccept(childModelList -> childListRecyclerView.setAdapter(new TackCompleteDialogChildListAdapter(childModelList))).thenRun(() -> {
                MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(requireContext());
                builder1.setTitle("お手伝いをしたお子様の名前を選択してください")
                        .setView(childListView)
                        .setNeutralButton("閉じる", (dialog, which) -> dialog.dismiss());
                builder1.show();
            }).join();

        });

        recyclerView.setAdapter(parentAdapter);
        updateTaskInfo();

        //Pull-to-refresh（スワイプで更新）
        try {

            swipeRefreshLayout.setOnRefreshListener(() ->{
                updateTaskInfo();
            });
        } catch (Exception e){
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // FABイベント設定
        if (!fabManager.isShown()) fabManager.show();
        fabManager.setFabEventCallback(new FabEventCallback() {
            @Override
            public void onClicked() {
                View childListView = layoutInflater.inflate(R.layout.add_task_list_dialog, null);
                RecyclerView childListRecyclerView = childListView.findViewById(R.id.taskchild);
                childData.getChildListDirect().thenAccept(childModelList ->
                        childListRecyclerView.setAdapter(
                            new AddTaskDialogChildListAdapter(childModelList)))
                        .thenRun(() -> {
                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
                            builder.setTitle("お手伝い名追加")
                                    .setView(childListView)
                                    .setPositiveButton("追加", null)
                                    .setNeutralButton("閉じる", null);
                            builder.show();
                        }).join();
            }

            @Override
            public void onLongClicked() {
                // Do nothing
            }
        });
    }
}