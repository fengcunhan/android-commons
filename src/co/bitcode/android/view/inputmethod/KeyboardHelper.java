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

package co.bitcode.android.view.inputmethod;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import co.bitcode.android.app.ContextUtils;
import co.bitcode.android.view.inputmethod.impl.KeyboardHelperEditorActionListener;

/**
 * Methods to deal with virtual keyboards.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class KeyboardHelper {
    private KeyboardHelper() {
    }

    /**
     * Hides the virtual keyboard.
     * 
     * It is the same as calling {@link #setKeyboardVisible(View, false)}.
     * 
     * @param view
     *        The {@link View} which caused the virtual keyboard to be shown.
     * @since 1.0.0
     */
    public static void hideKeyboard(final View view) {
        setKeyboardVisible(view, false);
    }

    /**
     * Shows the virtual keyboard.
     * 
     * It is the same as calling {@link #setKeyboardVisible(View, true)}.
     * 
     * @param view
     *        The {@link View} which caused the virtual keyboard to be shown.
     * @since 1.0.0
     */
    public static void showKeyboard(final View view) {
        setKeyboardVisible(view, true);
    }

    /**
     * Shows or hides the virtual keyboard.
     * 
     * @param view
     *        The {@link View} which caused the virtual keyboard to be shown.
     * @param isVisible
     *        Whether the keyboard is shown or not.
     * @since 1.0.0
     */
    public static void setKeyboardVisible(final View view, final boolean isVisible) {
        final InputMethodManager imm = ContextUtils.getInputMethodManager(view.getContext());

        if (isVisible) {
            // See: http://stackoverflow.com/q/2712822
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Sets up a listener which will cause the virtual keyboard to be hidden when users click the
     * "Send" or "Enter" button. Additionally, this click event is forwarded to any interested
     * listener after the keyboard has been hidden.
     * 
     * @param textView
     *        The {@link TextView} we want to configure.
     * @param listener
     *        Handles the click event after keyboard has been hidden.
     */
    public static void setEditorAction(final TextView textView, final OnClickListener listener) {
        textView.setOnEditorActionListener(new KeyboardHelperEditorActionListener(listener));
    }
}
