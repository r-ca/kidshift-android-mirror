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
    public RecyclerView setSlideUpAnimation(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(500);
        recyclerView.getItemAnimator().setRemoveDuration(500);
        recyclerView.getItemAnimator().setMoveDuration(500);
        recyclerView.getItemAnimator().setChangeDuration(500);

        return recyclerView;
    }

    public RecyclerView setSlideUpAnimation(RecyclerView recyclerView, int duration) {
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(duration);
        recyclerView.getItemAnimator().setRemoveDuration(duration);
        recyclerView.getItemAnimator().setMoveDuration(duration);
        recyclerView.getItemAnimator().setChangeDuration(duration);

        return recyclerView;
    }

    // Fade
    public RecyclerView setFadeAnimation(RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.getItemAnimator().setAddDuration(500);
        recyclerView.getItemAnimator().setRemoveDuration(500);
        recyclerView.getItemAnimator().setMoveDuration(500);
        recyclerView.getItemAnimator().setChangeDuration(500);

        return recyclerView;
    }

    public RecyclerView setFadeAnimation(RecyclerView recyclerView, int duration) {
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.getItemAnimator().setAddDuration(duration);
        recyclerView.getItemAnimator().setRemoveDuration(duration);
        recyclerView.getItemAnimator().setMoveDuration(duration);
        recyclerView.getItemAnimator().setChangeDuration(duration);

        return recyclerView;
    }

}
