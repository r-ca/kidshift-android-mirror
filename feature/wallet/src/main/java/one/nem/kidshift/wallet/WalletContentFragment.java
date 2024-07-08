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

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger logger;

    public WalletContentFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() { // WIP
        return new WalletContentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = loggerFactory.create("WalletMainFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_content, container, false);



        return view;
    }
}