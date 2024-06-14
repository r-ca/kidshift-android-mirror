package one.nem.kidshift.feature.debug;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.TaskData;

@AndroidEntryPoint
public class DebugMockTestFragment extends Fragment {

    @Inject
    TaskData taskData;

    @Inject
    RewardData rewardData;

    public DebugMockTestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debug_mock_test, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TaskData
        TextView taskDataStatus = view.findViewById(R.id.taskData_mockedStatusTextView);
        taskDataStatus.setText("isMocked: true"); // TODO: 固定値やめる

        TextView taskDataResult = view.findViewById(R.id.taskData_resultTextView);

        view.findViewById(R.id.taskData_getTasksButton).setOnClickListener(v -> {
            taskDataResult.setText(taskData.getTasks().stream().map(Object::toString).reduce("", (a, b) -> a + b + "\n"));
        });

        // RewardData
        TextView rewardDataStatus = view.findViewById(R.id.rewardData_mockedStatusTextView);
        rewardDataStatus.setText("isMocked: true"); // TODO: 固定値やめる

        TextView rewardDataResult = view.findViewById(R.id.rewardData_resultTextView);
        view.findViewById(R.id.rewardData_getTotalRewardButton).setOnClickListener(v -> {
            rewardDataResult.setText(rewardData.getTotalReward().toString());
        });
    }

}