package one.nem.kidshift;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChildLoginActivity extends AppCompatActivity {


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

        // コードのフォーカスを自動で移動する
        loginCode1 = findViewById(R.id.loginCode_1);
        loginCode2 = findViewById(R.id.loginCode_2);
        loginCode3 = findViewById(R.id.loginCode_3);
        loginCode4 = findViewById(R.id.loginCode_4);
        loginCode5 = findViewById(R.id.loginCode_5);
        loginCode6 = findViewById(R.id.loginCode_6);
        loginCode7 = findViewById(R.id.loginCode_7);
        loginCode8 = findViewById(R.id.loginCode_8);

        loginCode1.addTextChangedListener(new LoginCodeTextWatcher(loginCode1, loginCode2));
        loginCode2.addTextChangedListener(new LoginCodeTextWatcher(loginCode2, loginCode3));
        loginCode3.addTextChangedListener(new LoginCodeTextWatcher(loginCode3, loginCode4));
        loginCode4.addTextChangedListener(new LoginCodeTextWatcher(loginCode4, loginCode5));
        loginCode5.addTextChangedListener(new LoginCodeTextWatcher(loginCode5, loginCode6));
        loginCode6.addTextChangedListener(new LoginCodeTextWatcher(loginCode6, loginCode7));
        loginCode7.addTextChangedListener(new LoginCodeTextWatcher(loginCode7, loginCode8));
        loginCode8.addTextChangedListener(new LoginCodeTextWatcher(loginCode8, null));

        // ログインボタンを押したときの処理
        findViewById(R.id.childLoginButton).setOnClickListener(v -> {
            Toast.makeText(this, "ログインコード: " + getLoginCode(), Toast.LENGTH_SHORT).show();
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

    private static class LoginCodeTextWatcher implements TextWatcher {
        private EditText currentView;
        private final EditText nextView;

        LoginCodeTextWatcher(EditText currentView, EditText nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
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
    }
}