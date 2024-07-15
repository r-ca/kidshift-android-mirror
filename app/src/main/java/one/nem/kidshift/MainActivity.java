package one.nem.kidshift;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.ParentData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.feature.child.ChildManageMainActivity;
import one.nem.kidshift.model.ParentModel;
import one.nem.kidshift.model.callback.ParentModelCallback;
import one.nem.kidshift.utils.FabManager;
import one.nem.kidshift.utils.FeatureFlag;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.ToolBarManager;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    KSLoggerFactory ksLoggerFactory;
    @Inject
    FabManager fabManager;
    @Inject
    ToolBarManager toolBarManager;
    @Inject
    FeatureFlag featureFlag;
    @Inject
    UserSettings userSettings;
    @Inject
    ParentData parentData;
    @Inject
    ChildData childData;


    private KSLogger logger;
    private FloatingActionButton fab;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // 0にしないとボトムナビゲーションが埋まるため
            return insets;
        });

        logger = ksLoggerFactory.create("MainActivity");

        // Check logged in
        if (userSettings.getAppCommonSetting().isLoggedIn()) {
            logger.info("User is logged in!");
        } else {
            logger.info("User is not logged in!");

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarManager.setToolbar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        // アイテムが選択されたときの処理
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                logger.debug("Item selected: " + item.getItemId());
                if (item.getItemId() == R.id.manage_child_account) {
                    Intent intent = new Intent(MainActivity.this, ChildManageMainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.show_debug_dialog) {
                    showDebugDialog();
                    return true;
                } else if (item.getItemId() == R.id.show_account_dialog) {
                    showAccountDialog();
                    return true;
                } else {
                    logger.warn("不明なアイテム: " + item.getItemId());
                }
                return false;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(
                        this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        // Init navigation
        try {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.fragmentContainerView);
            assert navHostFragment != null;

            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserSettings.AppCommonSetting appCommonSetting = userSettings.getAppCommonSetting();
        if (appCommonSetting.isChildMode()) {
            logger.info("Child mode is enabled!");
            // 保護者向けのナビゲーションを削除
            bottomNavigationView.getMenu().removeItem(R.id.feature_common_parent_child_navigation);
            bottomNavigationView.getMenu().removeItem(R.id.feature_common_parent_parent_navigation);

            bottomNavigationView.getMenu().removeItem(R.id.feature_wallet_parent_navigation);
            // startDestinationを変更
            bottomNavigationView.setSelectedItemId(R.id.feature_common_child_child_navigation);

            // manage_child_accountを削除
            navigationView.getMenu().removeItem(R.id.manage_child_account);
        } else {
            logger.info("Child mode is disabled!");
            bottomNavigationView.getMenu().removeItem(R.id.feature_common_child_child_navigation);
            bottomNavigationView.getMenu().removeItem(R.id.feature_wallet_child_navigation);
        }

        fab = findViewById(R.id.mainFab);
        fabManager.setFab(fab);
    }

    /**
     * 起動時にバックグラウンドで行う各種更新処理とか
     */
    private void startup() {

    }

    private void showAccountDialog() {
        boolean isEditMode = false;
        View view = getLayoutInflater().inflate(R.layout.user_info_dialog_layout, null);
        if (userSettings.getAppCommonSetting().isChildMode()) {
            childData.getChild(userSettings.getAppCommonSetting().getChildId()).thenAccept(childModel -> {
                ((TextView) view.findViewById(R.id.userNameTextView)).setText(childModel.getName());
                logger.debug("ChildModel: " + childModel.getName());
                ((TextView) view.findViewById(R.id.emailTextView)).setText("子供ユーザーはメールアドレスを設定できません");
                ((Chip) view.findViewById(R.id.chip)).setText("子供/Child");
            }).join();
        } else {
            parentData.getParentDirect().thenAccept(parentModel -> {
                ((TextView) view.findViewById(R.id.userNameTextView)).setText(parentModel.getName());
                logger.debug("ParentModel: " + parentModel.getName() + ", " + parentModel.getEmail());
                ((TextView) view.findViewById(R.id.emailTextView)).setText(parentModel.getEmail());
                ((Chip) view.findViewById(R.id.chip)).setText("保護者/Parent");
            }).join();
        }

        // Workaround
        if (userSettings.getAppCommonSetting().isChildMode()) {
            view.findViewById(R.id.userNameEditButton).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.userNameEditButton).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.userNameEditButton).setOnClickListener(v -> {
            EditText editText = new EditText(this);
            editText.setText(((TextView) view.findViewById(R.id.userNameTextView)).getText());
            editText.setHint("ユーザー名");
            new MaterialAlertDialogBuilder(this)
                    .setTitle("ユーザー名の変更")
                    .setView(editText)
                    .setPositiveButton("OK", (dialog, which) -> {
                        ((TextView) view.findViewById(R.id.userNameTextView)).setText(editText.getText());
                        if (userSettings.getAppCommonSetting().isChildMode()) {
                            // 子供モード
                            childData.getChild(userSettings.getAppCommonSetting().getChildId()).thenAccept(childModel -> {
                                childModel.setName(editText.getText().toString());
                                childData.updateChild(childModel);
                            });
                        } else {
                            // 保護者モード
                            parentData.getParentDirect().thenAccept(parentModel -> {
                                parentModel.setName(editText.getText().toString());
                                parentData.updateParent(parentModel);
                            });
                        }
                    })
                    .setNegativeButton("キャンセル", (dialog, which) -> {
                        // Do nothing
                    })
                    .show();
        });

        new MaterialAlertDialogBuilder(this)
                .setTitle("アカウント情報")
                .setView(view)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Do nothing
                })
                .show();
    }

    private void showLoginCodeDialog(Integer loginCode) {
        View view = getLayoutInflater().inflate(R.layout.parent_login_code_dialog_layout, null);
        TextView loginCodeTextView = view.findViewById(R.id.loginCodeTextView);
        // loginCodeをStringに変換して4桁 2つに分割してハイフンで繋げる
        loginCodeTextView.setText(loginCode.toString().substring(0, 4) + "-" + loginCode.toString().substring(4));

        new MaterialAlertDialogBuilder(this)
                .setTitle("ログインコード")
                .setView(view)
                .setPositiveButton("閉じる", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showDebugDialog() {

        ScrollView scrollView = new ScrollView(this);
        scrollView.setPadding(32, 16, 32, 16);
        LinearLayout linearLayout = new LinearLayout(this);

        TextView serverAddressTextView = new TextView(this);
        serverAddressTextView.setText("サーバーアドレス: " + userSettings.getApiSetting().getApiBaseUrl());
        serverAddressTextView.setTextSize(16);

        TextView accessTokenTextView = new TextView(this);
        accessTokenTextView.setText("アクセストークン: " + userSettings.getAppCommonSetting().getAccessToken());
        accessTokenTextView.setTextSize(16);

        TextView childModeTextView = new TextView(this);
        childModeTextView.setText("子供モード: " + userSettings.getAppCommonSetting().isChildMode());
        childModeTextView.setTextSize(16);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(serverAddressTextView);
        linearLayout.addView(createDivider(this));
        linearLayout.addView(accessTokenTextView);
        linearLayout.addView(createDivider(this));
        linearLayout.addView(childModeTextView);

        scrollView.addView(linearLayout);

        new MaterialAlertDialogBuilder(this)
                .setTitle("参考情報(評価用)")
                .setView(scrollView)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Do nothing
                })
                .show();

    }

    private MaterialDivider createDivider(Context context) {
        MaterialDivider divider = new MaterialDivider(context);
        // Margin (48, 16, 48, 16)
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(48, 16, 48, 16);
        divider.setLayoutParams(params);
        return divider;
    }

    public Toolbar getToolbar() { // TODO: toolbarのインスタンス自体を取得するのではなく，fabのように操作できるようにする
        return toolbar;
    }

}