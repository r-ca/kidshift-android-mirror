package one.nem.kidshift.feature.parent;

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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.callback.TaskItemModelCallback;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;

@AndroidEntryPoint
public class ParentMainFragment extends Fragment {

    @Inject
    KSLogger ksLogger;
    @Inject
    TaskData taskData;
    @Inject
    ChildData childData;

    public ParentMainFragment() {
        // Required empty public constructor
    }

    private void dataRefresh(){
        SwipeRefreshLayout swipeRefreshLayout = requireView().findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);

        RecyclerView recyclerView =requireView().findViewById(R.id.main_recycle_view);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<TaskItemModel> task = taskData.getTasks(new TaskItemModelCallback() {
            @Override
            public void onUnchanged() {
                // TODO
            }

            @Override
            public void onUpdated(List<TaskItemModel> taskItem) {
                // TODO
            }

            @Override
            public void onFailed(String message) {
                // TODO
            }
        }).join();

        RecyclerView.Adapter mainAdapter = new ParentAdapter(task);
        recyclerView.setAdapter(mainAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //タスク一覧表示
        View view = inflater.inflate(R.layout.fragment_parent_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.main_recycle_view);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ksLogger.debug("タスク一覧取得開始");
        List<TaskItemModel> task = taskData.getTasks(new TaskItemModelCallback() {
            @Override
            public void onUnchanged() {
                // TODO: Do something
            }

            @Override
            public void onUpdated(List<TaskItemModel> taskItem) {
                // TODO: Do something
            }

            @Override
            public void onFailed(String message) {
                // TODO: Do something
            }
        }).join();
        ksLogger.debug("タスク一覧取得完了");

        RecyclerView.Adapter mainAdapter = new ParentAdapter(task);
        recyclerView.setAdapter(mainAdapter);

        //お手伝い追加ダイアログ
        LayoutInflater inflater1 = requireActivity().getLayoutInflater();
        View view1 = inflater1.inflate(R.layout.add_task_list_dialog,null);

        //子供選択表示
        RecyclerView recyclerView1 = view1.findViewById(R.id.taskchild);

        recyclerView1.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);

        ksLogger.debug("子供一覧取得開始");
        List<ChildModel> child = childData.getChildList().join();
        ksLogger.debug("子供一覧取得完了");

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
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(()->{
            dataRefresh();
        });

    }
}