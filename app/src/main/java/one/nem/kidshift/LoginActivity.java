package one.nem.kidshift;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger logger;

    @Inject
    UserSettings userSettings;

    @Inject
    KidShiftApiService kidShiftApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logger = loggerFactory.create("LoginActivity");

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String email = emailEditText.getText().toString(); // TODO: メールアドレスのバリデーション
            String password = passwordEditText.getText().toString();

            Call<ParentAuthResponse> call = kidShiftApiService.parentLogin(new ParentAuthRequest(email, password));
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
        });

        findViewById(R.id.intentRegisterButton).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}