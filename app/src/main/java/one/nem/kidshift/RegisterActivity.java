package one.nem.kidshift;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentAuthRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentAuthResponse;
import retrofit2.Call;
import retrofit2.Response;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {


    @Inject
    KidShiftApiService kidShiftApiService;

    @Inject
    UserSettings userSettings;


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

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        findViewById(R.id.registerButton).setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Call<ParentAuthResponse> call = kidShiftApiService.parentRegister(new ParentAuthRequest(email, password));
            try {
                Response<ParentAuthResponse> response = call.execute();
                if (response.isSuccessful()) {
                    ParentAuthResponse parentAuthResponse = response.body();
                    if (parentAuthResponse == null || parentAuthResponse.getAccessToken() == null) {
                        // エラー処理
                        return;
                    }
                    userSettings.getAppCommonSetting().setLoggedIn(true);
                    userSettings.getAppCommonSetting().setAccessToken(parentAuthResponse.getAccessToken());
                } else {
                    // エラー処理
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}