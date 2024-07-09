package one.nem.kidshift.utils;

import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

@Singleton
public class RecyclerViewAnimUtils {

    @Inject
    public RecyclerViewAnimUtils() {
    }

    // SlideUp
    public void setSlideUpAnimation(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(300);
        recyclerView.getItemAnimator().setRemoveDuration(100);
        recyclerView.getItemAnimator().setMoveDuration(100);
        recyclerView.getItemAnimator().setChangeDuration(100);
    }

    public void setSlideUpAnimation(RecyclerView recyclerView, int duration) {
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(duration);
        recyclerView.getItemAnimator().setRemoveDuration(duration);
        recyclerView.getItemAnimator().setMoveDuration(duration);
        recyclerView.getItemAnimator().setChangeDuration(duration);
    }

    // Fade
    public void setFadeAnimation(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.getItemAnimator().setAddDuration(300);
        recyclerView.getItemAnimator().setRemoveDuration(100);
        recyclerView.getItemAnimator().setMoveDuration(100);
        recyclerView.getItemAnimator().setChangeDuration(100);
    }

    public void setFadeAnimation(RecyclerView recyclerView, int duration) {
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.getItemAnimator().setAddDuration(duration);
        recyclerView.getItemAnimator().setRemoveDuration(duration);
        recyclerView.getItemAnimator().setMoveDuration(duration);
        recyclerView.getItemAnimator().setChangeDuration(duration);
    }
}
