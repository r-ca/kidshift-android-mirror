package one.nem.kidshift.feature.child;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.feature.child.adapter.ChildListAdapter;
import one.nem.kidshift.model.ChildModel;
import one.nem.kidshift.model.callback.ChildModelCallback;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class ChildManageMainActivity extends AppCompatActivity {

    @Inject
    ChildData childData;

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger logger;

    ChildListAdapter childListAdapter;

    /* MEMO
    - ToolBarの設定
        - タイトル
        - 戻るボタン
        - 追加ボタン
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_manage_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logger = loggerFactory.create("ChildManageMainActivity");

        RecyclerView recyclerView = findViewById(R.id.childListRecyclerView);
        childListAdapter = new ChildListAdapter();
        childListAdapter.setButtonEventCallback(new ChildListAdapter.ButtonEventCallback() {
            @Override
            public void onEditButtonClick(ChildModel childModel) {
                Toast.makeText(ChildManageMainActivity.this, "Edit button clicked", Toast.LENGTH_SHORT).show();
                // TODO: 実装
            }

            @Override
            public void onLoginButtonClick(ChildModel childModel) {
                childData.issueLoginCode(childModel.getId()).thenAccept(loginCode -> {
                    logger.debug("ログインコード発行完了: " + loginCode);
                    runOnUiThread(() -> showLoginCodeDialog(loginCode));
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(childListAdapter);

        updateList();
    }

    private void showLoginCodeDialog(Integer loginCode) {
        View view = getLayoutInflater().inflate(R.layout.child_login_code_dialog_layout, null);
        TextView loginCodeTextView = view.findViewById(R.id.loginCode);
        // loginCodeをStringに変換して4桁 2つに分割してハイフンで繋げる
        loginCodeTextView.setText(loginCode.toString().substring(0, 4) + "-" + loginCode.toString().substring(4));

        new MaterialAlertDialogBuilder(this)
                .setTitle("ログインコード")
                .setView(view)
                .setPositiveButton("閉じる", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateList() {
        childData.getChildList(new ChildModelCallback() {
            @Override
            public void onUnchanged() {

            }

            @Override
            public void onUpdated(List<ChildModel> childModelList) {

            }

            @Override
            public void onFailed(String message) {

            }
        }).thenAccept(childListAdapter::setChildList).thenRun(() -> {
            runOnUiThread(() -> childListAdapter.notifyDataSetChanged());
        });
    }
}