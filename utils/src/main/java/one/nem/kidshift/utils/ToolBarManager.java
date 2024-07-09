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

    public void setNavigationIcon(int resId) {
        checkToolbar();
        toolbar.setNavigationIcon(resId);
    }

    public void setMenuResId(int resId) {
        checkToolbar();
        toolbar.getMenu().clear();
        toolbar.inflateMenu(resId);
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener) {
        checkToolbar();
        toolbar.setOnMenuItemClickListener(listener);
    }

    public void setNavigationOnClickListener(Toolbar.OnClickListener listener) {
        checkToolbar();
        toolbar.setNavigationOnClickListener(listener);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
