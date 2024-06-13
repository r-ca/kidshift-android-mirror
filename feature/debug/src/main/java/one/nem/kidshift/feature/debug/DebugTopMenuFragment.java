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

public class DebugTopMenuFragment extends Fragment {

    public DebugTopMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_debug_top_menu, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.topItemsRecyclerView);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        List<DebugMenuListItemModel> debugMenuListItems = new ArrayList<>();

        debugMenuListItems.add(new DebugMenuListItemModel("Data mock tester", "データモジュールの取得処理のモックをテストします", R.id.action_debugTopMenuFragment_to_debugMockTestFragment, true));
        debugMenuListItems.add(new DebugMenuListItemModel("Debug console", "デバッグコマンドを実行します", R.id.action_debugTopMenuFragment_to_debugDebugConsoleFragment, true));

        DebugMenuListItemAdapter adapter = new DebugMenuListItemAdapter(debugMenuListItems);
        recyclerView.setAdapter(adapter);

        return view;
    }
}