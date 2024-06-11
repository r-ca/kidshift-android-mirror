package one.nem.kidshift;

import android.os.Bundle;

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

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.utils.KSLogger;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    KSLogger ksLogger;

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

        ksLogger.info("MainActivity started!");

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
    }
}