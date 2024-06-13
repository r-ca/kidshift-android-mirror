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
import one.nem.kidshift.feature.debug.adapter.DebugCommandListItemAdapter;
import one.nem.kidshift.feature.debug.adapter.DebugMenuListItemAdapter;
import one.nem.kidshift.feature.debug.model.DebugCommandItemModel;
import one.nem.kidshift.utils.FeatureFlag;
import one.nem.kidshift.utils.KSLogger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebugDebugConsoleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class DebugDebugConsoleFragment extends Fragment {

    @Inject
    KSLogger ksLogger;

    @Inject
    FeatureFlag featureFlag;

    private final List<DebugCommandItemModel> debugCommandItemModels = new ArrayList<>();
    DebugCommandListItemAdapter debugCommandItemAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DebugDebugConsoleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DebugDebugConsoleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DebugDebugConsoleFragment newInstance(String param1, String param2) {
        DebugDebugConsoleFragment fragment = new DebugDebugConsoleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                    ksLogger, featureFlag);
            debugCommandItemModels.add(
                    new DebugCommandItemModel(
                            debugCommandInput.getText().toString(),
                            debugCommandProcessor.execute(debugCommandInput.getText().toString())));

            debugCommandInput.setText(""); // Clear the input field

            debugCommandItemAdapter.notifyItemInserted(debugCommandItemModels.size() - 1);
        });
    }
}