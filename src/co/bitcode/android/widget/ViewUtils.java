/*
 * Android Utilities
 * Copyright (C) 2010, 2011  Lorenzo Villani
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

import android.view.View;

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
     * Finds a {@link View} by ID.
     * 
     * NOTE: This method will throw a {@link NullPointerException} if the view could not be found.
     * 
     * @param <T>
     *        Type of the widget.
     * @param parent
     *        The {@link View} that contains the widget we want to find.
     * @param resId
     *        The resource ID of the widget we want to find.
     * @return A widget of type <code>&lt;T&gt;</code>.
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T find(final View parent, final int resId) {
        final T ret = (T) parent.findViewById(resId);

        if (ret == null) {
            throw new NullPointerException("Could not find a view with provided ID");
        } else {
            return ret;
        }
    }
}
