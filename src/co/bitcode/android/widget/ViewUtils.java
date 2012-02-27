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

package co.bitcode.android.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Operations on {@link View}s.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class ViewUtils {
    private ViewUtils() {
    }

    /**
     * Finds a {@link View} inside an {@link Activity} content view.
     * 
     * @param <T>
     *        Type of the {@link View}.
     * @param activity
     *        The {@link Activity}.
     * @param resId
     *        {@link View} resource id.
     * @param listener
     *        The click listener to bind to the view.
     * @return Found {@link View} (if any).
     * @since 1.0.0
     */
    public static <T extends View> T bind(final Activity activity, final int resId,
            final OnClickListener listener) {
        final T found = find(activity, resId);

        found.setOnClickListener(listener);

        return found;
    }

    /**
     * Finds a {@link View} inside an {@link Activity} content view.
     * 
     * @param <T>
     *        Type of the {@link View}.
     * @param dialog
     *        The {@link Dialog}.
     * @param resId
     *        {@link View} resource id.
     * @param listener
     *        The click listener to bind to the view.
     * @return Found {@link View} (if any).
     * @since 1.0.0
     */
    public static <T extends View> T bind(final Dialog dialog, final int resId,
            final OnClickListener listener) {
        final T found = find(dialog, resId);

        found.setOnClickListener(listener);

        return found;
    }

    /**
     * Finds a child view.
     * 
     * @param <T>
     *        Type of the {@link View}.
     * @param parent
     *        The parent {@link View}.
     * @param resId
     *        {@link View} resource id.
     * @param listener
     *        The click listener to bind to the view.
     * @return Found {@link View} (if any).
     * @since 1.0.0
     */
    public static <T extends View> T bind(final View parent, final int resId,
            final OnClickListener listener) {
        final T found = find(parent, resId);

        found.setOnClickListener(listener);

        return found;
    }

    /**
     * Finds a {@link View} inside an {@link Activity} content view.
     * 
     * @param <T>
     *        Type of the {@link View}.
     * @param activity
     *        The {@link Activity}.
     * @param resId
     *        {@link View} resource id.
     * @return Found {@link View} (if any).
     * @since 1.0.0
     */
    public static <T> T find(final Activity activity, final int resId) {
        return coherceNotNull(activity.findViewById(resId));
    }

    /**
     * Finds a {@link View} inside an {@link Activity} content view.
     * 
     * @param <T>
     *        Type of the {@link View}.
     * @param dialog
     *        The {@link Dialog}.
     * @param resId
     *        {@link View} resource id.
     * @return Found {@link View} (if any).
     */
    public static <T> T find(final Dialog dialog, final int resId) {
        return coherceNotNull(dialog.findViewById(resId));
    }

    /**
     * Finds a child view.
     * 
     * @param <T>
     *        Type of the {@link View}.
     * @param parent
     *        The parent {@link View}.
     * @param resId
     *        {@link View} resource id.
     * @return Found {@link View} (if any).
     * @since 1.0.0
     */
    public static <T> T find(final View parent, final int resId) {
        return coherceNotNull(parent.findViewById(resId));
    }

    /**
     * Wraps {@link View#setVisibility(int)}.
     * 
     * @param view
     *        The view.
     * @param isVisible
     *        The visibility flag.
     * @since 1.0.0
     */
    public static void setVisible(final View view, final boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T coherceNotNull(final Object what) {
        if (what == null) {
            throw new NullPointerException("Could not find a view with provided ID");
        } else {
            return (T) what;
        }
    }
}
