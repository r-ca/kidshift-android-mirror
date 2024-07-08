package one.nem.kidshift;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.data.ChildData;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.KidShiftApiService;
import one.nem.kidshift.data.retrofit.model.child.auth.ChildAuthRequest;
import one.nem.kidshift.data.retrofit.model.child.auth.ChildAuthResponse;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;
import retrofit2.Call;

@AndroidEntryPoint
public class ChildLoginActivity extends AppCompatActivity {

    @Inject
    UserSettings userSettings;
    @Inject
    KSLoggerFactory loggerFactory;
    @Inject
    KidShiftApiService kidShiftApiService;

    private KSLogger logger;


    private EditText loginCode1, loginCode2, loginCode3, loginCode4, loginCode5, loginCode6, loginCode7, loginCode8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logger = loggerFactory.create("ChildLoginActivity");

        // コードのフォーカスを自動で移動する
        loginCode1 = findViewById(R.id.loginCode_1);
        loginCode2 = findViewById(R.id.loginCode_2);
        loginCode3 = findViewById(R.id.loginCode_3);
        loginCode4 = findViewById(R.id.loginCode_4);
        loginCode5 = findViewById(R.id.loginCode_5);
        loginCode6 = findViewById(R.id.loginCode_6);
        loginCode7 = findViewById(R.id.loginCode_7);
        loginCode8 = findViewById(R.id.loginCode_8);

        loginCode1.addTextChangedListener(new LoginCodeTextWatcher(loginCode1, loginCode2, null));
        loginCode2.addTextChangedListener(new LoginCodeTextWatcher(loginCode2, loginCode3, loginCode1));
        loginCode3.addTextChangedListener(new LoginCodeTextWatcher(loginCode3, loginCode4, loginCode2));
        loginCode4.addTextChangedListener(new LoginCodeTextWatcher(loginCode4, loginCode5, loginCode3));
        loginCode5.addTextChangedListener(new LoginCodeTextWatcher(loginCode5, loginCode6, loginCode4));
        loginCode6.addTextChangedListener(new LoginCodeTextWatcher(loginCode6, loginCode7, loginCode5));
        loginCode7.addTextChangedListener(new LoginCodeTextWatcher(loginCode7, loginCode8, loginCode6));
        loginCode8.addTextChangedListener(new LoginCodeTextWatcher(loginCode8, null, loginCode7));

        // ログインボタンを押したときの処理
        findViewById(R.id.childLoginButton).setOnClickListener(v -> {
            logger.debug("ログインボタンが押されました");
            Call<ChildAuthResponse> call = kidShiftApiService.childLogin(new ChildAuthRequest(getLoginCode()));
            CompletableFuture.runAsync(() -> {
                try {
                    ChildAuthResponse childAuthResponse = call.execute().body();
                    if (childAuthResponse == null || childAuthResponse.getAccessToken() == null) {
                        // エラー処理
                        logger.error("ChildAuthResponseがnullまたはAccessTokenがnullです");
                        return;
                    }
                    UserSettings.AppCommonSetting appCommonSetting = userSettings.getAppCommonSetting();
                    appCommonSetting.setLoggedIn(true);
                    appCommonSetting.setAccessToken(childAuthResponse.getAccessToken());
                    appCommonSetting.setChildId(childAuthResponse.getChildId());
                    appCommonSetting.setChildMode(true);
                } catch (Exception e) {
                    logger.error("リクエストに失敗しました");
                    Toast.makeText(this, "ログインに失敗しました", Toast.LENGTH_SHORT).show();
                }
            }).thenRun(() -> {
                startActivity(new Intent(this, MainActivity.class));
            });
        });
    }

    private String getLoginCode() {
        return loginCode1.getText().toString() +
                loginCode2.getText().toString() +
                loginCode3.getText().toString() +
                loginCode4.getText().toString() +
                loginCode5.getText().toString() +
                loginCode6.getText().toString() +
                loginCode7.getText().toString() +
                loginCode8.getText().toString();
    }

    private static class LoginCodeTextWatcher implements TextWatcher, View.OnKeyListener {
        private EditText currentView;
        private final EditText nextView;
        private final EditText previousView;

        LoginCodeTextWatcher(EditText currentView, EditText nextView, EditText previousView) {
            this.currentView = currentView;
            this.nextView = nextView;
            this.previousView = previousView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1 && nextView != null) {
                nextView.requestFocus();
            }
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) { // TODO: バックスペースの処理
//                EditText currentView = (EditText) v;
//                if (currentView.getText().length() == 0 && previousView != null) {
//                    previousView.requestFocus();
//                }
//            }
//            return false;
            return true;
        }
    }
}