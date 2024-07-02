package one.nem.kidshift;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import one.nem.kidshift.utils.FeatureFlag;
import one.nem.kidshift.utils.KSLogger;
import one.nem.kidshift.utils.factory.KSLoggerFactory;

@HiltAndroidApp
public class KidShiftApplication extends Application {

    @Inject
    FeatureFlag featureFlag;

    @Inject
    KSLoggerFactory loggerFactory;

    private KSLogger logger;

    @Override
    public void onCreate() {
        super.onCreate();

        this.logger = loggerFactory.create("KidShiftApplication");

        logger.info("super.onCreate() completed");

        if(featureFlag.isEnabled("dynamicColorEnable")) {
            if (DynamicColors.isDynamicColorAvailable()) {
                DynamicColors.applyToActivitiesIfAvailable(this);
                logger.info("DynamicColors is available and applied to activities.");
            } else {
                logger.info("DynamicColors is not available.");
            }
        } else {
            logger.info("DynamicColors feature is disabled by feature flag.");
        }

    }
}
