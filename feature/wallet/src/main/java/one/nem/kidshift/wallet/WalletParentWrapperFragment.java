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
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.ToolBarManager;
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
    @Inject
    FabManager fabManager;
    @Inject
    ToolBarManager toolBarManager;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TabAdapter tabAdapter;

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

        tabAdapter = new TabAdapter(requireActivity());

        viewPager.setAdapter(tabAdapter);

        setupViewPager();

        return view;
    }

    private void setupViewPager() {

        // デバッグ用
        childData.getChildList(new ChildModelCallback() {
            @Override
            public void onUnchanged() {
                // TODO: impl
            }

            @Override
            public void onUpdated(List<ChildModel> childModelList) {
                // TODO: impl
            }

            @Override
            public void onFailed(String message) {
                // TODO: impl
            }
        }).thenAccept(childModels -> {
            tabAdapter.setChildList(childModels);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                tab.setText(childModels.get(position).getName());
            }).attach();

            requireActivity().runOnUiThread(() -> tabAdapter.notifyDataSetChanged());
        });
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
            return WalletContentFragment.newInstance(childList.get(position).getId());
        }

        @Override
        public int getItemCount() {
            return childList == null ? 0 : childList.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fabManager.hide();
        toolBarManager.setTitle("ウォレット");
        toolBarManager.setSubtitle(null);
    }
}