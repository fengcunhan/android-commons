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

package co.bitcode.android.app.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import co.bitcode.android.R;

/**
 * Builds a standardized error dialog.
 * 
 * @author Lorenzo Villani
 * @since 1.0.0
 */
public class ErrorDialog extends AlertDialog implements OnClickListener {
    /**
     * Construct an error dialog.
     * 
     * @param context
     *        Parent context.
     * @param message
     *        Message to show.
     */
    public ErrorDialog(final Context context, final CharSequence message) {
        super(context);

        init();
        setMessage(message);
    }

    /**
     * Construct an error dialog.
     * 
     * @param context
     *        Parent context.
     * @param messageId
     *        String resource ID.
     */
    public ErrorDialog(final Context context, final int messageId) {
        super(context);

        init();
        setMessage(context.getResources().getString(messageId));
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        dismiss();
    }

    private void init() {
        setButton(BUTTON_POSITIVE, getContext().getString(android.R.string.ok), this);
        setCancelable(true);
        setIcon(android.R.drawable.ic_dialog_alert);
        setTitle(R.string.dialog_error_title);
    }
}
