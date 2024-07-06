package one.nem.kidshift.utils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;
import javax.inject.Singleton;

import one.nem.kidshift.utils.factory.KSLoggerFactory;
import one.nem.kidshift.utils.models.FabEventCallback;

@Singleton
public class FabManager {
    private FloatingActionButton fab;
    private final KSLogger logger;

    @Inject
    public FabManager(KSLoggerFactory loggerFactory) {
        this.logger = loggerFactory.create("FabManager");
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    private void checkFab() {
        if (fab == null) {
            logger.error("Fab is not set");
            throw new IllegalStateException("Fab is not set");
        }
    }

    public void show() {
        checkFab();
        fab.show();
    }

    public void hide() {
        checkFab();
        fab.hide();
    }

    public boolean isShown() {
        checkFab();
        return fab.isShown();
    }

    public void setFabEventCallback(FabEventCallback callback) {
        checkFab();
        fab.setOnClickListener(v -> callback.onClicked());
        fab.setOnLongClickListener(v -> {
            callback.onLongClicked();
            return true;
        });
    }
}
