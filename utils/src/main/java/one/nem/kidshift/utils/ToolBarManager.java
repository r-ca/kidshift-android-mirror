package one.nem.kidshift.utils;

import androidx.appcompat.widget.Toolbar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ToolBarManager {

    private Toolbar toolbar;

    @Inject
    public ToolBarManager() {
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    private void checkToolbar() {
        if (toolbar == null) {
            throw new IllegalStateException("Toolbar is not set");
        }
    }

    public void setTitle(String title) {
        checkToolbar();
        toolbar.setTitle(title);
    }

    public void setSubtitle(String subtitle) {
        checkToolbar();
        toolbar.setSubtitle(subtitle);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
