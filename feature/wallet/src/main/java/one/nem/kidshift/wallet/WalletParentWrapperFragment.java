package one.nem.kidshift.wallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class WalletParentWrapperFragment extends Fragment {

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger logger;

    @Inject
    ChildData childData;

    @Inject
    RewardData rewardData;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public WalletParentWrapperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_parent_wrapper, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        TabAdapter tabAdapter = new TabAdapter(requireActivity());

        // デバッグ用
        List<ChildModel> childList = new ArrayList<>();
        ChildModel child = new ChildModel();
        child.setId("1");
        child.setName("Child 1");
        childList.add(child);
        ChildModel child2 = new ChildModel();
        child2.setId("2");
        child2.setName("Child 2");
        childList.add(child2);

        tabAdapter.setChildList(childList);

        viewPager.setAdapter(tabAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText("Child " + (position + 1));
        }).attach();


        return view;
    }

    private static class TabAdapter extends FragmentStateAdapter {

        private List<ChildModel> childList;

        public void setChildList(List<ChildModel> childList) {
            this.childList = childList;
        }

        public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // デバッグ用
            return WalletContentFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return childList == null ? 0 : childList.size();
        }
    }
}