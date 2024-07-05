package one.nem.kidshift;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.model.callback.FabEventCallback;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    KSLoggerFactory loggerFactory;

    @Inject
    FabManager fabManager;

    private KSLogger logger;

    private FloatingActionButton fab;

    @Inject
    UserSettings userSettings;

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

        logger = loggerFactory.create("MainActivity");

        logger.info("MainActivity started!");

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

        // Check logged in
        if (userSettings.getAppCommonSetting().isLoggedIn()) {
            logger.info("User is logged in!");
        } else {
            logger.info("User is not logged in!");

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        fab = findViewById(R.id.mainFab);
        fabManager.setFab(fab);
    }

    /**
     * 起動時にバックグラウンドで行う各種更新処理とか
     */
    private void startup() {

    }
}