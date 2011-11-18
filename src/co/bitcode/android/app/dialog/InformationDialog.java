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

import co.bitcode.android.R;

/**
 * Builds a standardized informational dialog.
 * 
 * @author Lorenzo Villani
 * @since 1.0.0
 */
public class InformationDialog extends AlertDialog {
    /**
     * Construct an error dialog.
     * 
     * @param context
     *        Parent context.
     * @param message
     *        Message to show.
     */
    public InformationDialog(final Context context, final CharSequence message) {
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
    public InformationDialog(final Context context, final int messageId) {
        super(context);

        init();
        setMessage(context.getResources().getString(messageId));
    }

    private void init() {
        setIcon(android.R.drawable.ic_dialog_info);
        setTitle(R.string.dialog_error_title);
    }
}
