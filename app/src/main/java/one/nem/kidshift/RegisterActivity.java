package one.nem.kidshift;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentAuthRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentAuthResponse;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {


    @Inject
    KidShiftApiService kidShiftApiService;

    @Inject
    UserSettings userSettings;

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger logger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logger = loggerFactory.create("RegisterActivity");

        EditText emailEditText = findViewById(R.id.parentRegisterEmailEditText); // TODO: メールアドレスのバリデーション
        EditText passwordEditText = findViewById(R.id.parentRegisterPasswordEditText);

        findViewById(R.id.parentRegisterButton).setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            CompletableFuture.runAsync(() -> {
                Call<ParentAuthResponse> call = kidShiftApiService.parentRegister(new ParentAuthRequest(email, password));
                try {
                    Response<ParentAuthResponse> response = call.execute();
                    if (response.isSuccessful()) {
                        ParentAuthResponse parentAuthResponse = response.body();
                        if (parentAuthResponse == null || parentAuthResponse.getAccessToken() == null) {
                            // エラー処理
                            logger.error("ParentAuthResponseがnullまたはAccessTokenがnullです");
                            return;
                        }
                        userSettings.getAppCommonSetting().setLoggedIn(true);
                        userSettings.getAppCommonSetting().setAccessToken(parentAuthResponse.getAccessToken());
                    } else {
                        logger.error("リクエストに失敗しました");
                        // エラー処理
                    }
                } catch (Exception e) {
                    logger.error("リクエストに失敗しました: " + e.getMessage());
                    e.printStackTrace();
                }
            }).thenRun(() -> {
                startActivity(new Intent(this, MainActivity.class));
            });
        });

        findViewById(R.id.toLoginButton).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}