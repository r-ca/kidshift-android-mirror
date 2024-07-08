package one.nem.kidshift.feature.child;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Objects;

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

        // ToolBarのセットアップ
        Toolbar toolbar = findViewById(R.id.toolBar);
        // タイトル
        toolbar.setTitle("子供アカウント管理");
        // 閉じる
        toolbar.setNavigationIcon(one.nem.kidshift.shared.R.drawable.check_24px); // TODO: アイコン修正
        toolbar.setNavigationOnClickListener(v -> finish());
        // 追加ボタン
        toolbar.inflateMenu(R.menu.child_manage_main_toolbar_item);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add_child_account) {
                showAddChildDialog();
                return true;
            }
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.childListRecyclerView);
        childListAdapter = new ChildListAdapter();
        childListAdapter.setButtonEventCallback(new ChildListAdapter.ButtonEventCallback() {
            @Override
            public void onEditButtonClick(ChildModel childModel) {
                showEditChildDialog(childModel);
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

    private void showAddChildDialog() {
        // EditTextを作成
        EditText childNameEditText = new EditText(this);
        childNameEditText.setHint("子供の名前");
        // FrameLayoutに入れる
        FrameLayout container = new FrameLayout(this);
        container.addView(childNameEditText);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) childNameEditText.getLayoutParams();
        params.setMargins(32, 16, 32, 16);
        childNameEditText.setLayoutParams(params);

        new MaterialAlertDialogBuilder(this)
                .setTitle("子供アカウント追加")
                .setView(container)
                .setPositiveButton("追加", (dialog, which) -> {
                    String childName = Objects.requireNonNull(childNameEditText.getText()).toString();
                    if (childName.isEmpty()) {
                        Toast.makeText(this, "名前を入力してください", Toast.LENGTH_SHORT).show();
                    }
                    childData.addChild(new ChildModel(childName))
                            .thenRun(this::updateListDirectly);
                })
                .setNegativeButton("キャンセル", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showEditChildDialog(ChildModel childModel) {
        // EditTextを作成
        EditText childNameEditText = new EditText(this);
        childNameEditText.setHint("子供の名前");
        childNameEditText.setText(childModel.getName());
        // FrameLayoutに入れる
        FrameLayout container = new FrameLayout(this);
        container.addView(childNameEditText);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) childNameEditText.getLayoutParams();
        params.setMargins(32, 16, 32, 16);
        childNameEditText.setLayoutParams(params);

        new MaterialAlertDialogBuilder(this)
                .setTitle("子供アカウント編集")
                .setView(container)
                .setPositiveButton("保存", (dialog, which) -> {
                    String childName = Objects.requireNonNull(childNameEditText.getText()).toString();
                    if (childName.isEmpty()) {
                        Toast.makeText(this, "名前を入力してください", Toast.LENGTH_SHORT).show();
                    }
                    childModel.setName(childName);
                    childData.updateChild(childModel)
                            .thenRun(this::updateListDirectly);
                })
                .setNegativeButton("キャンセル", (dialog, which) -> dialog.dismiss())
                // 削除ボタン
                .setNeutralButton("削除", (dialog, which) -> { // TODO: 確認ダイアログを表示する
                    childData.removeChild(childModel.getId())
                            .thenRun(this::updateListDirectly);
                })
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

    @SuppressLint("NotifyDataSetChanged")
    private void updateListDirectly() {
        childData.getChildListDirect().thenAccept(childListAdapter::setChildList).thenRun(() -> {
            runOnUiThread(() -> childListAdapter.notifyDataSetChanged());
        });
    }
}