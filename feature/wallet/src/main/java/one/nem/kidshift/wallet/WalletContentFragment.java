package one.nem.kidshift.wallet;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class WalletContentFragment extends Fragment {

    private static final String ARG_CHILD_ID = "childId";
    @Inject
    KSLoggerFactory loggerFactory;
    @Inject
    RewardData rewardData;

    @Inject
    FabManager fabManager;

    @Inject
    UserSettings userSettings;

    private KSLogger logger;
    private String childId;

    private TextView totalRewardTextView;

    public WalletContentFragment() {
        // Required empty public constructor
    }

    public static WalletContentFragment newInstance(String childId) {
        WalletContentFragment fragment = new WalletContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CHILD_ID, childId);
        fragment.setArguments(args);
        return fragment;
    }

    public static WalletContentFragment newInstance() {
        return new WalletContentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childId = getArguments().getString(ARG_CHILD_ID);
        }
        logger = loggerFactory.create("WalletMainFragment");
        logger.debug("Received parameter: " + childId);
        if (childId == null) {
            // 単品で呼び出されてる = 子供モードでログインされている
            childId = userSettings.getAppCommonSetting().getChildId();
            if (childId == null) {
                logger.error("Child ID is not set");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_content, container, false);

        totalRewardTextView = view.findViewById(R.id.totalRewardTextView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void updateTotalReward() {
        rewardData.getTotalReward(childId).thenAccept(totalReward -> {
            logger.debug("Total reward: " + totalReward);
            totalRewardTextView.setText(String.valueOf(totalReward) + "円");
        }).exceptionally(throwable -> {
            logger.error("Failed to get total reward: " + throwable.getMessage());
            return null;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTotalReward();
        fabManager.hide();
    }
}
