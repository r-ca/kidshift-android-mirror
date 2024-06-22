package one.nem.kidshift;

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
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginResponse;
import one.nem.kidshift.utils.KSLogger;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    @Inject
    KSLogger logger;

    @Inject
    UserSettings userSettings;

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

        // Retrofit init
        KidShiftApiService apiService = new Retrofit.Builder()
                .baseUrl("https://kidshift-beta.nem.one/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KidShiftApiService.class);

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        findViewById(R.id.loginButton).setOnClickListener(v -> {
            CompletableFuture.supplyAsync(() -> {
                try {
                    Response<ParentLoginResponse> response = apiService.parentLogin(
                            new ParentLoginRequest(
                                    emailEditText.getText().toString(),
                                    passwordEditText.getText().toString()
                            )).execute();

                    return response;
                } catch (IOException e) {
                    logger.error("IOException");
                    throw new RuntimeException(e);
                }
            }).thenAccept(response -> {
                if (response.isSuccessful()) {
                    logger.info("Login Success");
                    logger.debug("AccessToken: " + response.body().getAccessToken());

                    userSettings.getAppCommonSetting().setLoggedIn(true);
                    // TODO: Apiキーを保存
                    finish();
                } else {
                    logger.error("Login Failed");
                    try {
                        logger.debug("Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        logger.error("IOException while reading error body");
                    }
                    // ログイン失敗時の処理
                }
            }).exceptionally(e -> {
                logger.error("Exception occurred: " + e.getMessage());
                return null;
            });
        });

        // for Debug
        findViewById(R.id.loginButton).setOnLongClickListener(v -> {
            // ログイン画面をバイパスしてメイン画面に遷移
            finish();
            return true;
        });
    }
}