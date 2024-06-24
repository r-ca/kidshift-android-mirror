package one.nem.kidshift.data.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import one.nem.kidshift.data.UserSettings;
import one.nem.kidshift.data.retrofit.interceptor.AuthorizationInterceptor;
import one.nem.kidshift.utils.KSLogger;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class KidShiftApiServiceModule {

    @Inject
    KSLogger logger;

    @Provides
    @Singleton
    public AuthorizationInterceptor provideAuthorizationInterceptor(UserSettings userSettings, KSLogger logger) {
        return new AuthorizationInterceptor(userSettings, logger);
    }

    // Gson
    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(AuthorizationInterceptor authorizationInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(authorizationInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public KidShiftApiService provideKidShiftApiService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
//                .baseUrl(userSettings.getApiSetting().getApiBaseUrl())
                .baseUrl("https://kidshift-beta.nem.one/")
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .client(okHttpClient)
                .build()
                .create(KidShiftApiService.class);
    }
}
