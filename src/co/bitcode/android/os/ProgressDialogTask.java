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

package co.bitcode.android.os;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

/**
 * An {@link ObservableTask} task which shows a {@link ProgressDialog} during execution.
 * 
 * @param <Params>
 *        The type of the parameters sent to the task upon execution.
 * @param <Result>
 *        The type of the result of the background computation.
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class ProgressDialogTask<Params, Result> extends ContextAsyncTask<Params, Result>
        implements OnCancelListener {
    private final ProgressDialog progressDialog;

    /**
     * Constructor. Shows a cancelable {@link ProgressDialog} by default.
     * 
     * @param context
     *        The {@link Context}.
     * @param messageId
     *        String resource ID to show in the {@link ProgressDialog}.
     * @since 1.0.0
     */
    public ProgressDialogTask(final Context context, final int messageId) {
        super(context);

        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setCancelable(true);
        this.progressDialog.setOnCancelListener(this);
        this.progressDialog.setMessage(context.getResources().getString(messageId));
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        cancel(true);
        onFinalize();
    }

    /**
     * @param flag
     *        Whether this task is user-cancelable by pressing the back button.
     */
    public void setCancelable(final boolean flag) {
        this.progressDialog.setCancelable(flag);
    }

    @Override
    protected void onFinalize() {
        super.onFinalize();

        this.progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.progressDialog.show();
    }
}
