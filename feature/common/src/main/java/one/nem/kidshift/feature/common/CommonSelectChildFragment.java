package one.nem.kidshift.feature.common;

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
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class CommonSelectChildFragment extends Fragment {

    @Inject
    KSLoggerFactory loggerFactory;
    @Inject
    ChildData childData;
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
        childData.getChildListDirect().thenAccept(childList -> {
            adapter = new SelectShowChildListItemAdapter(childList);
            adapter.setCallback(new SelectShowChildListItemAdapter.CompleteButtonClickedCallback() {
                @Override
                public void onClicked(String taskId) {
                    logger.info("Clicked on child with id: " + taskId);
                }
            });
        });

        return view;
    }
}