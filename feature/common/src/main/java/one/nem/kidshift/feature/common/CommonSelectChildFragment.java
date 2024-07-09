package one.nem.kidshift.feature.common;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.feature.common.adapter.SelectShowChildListItemAdapter;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.RecyclerViewAnimUtils;
import one.nem.kidshift.utils.ToolBarManager;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class CommonSelectChildFragment extends Fragment {

    @Inject
    KSLoggerFactory loggerFactory;
    @Inject
    ChildData childData;
    @Inject
    FabManager fabManager;
    @Inject
    ToolBarManager toolBarManager;
    @Inject
    RecyclerViewAnimUtils recyclerViewAnimUtils;
    private KSLogger logger;

    private SelectShowChildListItemAdapter adapter;

    public CommonSelectChildFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = loggerFactory.create("CommonSelectChildFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_select_child, container, false);

        RecyclerView childListRecyclerView = view.findViewById(R.id.selectShowChildListRecyclerView);
        childListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAnimUtils.setSlideUpAnimation(childListRecyclerView);
        adapter = new SelectShowChildListItemAdapter();
        adapter.setCallback(taskId -> {
            // 静的解析エラーが発生するのになぜか実行はできる↓
            findNavController(view).navigate(CommonSelectChildFragmentDirections.actionCommonSelectChildFragmentToCommonHomeFragmentParentChild(taskId));
        });
        CompletableFuture.runAsync(() -> childListRecyclerView.setAdapter(adapter)).thenRun(() -> childData.getChildListDirect().thenAccept(childList -> {
            requireActivity().runOnUiThread(() -> {
                adapter.setChildDataList(childList);
                adapter.notifyItemRangeChanged(0, childList.size());
            });
        }).exceptionally(e -> {
            logger.error("Failed to load child list");
            return null;
        }));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fabManager.hide();
        toolBarManager.setTitle("子供選択");
        toolBarManager.setSubtitle(null);
    }

}