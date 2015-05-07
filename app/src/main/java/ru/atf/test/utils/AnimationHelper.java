package ru.atf.test.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {

    static final int BUTTON_ANIMATION_DURATION = 500;
    static final int LIST_ANIMATION_DURATION = 700;

    public static void animateHideListView(final View placesList, final View placeButton) {
        final TranslateAnimation showButtonAnimation = new TranslateAnimation(0, 0, placeButton.getHeight(), 0);
        showButtonAnimation.setDuration(BUTTON_ANIMATION_DURATION);
        showButtonAnimation.setInterpolator(new BounceInterpolator());
        showButtonAnimation.setAnimationListener(new SimpleStartAnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                placeButton.setVisibility(View.VISIBLE);
            }
        });
        TranslateAnimation listHideAnimation = new TranslateAnimation(0, 0, 0, placesList.getHeight());
        listHideAnimation.setDuration(LIST_ANIMATION_DURATION);
        listHideAnimation.setAnimationListener(new SimpleEndAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                placesList.setVisibility(View.INVISIBLE);
                placeButton.startAnimation(showButtonAnimation);
            }
        });
        placesList.startAnimation(listHideAnimation);
    }

    public static void animateShowListView(final View placesList, final View placeButton) {
        final TranslateAnimation listShowAnimation = new TranslateAnimation(0, 0, placesList.getHeight(), 0);
        listShowAnimation.setDuration(LIST_ANIMATION_DURATION);
        listShowAnimation.setAnimationListener(new SimpleStartAnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                placesList.setVisibility(View.VISIBLE);
            }
        });

        TranslateAnimation hideButtonAnimation = new TranslateAnimation(0, 0, 0, placeButton.getHeight());
        hideButtonAnimation.setDuration(BUTTON_ANIMATION_DURATION);
        hideButtonAnimation.setInterpolator(new BounceInterpolator());
        hideButtonAnimation.setAnimationListener(new SimpleEndAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                placeButton.setVisibility(View.INVISIBLE);
                placesList.startAnimation(listShowAnimation);
            }
        });
        placeButton.startAnimation(hideButtonAnimation);
    }

    abstract static class SimpleStartAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    abstract static class SimpleEndAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

}
