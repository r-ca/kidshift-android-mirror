package one.nem.kidshift.data.retrofit.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.utils.KSLogger;

/**
 * Authorization placeholderが指定されている場合にアクセストークンで置換するインターセプター
 */
public class AuthorizationInterceptor implements Interceptor {

    private static final String HEADER_NAME = "Authorization";
    private static final String HEADER_VALUE = "VALUE";
    public static final String HEADER_PLACEHOLDER = HEADER_NAME + ": " + HEADER_VALUE;

    private final UserSettings userSettings;
    private final KSLogger logger;

    public AuthorizationInterceptor(UserSettings userSettings, KSLogger logger) {
        this.userSettings = userSettings;
        this.logger = logger;
        logger.setTag("Auth_Interceptor");
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        logger.debug("intercept");
        try {
            if (chain.request().header(HEADER_NAME) == null) {
                logger.debug("Authorization header is null");
                return chain.proceed(chain.request());
            }
            if (!HEADER_VALUE.equals(chain.request().header(HEADER_NAME))) {
                logger.debug("Authorization header is invalid");
                return chain.proceed(chain.request());
            }

            return chain.proceed(chain.request().newBuilder()
                    .header(HEADER_NAME, "Barer " + userSettings.getAppCommonSetting().getAccessToken())
                    .build());
        } catch (Exception e) {
            return chain.proceed(chain.request());
        }
    }
}
