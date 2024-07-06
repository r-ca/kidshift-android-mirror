package one.nem.kidshift.feature.parent;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class ParentMainFragment extends Fragment {

    @Inject
    KSLoggerFactory ksLoggerFactory;
    @Inject
    TaskData taskData;
    @Inject
    ChildData childData;

    private KSLogger logger;

    ParentAdapter parentAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

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
        parentAdapter.setCallback(new ParentAdapter.CompleteButtonClickedCallback() {
            @Override
            public void onClicked(String taskId) {
                Toast.makeText(requireContext(), "TaskID: " + taskId, Toast.LENGTH_LONG).show();
                //お手伝い完了処理
                LayoutInflater inflater2 = requireActivity().getLayoutInflater();
                View view2 = inflater2.inflate(R.layout.act_child_select_dialog,null);

                //子供一覧表示
                RecyclerView recyclerView2 = view2.findViewById(R.id.act_recycle_view);

                recyclerView2.setHasFixedSize(true);

                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext());
                recyclerView2.setLayoutManager(layoutManager2);

                List<ChildModel> child1 = childData.getChildList(new ChildModelCallback() {
                    @Override
                    public void onUnchanged() {

                    }

                    @Override
                    public void onUpdated(List<ChildModel> childModelList) {

                    }

                    @Override
                    public void onFailed(String message) {

                    }
                }).join();

                RecyclerView.Adapter mainAdapter2 = new ChildListAdapter2(child1);
                recyclerView2.setAdapter(mainAdapter2);

                MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(getContext());
                builder1.setTitle("お手伝いをしたお子様の名前を選択してください")
                        .setView(view2)
                        .setNeutralButton("閉じる",null);
                builder1.create().show();
            }
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



        //お手伝い追加ダイアログ
        LayoutInflater inflater1 = requireActivity().getLayoutInflater();
        View view1 = inflater1.inflate(R.layout.add_task_list_dialog,null);

        //子供選択表示
        RecyclerView recyclerView1 = view1.findViewById(R.id.taskchild);

        recyclerView1.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);



        logger.debug("子供一覧取得開始");
        List<ChildModel> child = childData.getChildList(new ChildModelCallback() {
            @Override
            public void onUnchanged() {

            }

            @Override
            public void onUpdated(List<ChildModel> childModelList) {

            }

            @Override
            public void onFailed(String message) {

            }
        }).join();
        logger.debug("子供一覧取得完了");

        RecyclerView.Adapter mainAdapter1 = new ChildListAdapter(child);
        recyclerView1.setAdapter(mainAdapter1);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("お手伝い名追加")
                .setView(view1)
                .setPositiveButton("追加", null)
                .setNeutralButton("閉じる",null);
        builder.create();

        view.findViewById(R.id.addtask).setOnClickListener(v -> {
            builder.show();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Do something...

    }
}