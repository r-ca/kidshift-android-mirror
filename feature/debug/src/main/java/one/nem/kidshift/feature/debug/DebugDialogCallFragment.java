package one.nem.kidshift.feature.debug;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;

@AndroidEntryPoint
public class DebugDialogCallFragment extends Fragment {

    @Inject
    ChildData childData;

    private FragmentManager fragmentManager;
    public DebugDialogCallFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debug_dialog_call, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view1 =inflater.inflate(R.layout.fragment_debug_dialog_view,null);
        int loginCode = childData.issueLoginCode("543256");
        TextView loginCodeTextView = view1.findViewById(R.id.loginCode);
        new StringBuilder(Integer.toString(loginCode)).insert(3,"-");

        loginCodeTextView.setText(
                new StringBuilder(Integer.toString(loginCode)).insert(3,"-"));

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle("ログインコード")
                .setView(view1)
                .setNeutralButton("閉じる",null);
        builder.create();

        view.findViewById(R.id.callDialogButton).setOnClickListener(v -> {
            // ここに書く
            builder.show();
        });
    }
}