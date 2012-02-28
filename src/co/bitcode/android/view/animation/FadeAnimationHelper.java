/*
 * Android Utilities
 * Copyright (C) 2010, 2011, 2012  Lorenzo Villani
 *
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package co.bitcode.android.view.animation;

import java.security.InvalidParameterException;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import co.bitcode.android.widget.ViewUtils;

/**
 * Utility methods to easily apply a fade-in/out animation to a view.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class FadeAnimationHelper {
    private static final long DEFAULT_DURATION = 250;

    private static class FadeAnimationListener implements AnimationListener {
        private final AnimationType animationType;
        private final View animatedView;

        public enum AnimationType {
            FADE_IN,
            FADE_OUT,
        }

        public FadeAnimationListener(final AnimationType animationType, final View animatedView) {
            this.animationType = animationType;
            this.animatedView = animatedView;
        }

        @Override
        public void onAnimationEnd(final Animation animation) {
            this.animatedView.clearAnimation();

            if (this.animationType == AnimationType.FADE_IN) {
                ViewUtils.setVisible(this.animatedView, true);
            } else if (this.animationType == AnimationType.FADE_OUT) {
                ViewUtils.setVisible(this.animatedView, false);
            }
        }

        @Override
        public void onAnimationRepeat(final Animation animation) {
            // Ignored
        }

        @Override
        public void onAnimationStart(final Animation animation) {
            // Ignored
        }
    }

    private FadeAnimationHelper() {
    }

    /**
     * Fades in a view.
     * 
     * @param context
     *        Application context used to access resources.
     * @param view
     *        View to animate.
     * @since 1.0.0
     */
    public static void fadeIn(final Context context, final View view) {
        fadeIn(context, view, DEFAULT_DURATION);
    }

    /**
     * Fades in a view.
     * 
     * @param context
     *        Application context used to access resources.
     * @param duration
     *        Animation length, in milliseconds.
     * @param view
     *        View to animate.
     * @since 1.0.0
     */
    public static void fadeIn(final Context context, final View view, final long duration) {
        animate(context, view, android.R.anim.fade_in, duration);
    }

    /**
     * Fades out a view.
     * 
     * @param context
     *        Application context used to access resources.
     * @param view
     *        View to animate.
     * @since 1.0.0
     */
    public static void fadeOut(final Context context, final View view) {
        fadeOut(context, view, DEFAULT_DURATION);
    }

    /**
     * Fades out a view.
     * 
     * @param context
     *        Application context used to access resources.
     * @param duration
     *        Animation length, in milliseconds.
     * @param view
     *        View to animate.
     * @since 1.0.0
     */
    public static void fadeOut(final Context context, final View view, final long duration) {
        animate(context, view, android.R.anim.fade_out, duration);
    }

    private static void animate(final Context context, final View view, final int resId,
            final long duration) {
        final Animation animation = AnimationUtils.loadAnimation(context, resId);

        animation.setDuration(duration);
        animation.setAnimationListener(getAnimationListener(resId, view));

        view.startAnimation(animation);
    }

    private static FadeAnimationListener getAnimationListener(final int resId, final View view) {
        if (resId == android.R.anim.fade_in) {
            return new FadeAnimationListener(FadeAnimationListener.AnimationType.FADE_IN, view);
        } else if (resId == android.R.anim.fade_out) {
            return new FadeAnimationListener(FadeAnimationListener.AnimationType.FADE_OUT, view);
        } else {
            throw new InvalidParameterException("Unsupported animation type");
        }
    }
}
