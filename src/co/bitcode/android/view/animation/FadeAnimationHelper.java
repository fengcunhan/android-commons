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

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Utility methods to easily apply a fade-in/out animation to a view.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class FadeAnimationHelper {
    private static final long DEFAULT_DURATION = 500;

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

        view.startAnimation(animation);
    }
}
