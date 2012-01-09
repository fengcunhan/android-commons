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

package co.bitcode.android.view.inputmethod.impl;

import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import co.bitcode.android.view.inputmethod.KeyboardHelper;

/**
 * An {@link OnEditorActionListener} used by
 * {@link co.bitcode.android.view.inputmethod.KeyboardHelper}.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class KeyboardHelperEditorActionListener implements OnEditorActionListener {
    private final OnClickListener listener;

    /**
     * Constructor.
     * 
     * @param listener
     *        A click event will be dispatched to this listener after the virtual keyboard has been
     *        closed.
     * @since 1.0.0
     */
    public KeyboardHelperEditorActionListener(final OnClickListener listener) {
        super();

        this.listener = listener;
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
        if ((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_ACTION_SEND)) {
            KeyboardHelper.hideKeyboard(v);

            this.listener.onClick(v);

            return true;
        } else {
            return false;
        }
    }
}
