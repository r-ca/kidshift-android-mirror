package one.nem.kidshift.data.retrofit.interceptor;

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
    private final KSLogger logger;

    @Inject
    public AuthorizationInterceptor(UserSettings userSettings, KSLogger logger) {
        this.userSettings = userSettings;
        this.logger = logger;
        logger.setTag("AuthorizationInterceptor");
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        try {
            if (chain.request().header(HEADER_NAME) == null) {
                logger.debug("No Authorization header found. Skipping.");
                return chain.proceed(chain.request());
            }
            if (!HEADER_VALUE.equals(chain.request().header(HEADER_NAME))) {
                logger.debug("Authorization header found, but value is not expected. Skipping.");
                return chain.proceed(chain.request());
            }

            logger.debug("Authorization header found. Adding value.");
            return chain.proceed(chain.request().newBuilder()
                    .header(HEADER_NAME, userSettings.getAppCommonSetting().getAccessToken())
                    .build());
        } catch (Exception e) {
            return chain.proceed(chain.request());
        }
    }
}
