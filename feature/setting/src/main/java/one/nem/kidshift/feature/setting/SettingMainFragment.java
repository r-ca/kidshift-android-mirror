package one.nem.kidshift.feature.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ParentModelCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class SettingMainFragment extends Fragment {

    @Inject
    ChildData childData;

    @Inject
    ParentData parentData;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingMainFragment newInstance(String param1, String param2) {
        SettingMainFragment fragment = new SettingMainFragment();
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

        CompletableFuture<ParentModel> completableFuture = parentData.getParent(new ParentModelCallback() {

            @Override
            public void onUnchanged() {
                // TODO
            }

            @Override
            public void onUpdated(ParentModel parent) {
                // TODO
            }

            @Override
            public void onFailed(String message) {
                // TODO
            }
        });

        /*
        TODO:
            - コールバックの処理を実装
            - 結果に応じてRecyclerViewを更新する
            - キャッシュ受け取りの時にjoinでUIスレッドをブロックしないように
            - Placeholderの表示?
            - エラーハンドリング
                - onFailed時にそれを通知
         */

        ParentModel parent = completableFuture.join();

        if (parent == null) {
            parent = new ParentModel(); // Workaround（非ログインデバッグ用）
            parent.setName("親の名前");
            parent.setEmail("親のアドレス");
        }




        View view = inflater.inflate(R.layout.fragment_setting_main, container, false);

        // Pull-to-refresh（スワイプで更新）
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        ParentModel finalParent = parent;
        swipeRefreshLayout.setOnRefreshListener(() ->{

            TextView username = view.findViewById(R.id.username);
            TextView useradress = view.findViewById(R.id.useradress);

            username.setText(finalParent.getName());
            useradress.setText(finalParent.getEmail());

            RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            List<ChildModel> child = childData.getChildList().join();

            RecyclerView.Adapter mainAdapter = new SettingAdapter(child);
            recyclerView.setAdapter(mainAdapter);

            swipeRefreshLayout.setRefreshing(false);

        });

        //RecyclerViewの処理
        TextView username = view.findViewById(R.id.username);
        TextView useradress = view.findViewById(R.id.useradress);

        username.setText(parent.getName());
        useradress.setText(parent.getEmail());

        RecyclerView recyclerView = view.findViewById(R.id.childrecyclerview);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<ChildModel> child = childData.getChildList().join();

        RecyclerView.Adapter mainAdapter = new SettingAdapter(child);
        recyclerView.setAdapter(mainAdapter);

        //子供の名前追加のダイアログ
        LayoutInflater inflater1 = requireActivity().getLayoutInflater();
        View view1 = inflater1.inflate(R.layout.add_child_list_dialog,null);


        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("お子様の名前を入力してください。")
                .setView(view1)
                .setPositiveButton("追加",null)
                .setNeutralButton("閉じる",null);
        builder.create();

        view.findViewById(R.id.addchildname).setOnClickListener(v -> {
            builder.show();
        });


        return view;
    }
}