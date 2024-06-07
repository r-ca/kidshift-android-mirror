package one.nem.kidshift;

import android.app.Application;
import android.util.Log;

import com.google.android.material.color.DynamicColors;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class KidShiftApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(DynamicColors.isDynamicColorAvailable()) {
            Log.d("StartUp/DynamicColors", "DynamicColors is available!");
            DynamicColors.applyToActivitiesIfAvailable(this);
        } else {
            Log.d("StartUp/DynamicColors", "DynamicColors is not available.");
        }
    }
}
