package one.nem.kidshift.data.impl;

import javax.inject.Inject;

import one.nem.kidshift.data.KSActions;
import one.nem.kidshift.data.UserSettings;

public class KSActionsImpl implements KSActions {

    private UserSettings userSettings;

    @Inject
    public KSActionsImpl(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @Override
    public void syncTasks() {

    }

    @Override
    public void syncChildList() {

    }

    @Override
    public void syncParent() {

    }
}
