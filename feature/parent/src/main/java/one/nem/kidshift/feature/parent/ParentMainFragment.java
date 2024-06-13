package one.nem.kidshift.feature.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.model.tasks.TaskItemModel;
import one.nem.kidshift.utils.KSLogger;

@AndroidEntryPoint
public class ParentMainFragment extends Fragment {

    @Inject
    KSLogger ksLogger;
    @Inject
    TaskData taskData;

    public ParentMainFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.main_recycle_view);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<TaskItemModel> task = taskData.getTasks();

        RecyclerView.Adapter mainAdapter = new ParentAdapter(task);
        recyclerView.setAdapter(mainAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ksLogger.addTag("ParentFragment");

        List<TaskItemModel> task = taskData.getTasks();

        ksLogger.debug("取得したデータ: " + task);

    }
}