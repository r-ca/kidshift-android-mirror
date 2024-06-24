package one.nem.kidshift.data.retrofit.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.utils.KSLogger;

public class AuthorizationInterceptor implements Interceptor {

    private static final String HEADER_NAME = "Authorization";
    private static final String HEADER_VALUE = "VALUE";
    public static final String HEADER_PLACEHOLDER = HEADER_NAME + ": " + HEADER_VALUE;

    private final UserSettings userSettings;

    public AuthorizationInterceptor(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Log.d("AuthorizationInterceptor", "intercept");
        try {
            if (chain.request().header(HEADER_NAME) == null) {
                Log.d("AuthorizationInterceptor", "Authorization header is null");
                return chain.proceed(chain.request());
            }
            if (!HEADER_VALUE.equals(chain.request().header(HEADER_NAME))) {
                Log.d("AuthorizationInterceptor", "Authorization header is not valid");
                return chain.proceed(chain.request());
            }

//            Log.d("AuthorizationInterceptor", "Authorization header is valid");
//            Log.d("AuthorizationInterceptor", "Authorization header: " + chain.request().header(HEADER_NAME));


            Log.d("AuthorizationInterceptor", "Fetching token from UserSettings");

            if (userSettings == null) {
                Log.e("AuthorizationInterceptor", "userSettings is null");
                return chain.proceed(chain.request());
            } else {
                Log.d("AuthorizationInterceptor", "userSettings is not null");
            }

            UserSettings.AppCommonSetting appCommonSetting = userSettings.getAppCommonSetting();
            if (appCommonSetting == null) {
                Log.e("AuthorizationInterceptor", "AppCommonSetting is null");
                return chain.proceed(chain.request());
            } else {
                Log.d("AuthorizationInterceptor", "AppCommonSetting is not null");
            }

            String token = appCommonSetting.getAccessToken();
            if (token == null) {
                Log.e("AuthorizationInterceptor", "Token is null");
                return chain.proceed(chain.request());
            }

            return chain.proceed(chain.request().newBuilder()
                    .header(HEADER_NAME, userSettings.getAppCommonSetting().getAccessToken())
                    .build());
        } catch (Exception e) {
            return chain.proceed(chain.request());
        }
    }
}
