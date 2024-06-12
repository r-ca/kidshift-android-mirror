package one.nem.kidshift.feature.child;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.utils.KSLogger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChildMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
public class ChildMainFragment extends Fragment {
    @Inject
    KSLogger ksLogger;

    @Inject
    RewardData rewardData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChildMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildMainFragment newInstance(String param1, String param2) {
        ChildMainFragment fragment = new ChildMainFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ksLogger.addTag("ChildMainFragment");

        Integer reward = rewardData.getTotalReward();

        ksLogger.debug("取得したデータ: " + reward);

        TextView totalReward = view.findViewById(R.id.totalReward);

        NumberFormat nf = NumberFormat.getNumberInstance();

        totalReward.setText("¥" + nf.format(reward).toString());
    }
}