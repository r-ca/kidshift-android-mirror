package one.nem.kidshift.feature.debug;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.feature.debug.adapter.DebugCommandListItemAdapter;
import one.nem.kidshift.feature.debug.model.DebugCommandItemModel;
import one.nem.kidshift.utils.FeatureFlag;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class DebugDebugConsoleFragment extends Fragment {

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger ksLogger;

    @Inject
    FeatureFlag featureFlag;

    @Inject
    UserSettings userSettings;

    private final List<DebugCommandItemModel> debugCommandItemModels = new ArrayList<>();
    DebugCommandListItemAdapter debugCommandItemAdapter;

    public DebugDebugConsoleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ksLogger = loggerFactory.create("DebugConsole");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug_debug_console, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.debugCommandHistoryRecyclerView);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        debugCommandItemModels.add(new DebugCommandItemModel("---", "Initialized Debug Console"));

        debugCommandItemAdapter = new DebugCommandListItemAdapter(debugCommandItemModels);
        recyclerView.setAdapter(debugCommandItemAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView debugCommandInput = view.findViewById(R.id.debugCommandEditText);
        view.findViewById(R.id.debugCommandExecuteButton).setOnClickListener(v -> {
            DebugCommandProcessor debugCommandProcessor = new DebugCommandProcessor(
                    ksLogger, featureFlag, userSettings);
            debugCommandItemModels.add(
                    new DebugCommandItemModel(
                            debugCommandInput.getText().toString(),
                            debugCommandProcessor.execute(debugCommandInput.getText().toString())));

            debugCommandInput.setText(""); // Clear the input field

            debugCommandItemAdapter.notifyItemInserted(debugCommandItemModels.size() - 1);
        });
    }
}