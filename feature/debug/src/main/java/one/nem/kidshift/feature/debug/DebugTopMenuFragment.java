package one.nem.kidshift.feature.debug;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import one.nem.kidshift.feature.debug.adapter.DebugMenuListItemAdapter;
import one.nem.kidshift.feature.debug.model.DebugMenuListItemModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebugTopMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebugTopMenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DebugTopMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DebugTopMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DebugTopMenuFragment newInstance(String param1, String param2) {
        DebugTopMenuFragment fragment = new DebugTopMenuFragment();
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

        View view = inflater.inflate(R.layout.fragment_debug_top_menu, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.topItemsRecyclerView);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        List<DebugMenuListItemModel> debugMenuListItems = new ArrayList<>();

        debugMenuListItems.add(new DebugMenuListItemModel("Data mock tester", "データモジュールの取得処理のモックをテストします", R.id.action_debugTopMenuFragment_to_debugMockTestFragment2, true));

        DebugMenuListItemAdapter adapter = new DebugMenuListItemAdapter(debugMenuListItems);
        recyclerView.setAdapter(adapter);

        return view;
    }
}