package one.nem.kidshift.feature.common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.TaskData;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class CommonHomeFragment extends Fragment {

    private static final String ARG_IS_CHILD_MODE = "isChildMode";

    @Inject
    KSLoggerFactory ksLoggerFactory;
    @Inject
    TaskData taskData;
    @Inject
    ChildData childData;

    private boolean isChildMode;

    CompactCalendarView compactCalendarView;

    public CommonHomeFragment() {
        // Required empty public constructor
    }

    public static CommonHomeFragment newInstance(boolean isChildMode) {
        CommonHomeFragment fragment = new CommonHomeFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_CHILD_MODE, isChildMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isChildMode = getArguments().getBoolean(ARG_IS_CHILD_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_home, container, false);

        compactCalendarView = view.findViewById(R.id.calendar);


        return view;
    }

    private void updateCalender() {

    }
}
