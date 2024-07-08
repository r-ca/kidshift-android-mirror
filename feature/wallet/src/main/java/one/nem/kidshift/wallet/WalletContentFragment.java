package one.nem.kidshift.wallet;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class WalletContentFragment extends Fragment {

    private static final String ARG_CHILD_ID = "childId";

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger logger;
    private String childId;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childId = getArguments().getString(ARG_CHILD_ID);
        }
        logger = loggerFactory.create("WalletMainFragment");
        logger.debug("Received parameter: " + childId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_content, container, false);
        // Use the parameter as needed in the view setup
        return view;
    }
}
